const express = require('express');
const http = require('http');
const WebSocket = require('ws');

const alarm = express(); // 'express' 변수명 수정
const server = http.createServer(alarm);
const wss = new WebSocket.Server({ server });

// 현재 서버 접속 중인 유저 목록
const loginUser = new Map();

// 알림 내용
let alarmContent = [];

function setAlarmContent(messageObj){
  alarmContent.push({
    memberNo: messageObj.memberNo,
    content: messageObj.content,
    timestamp: messageObj.notifyDate
  });
}
let count = 1;
// 클라이언트 연결시
wss.on('connection', (ws) => {
  console.log("클라이언트 연결됨");
  console.log("접속인원 : " + loginUser.size);
  // 클라이언트로부터 메시지를 받았을 때
  ws.on('message', (message) => {
    console.log("클라이언트로부터 요청을 받음");

    const messageObj = JSON.parse(message);
    const memberNo = parseInt(messageObj.memberNo);
    // 유저가 로그인 했을 때
    if( messageObj.message === 'connectUser' ){
      setUser(memberNo , ws);
      console.log("유저 로그인 함");
      console.log("============================");
      console.log(loginUser);
      console.log("============================");
    }
    
    // 새로운 알림을 받았을 때
    if( messageObj.message === 'getAlarm' ){
      console.log("새로운 알람을 받음");
      console.log(messageObj);
      console.log(messageObj.memberNo);
      console.log(loginUser.has(memberNo));
      // 알람받을 대상이 클라이언트에 연결된 상태일 경우
      if(loginUser.has(memberNo)){
        console.log("map에 존재");
        setAlarmContent(messageObj); // 알림 내용 저장
        
        // 알림 받을 회원의 웹소켓 정보를 갖고오기 위해 map에서 조회
        const userInfo = loginUser.get(memberNo);

        if(userInfo.readyState === WebSocket.OPEN){
          userInfo.send(JSON.stringify(alarmContent)); // 알림 전송
          console.log("알람 보냄");
          console.log("알람 내용" + alarmContent);
          console.log("대상 :" + userInfo);
          console.log(userInfo);
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
function setUser(memberNo , ws){
  // 로그인한 유저의 번호 , 소켓 저장
  console.log(memberNo);
  if(!loginUser.has(memberNo)){
    loginUser.set(memberNo, ws);
  }
}

// 클라이언트 종료시
function deleteUser(ws){
  loginUser.forEach((userInfo) =>{
    if(userInfo.ws === ws){ // 해당 유저의 웹소켓 정보 삭제
      loginUser.delete(userInfo);
    }
  });
  console.log("클라이언트 연결 종료");
  console.log(loginUser);
}

server.listen(8890, () => {
  console.log("알람 서버 시작 port : 8890");
});
