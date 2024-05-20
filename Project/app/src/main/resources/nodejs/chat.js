const express = require('express');
const http = require('http');
const WebSocket = require('ws');
const fs = require('fs');
const properties = require('properties-parser');
const path = require('path');

// properties 파일 불러오기
const config = properties.read('../application.properties');


// ncp file upload
const AWS = require('aws-sdk');
const ncpEndpoint = config.ncpendpoint;
const endpoint = new AWS.Endpoint(ncpEndpoint);
const region = config.ncpregionname;
const access_key = config.ncpaccesskey;
const secret_key = config.ncpsecretkey;
const bucket_name = config.ncpbucketname;
const uploadDir = 'chat/';
const localDir = '../chat/log/';
const chat = express(); // 'express' 변수명 수정
const server = http.createServer(chat);
const wss = new WebSocket.Server({ server });

// 채팅파일
const chatFile = {
  chatFileName: '',
  chatFilePath: '',
  chatFileFullPath: ''
}

// 채팅 내역
const chatContent = [];
let lastDate = 'ㅁ';

function setChatContent(messageObj){

  if (messageObj.timestamp)
  chatContent.push({
      name: messageObj.name,
      sender: messageObj.sender,
      timestamp: messageObj.timestamp,
      content: messageObj.content
  });
}

// 이전에 전송된 메시지를 보낸 방들의 목록을 저장하는 Set
const sentPreviousMessagesRooms = new Map();

const S3 = new AWS.S3({
  endpoint: endpoint,
  region: region,
  credentials: {
    accessKeyId : access_key,
    secretAccessKey: secret_key
  }
});

let count = 1;
// 클라이언트 연결시
wss.on('connection', (ws) => {
  console.log("연결됨" + count);
  count++;
  // 클라이언트로부터 메시지를 받았을 때
  ws.on('message', (message) => {

    const messageObj = JSON.parse(message);
    const roomInfo = sentPreviousMessagesRooms.get(messageObj.reservationNo);

    chatFile.chatFileName = messageObj.chatName;
    chatFile.chatFilePath = localDir;
    chatFile.chatFileFullPath = localDir + chatFile.chatFileName;


    if(messageObj.message === 'getChat'){
      if( !roomInfo ){
        setChatRoom(ws, messageObj);

      } else{
        addChatUser(ws, messageObj);
      }
      sendPreviousMessages(ws);

    } else{
      // 메시지를 파일에 저장
      saveMessage(messageObj);
      // 채팅 메시지 설정
      setChatContent(messageObj);

      // 방에 속한 클라이언트에게 새로운 메시지 전송
      roomInfo.forEach(clientInfo => {
        const ws = clientInfo.ws;
        if( ws.readyState === WebSocket.OPEN ){
          ws.send(JSON.stringify(chatContent));
        }
      });
      chatContent.pop();
    }
  });

  ws.on('close', () =>{
    console.log("웹소켓 종료");
    deleteChatUser(ws);
  })
});

// 채팅방 설정
function setChatRoom(ws, messageObj){
  if(!sentPreviousMessagesRooms.has(messageObj.reservationNo)){ // 예약번호에 대한 채팅방 존재 체크
    sentPreviousMessagesRooms.set( messageObj.reservationNo, new Map() ); // 없는경우 채팅방 생성
  }

  const roomInfo = sentPreviousMessagesRooms.get(messageObj.reservationNo); // 생성한 채팅방 가져옴
  const clientInfo = { sender: messageObj.sender, ws: ws }; // 채팅방에 접속한 클라이언트 정보 , sender / WebSocket 설정
  roomInfo.set( messageObj.memberNo , clientInfo ); // memberNo를 토대로 clientInfo 설정
}

// 채팅방에 유저 추가
function addChatUser(ws, messageObj){
  const roomInfo = sentPreviousMessagesRooms.get(messageObj.reservationNo);
  const clientInfo = roomInfo.get(messageObj.memberNo);
  if(!clientInfo){
    const addInfo = { sender: messageObj.sender, ws:ws };
    roomInfo.set( messageObj.memberNo, addInfo );
  }
}

// 채팅방 유저 삭제
function deleteChatUser(ws){
  console.log("맵에서 웹소켓 삭제");
  sentPreviousMessagesRooms.forEach((roomInfo, reservationNo) => { // Map에 대해 반복문 실행
    roomInfo.forEach((clientInfo, memberNo) => {
      if(clientInfo.ws === ws){ // 클라이언트에서 종료를한(웹소켓연결이 끊긴) 대상 체크
        roomInfo.delete(memberNo); // map에서 삭제
      }
    });

    if(roomInfo.size === 0){ // Map에 해당 채팅방에 사용자가 없는경우
      if (fs.existsSync(chatFile.chatFileFullPath)) {
        fs.readFile(chatFile.chatFileFullPath, 'utf8', (err, data) => {
          if (err) {
            console.error(err);
            return;
          }
          sentPreviousMessagesRooms.delete(reservationNo); // 채팅방 완전 삭제
          uploadFile(); // 스토리지 서버에 파일 업로드
        });
      } else {
        console.log("채팅 기록 파일이 없습니다.");
      }
    }



  });
}

// 이전 채팅기록 전송
function sendPreviousMessages(ws) {
  console.log("이전 채팅 기록 전송");
  console.log(chatFile);

  // 디렉토리 존재 여부 확인
  if (!fs.existsSync(chatFile.chatFilePath)) {
    // 디렉토리가 없으면 생성
    fs.mkdirSync(chatFile.chatFilePath);
  }

  if (fs.existsSync(chatFile.chatFileFullPath)) {
    fs.readFile(chatFile.chatFileFullPath, 'utf8', (err, data) => {
      if (err) {
        console.error(err);
        return;
      }
      ws.send(data);
    });
  } else {
    console.log("채팅 기록 파일이 없습니다.");
  }
}

// 채팅 파일에 저장
function saveMessage(message) {
  console.log("채팅 파일 로컬에 저장");
  let messages = [];
  fs.readFile(chatFile.chatFileFullPath, 'utf8', (err, data) => {
    if (err) {

    }
    if (data) {
      messages = JSON.parse(data);
    }
    messages.push(message);
    fs.writeFile(chatFile.chatFileFullPath, JSON.stringify(messages) + '\n', { flag: 'w' }, (err) => { // flag: 'a'를 사용하여 기존 데이터 덮어씌우지 않고 추가
      if (err) {
        console.error(err);
        return;
      }
      console.log("채팅 파일 저장");
    });
  });
}


async function uploadFile(){
  console.log("파일 업로드");
  // upload file
  await S3.putObject({
    Bucket: bucket_name, // upload할 bucket 명
    Key: uploadDir + chatFile.chatFileName,
    ACL: 'public-read',
    // ACL을 지우면 전체 공개되지 않습니다.
    Body: fs.createReadStream(chatFile.chatFileFullPath) // 로컬에 있는 파일 지정
}).promise();
}

server.listen(8889, () => {
  console.log("채팅 서버 시작 port : 8889");
});

