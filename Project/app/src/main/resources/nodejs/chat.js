const express = require('express');
const http = require('http');
const WebSocket = require('ws');
const fs = require('fs');
const properties = require('properties-parser');

// properties 파일 불러오기
const config = properties.read('../application.properties');

// ncp file upload
const AWS = require('aws-sdk');
const endpoint = new AWS.Endpoint(config.ncp.endpoint);
const region = config.ncp.regionname;
const access_key = config.ncp.accesskey;
const secret_key = config.ncp.secretkey;
const bucket_name = config.tp3-salaba;

const chat = express(); // 'express' 변수명 수정
const server = http.createServer(chat);
const wss = new WebSocket.Server({ server });


// 채팅파일
const chatFile = {
  chatFileName: '',
  chatFilePath: '',
  chatFileFullPath: ''
}

// 이전에 전송된 메시지를 보낸 방들의 목록을 저장하는 Set
const sentPreviousMessagesRooms = new Set();

const S3 = new AWS.S3({
  endpoint: endpoint,
  region: region,
  credentials: {
    accessKeyId : access_key,
    secretAccessKey: secret_key
  }
});


// 클라이언트 연결시
wss.on('connection', (ws) => {
  console.log("연결됨");
  // 클라이언트로부터 메시지를 받았을 때
  ws.on('message', (message) => {
    
    const messageObj = JSON.parse(message);
    chatFile.chatFileName = messageObj.chatName;
    chatFile.chatFileFullPath = chatFile.chatFilePath+chatFile.chatFileName;

    console.log(messageObj);

    if(messageObj === 'getChat' && !sentPreviousMessagesRooms.has(messageObj.reservationNo)){
      sendPreviousMessages(ws);
      sentPreviousMessagesRooms.add(messageObj.reservationNo);
    }
    else{
      // 메시지를 파일에 저장
      saveMessage(messageObj,chatFilePath);
      // 모든 클라이언트에게 새로운 메시지 전송
      wss.clients.forEach((client) => {
        if (client.readyState === WebSocket.OPEN) {
          client.send(JSON.stringify(messageObj));
        }
      });
    }
  });

  // 클라이언트와의 WebSocket 연결이 끊겼을때
  ws.on('close', () =>{
    uploadFile(chatFileName);
  });
});

// 이전 채팅기록 전송
function sendPreviousMessages(ws) {
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
  // upload file
  await S3.putObject({
    Bucket: bucket_name, // upload할 bucket 명
    Key: chatFile.chatFileName, // upload시 저장할 파일명
    ACL: 'public-read',
    // ACL을 지우면 전체 공개되지 않습니다.
    Body: fs.createReadStream(chatFile.chatFileFullPath) // 로컬에 있는 파일 지정
}).promise();
}

server.listen(8889, () => {
  console.log("채팅 서버 시작 port : 8889");
});
