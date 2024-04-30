const loginUserInfo = sessionInfo;
console.log(loginUserInfo);
console.log(sessionInfo);
  const ws = new WebSocket('ws://192.168.0.55:8890');
  if(loginUserInfo){
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

   window.sendAlarm = function(){
    const message = {
      message: 'getAlarm',
      memberNo: loginUserInfo.no,
      content: '알람알람',
      timestamp: new Date().toISOString()
    }
    ws.send(JSON.stringify(message));
  }