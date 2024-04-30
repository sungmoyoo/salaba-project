let ws; // WebSocket
const loginUserInfo = sessionInfo;
console.log(loginUserInfo);
console.log(sessionInfo);
window.onload = function(){
  if(loginUserInfo){
    console.log("소켓 생성" );
    // 웹소켓 연결
    ws = new WebSocket('ws://192.168.0.55:8890');
    
    // 로그인했을때 웹소켓 연결
    ws.onopen = function(event){
      const message = {
        message: 'connectUser',
        memberNo: loginUserInfo.no,
      }
      ws.send(JSON.stringify(message));
      console.log("로그인했음 , node로 정보 전송");
      console.log(loginUserInfo);
    }

    // 알림을 받았을 때
    ws.onmessage = function(event){
      const alarm = JSON.parse(event.data);
      console.log("알람을 받음");
      console.log(alarm);
    }
  }
}

   window.sendAlarm = function(targetNo){
    // 웹소켓이 연결되어 있을때만 알림 전송
    if( ws && ws.readyState === WebSocket.OPEN ){
      const message = {
        message: 'getAlarm',
        memberNo: targetNo,
        content: '알람알람',
        timestamp: new Date().toISOString()
      }
      ws.send(JSON.stringify(message));
    }else{
      console.log("웹소켓이 연결되어 있지 않음");
    }
  }