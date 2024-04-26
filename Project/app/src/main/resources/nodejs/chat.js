const express = require('express');
const http = require('http');
const WebSocket = require('ws');
const fs = require('fs');
const properties = require('properties-parser');

// properties 파일 불러오기
const config = properties.read('../application.properties');
console.log(config);

// ncp file upload
const AWS = require('aws-sdk');
const ncpEndpoint = config.ncpendpoint;
const endpoint = new AWS.Endpoint(ncpEndpoint);
const region = config.ncpregionname;
const access_key = config.ncpaccesskey;
const secret_key = config.ncpsecretkey;
const bucket_name = config.ncpbucketname + '/chat';

const chat = express(); // 'express' 변수명 수정
const server = http.createServer(chat);
const wss = new WebSocket.Server({ server });

const localFilePath = "";
// 채팅파일
const chatFile = {
  chatFileName: '',
  chatFilePath: '',
  chatFileFullPath: ''
}

// 채팅 내역
const chatContent = [];

function setChatContent(messageObj){
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
    chatFile.chatFileName = messageObj.chatName;
    chatFile.chatFileFullPath = localFilePath+chatFile.chatFileName;
    console.log(messageObj);
    console.log(chatFile);
    console.log(sentPreviousMessagesRooms);
    const roomInfo = sentPreviousMessagesRooms.get(messageObj.reservationNo);

    // front로부터 받은 요청 확인 && 이전내용 중복 보내기 체크
    if(messageObj.message === 'getChat' && (!roomInfo || !(roomInfo.includes(messageObj.sender)))){
      sendPreviousMessages(ws);
      if(!roomInfo){
        sentPreviousMessagesRooms.set(messageObj.reservationNo, [messageObj.sender]);
      }else{
        roomInfo.push(messageObj.sender);
      }
    }
    else{
      console.log(chatContent);
      // 채팅 메시지 설정
      setChatContent(messageObj);
      console.log(chatContent);
      // 메시지를 파일에 저장
      saveMessage(chatContent);
      // 모든 클라이언트에게 새로운 메시지 전송
      wss.clients.forEach((client) => {
        if (client.readyState === WebSocket.OPEN) {
          client.send(JSON.stringify(chatContent));
        }
      });
      chatContent.pop();
    }
  });

  // 클라이언트와의 WebSocket 연결이 끊겼을때
  ws.on('close', () =>{
    uploadFile();
  });
});

// 이전 채팅기록 전송
function sendPreviousMessages(ws) {
  console.log(chatFile);
  fs.readFile(chatFile.chatFileFullPath, 'utf8', (err, data) => {
    if (err) {
      console.error(err);
      return;
    }
    ws.send(data);
  });
}

// 채팅 파일에 저장
function saveMessage(message) {
  console.log(chatFile);
  fs.readFile(chatFile.chatFileFullPath, 'utf8', (err, data) => {
    if (err) {
      console.error(err);
      return;
    }
    let messages = [];
    if (data) {
      messages = JSON.parse(data);
    }
    messages.push(message); // 변수명 수정: message -> messageObj
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
    Key: chatFile.chatFileName,
    ACL: 'public-read',
    // ACL을 지우면 전체 공개되지 않습니다.
    Body: fs.createReadStream(chatFile.chatFileFullPath) // 로컬에 있는 파일 지정
}).promise();
}

server.listen(8889, () => {
  console.log("채팅 서버 시작 port : 8889");
});
