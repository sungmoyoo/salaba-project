const express = require('express');
const http = require('http');
const WebSocket = require('ws');
const fs = require('fs');
const path = require('path');

const chat = express(); // 'express' 변수명 수정
const server = http.createServer(chat);
const wss = new WebSocket.Server({ server });

const chatFilePath = 'https://kr.object.ncloudstorage.com/tp3-salaba/chat/';

function setFileDir(chatFileName){
  const fileFullPath = chatFilePath + chatFileName;
  const fileDir = path.dirname(fileFullPath);
  process.chdir(fileDir);
}

// 이전에 전송된 메시지를 보낸 방들의 목록을 저장하는 Set
const sentPreviousMessagesRooms = new Set();



// 클라이언트 연결시
wss.on('connection', (ws) => {
  console.log("연결됨");
  // 클라이언트로부터 메시지를 받았을 때
  ws.on('message', (message) => {
    console.log("aaaa");
    const messageObj = JSON.parse(message);
    const chatFileName = messageObj.chatName;

    console.log(messageObj);
    console.log(chatFilePath);
    console.log(chatFileName);

    if(messageObj === 'getChat' && !sentPreviousMessagesRooms.has(messageObj.reservationNo)){
      sendPreviousMessages(ws, chatFileName);
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
  console.log("bbbbb");
});

// 이전 채팅기록 전송
function sendPreviousMessages(ws, chatFileName) {
  setFileDir(chatFileName);
  fs.readFile(chatFileName, 'utf8', (err, data) => {
    if (err) {
      console.error(err);
      return;
    }
    ws.send(data);
  });
}

// 채팅 파일에 저장
function saveMessage(message, chatFilePath) {
  fs.readFile(chatFilePath, 'utf8', (err, data) => {
    if (err) {
      console.error(err);
      return;
    }
    let messages = [];
    if (data) {
      messages = JSON.parse(data);
    }
    messages.push(message); // 변수명 수정: message -> messageObj
    fs.writeFile(chatFilePath, JSON.stringify(messages) + '\n', { flag: 'w' }, (err) => { // flag: 'a'를 사용하여 기존 데이터 덮어씌우지 않고 추가
      if (err) {
        console.error(err);
        return;
      }
      console.log("채팅 파일 저장");
    });
  });
}

server.listen(8889, () => {
  console.log("채팅 서버 시작 port : 8889");
});
