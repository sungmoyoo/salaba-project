const express = require('express');
const http = require('http');
const { userInfo } = require('os');
const WebSocket = require('ws');

const alarm = express(); // 'express' 변수명 수정
const server = http.createServer(chat);
const wss = new WebSocket.Server({ server });

// 현재 서버 접속 중인 유저 목록
const loginUser = new Map();

// 알림 내용
let alarmContent = [];

function setAlarmContent(messageObj){
  alarmContent.push({
    memberNo: messageObj.memberNo,
    content: messageObj.content,
    timestamp: messageObj.timestamp
  });
}

// 클라이언트 연결시
wss.on('connection', (ws) => {
  
  // 클라이언트로부터 메시지를 받았을 때
  ws.on('message', (message) => {
    const messageObj = JSON.parse(message);
    
    // 유저가 로그인 했을 때
    if( messageObj.message === 'connectUser' ){
      setUser(messageObj , ws);
    }
    
    // 새로운 알림을 받았을 때
    if( messageObj.message === 'getAlarm' ){
      // 알람받을 대상이 클라이언트에 연결된 상태일 경우
      if(loginUser.has(messageObj.memberNo)){
        setAlarmContent(messageObj); // 알림 내용 저장
        
        // 알림 받을 회원의 웹소켓 정보를 갖고오기 위해 map에서 조회
        const userInfo = loginUser.get(messageObj.memberNo);

        const userWs = userInfo.ws; // 알림받을 대상의 웹켓

        if(userWs.readyState === WebSocket.OPEN){
          userWs.send(JSON.stringify(alarmContent)); // 알림 전송
          alarmContent.pop(); // 알람 내용 초기화
        }
      }
    }
  })

  // 클라이언트 종료 시
  ws.on('close',() =>{
    deleteUser(ws);
  });
});

// 로그인 유저 셋팅
function setUser(messageObj , ws){
  // 로그인한 유저의 번호 , 소켓 저장
  if(!loginUser.has(messageObj.memberNo)){
    loginUser.set(messageObj.memberNo, ws);
  }
}

// 클라이언트 종료시
function deleteUser(ws){
  loginUser.forEach((userInfo) =>{
    if(userInfo.ws === ws){ // 해당 유저의 웹소켓 정보 삭제
      loginUser.delete(userInfo);
    }
  });
}

server.listen(8890, () => {
  console.log("알람 서버 시작 port : 8890");
});
