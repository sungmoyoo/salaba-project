const loginUserInfo = sessionInfo;

console.log("======================");
console.log(loginUserInfo);
console.log(sessionInfo);

window.onload = function() {
  console.log("======================***");
  console.log(loginUserInfo.loginUser);

  if (loginUserInfo.loginUser) {
    if (!window.ws || window.ws.readyState === WebSocket.CLOSED) { // Check if ws is undefined or closed
      // 웹소켓 연결
      const ws = new WebSocket('ws://localhost:8890');
      window.ws = ws;
      // 로그인했을 때 웹소켓 연결
      ws.onopen = function(event) {
        const message = {
          message: 'connectUser',
          memberNo: loginUserInfo.loginUser.no,
        };
        ws.send(JSON.stringify(message));
        console.log("로그인했음, node로 정보 전송");
        console.log(loginUserInfo);

        // 로그인시 알람정보 가져옴
        $.ajax({
          type: "POST",
          url: "/alarm/get",
          success:function(data){
            console.log(data);
            setAlarmPopover(data);
          },
          error:()=>{

          }
        });
      };

      // 알림을 받았을 때
      ws.onmessage = function(event) {
        const alarm = JSON.parse(event.data);
        console.log("알람을 받음");
        console.log(alarm);
        $.ajax({
          type: "POST",
          url: "/alarm/get",
          success:function(data){
            console.log(data);
            setAlarmPopover(data);
          },
          error:()=>{

          }
        });
      };

      ws.onerror = function(error) {
        console.error("WebSocket Error: ", error);
      };

      ws.onclose = function(event) {
        console.log("WebSocket closed: ", event);
      };
    }
  }
};

function getAlarm(){
  $.ajax({
    type: "POST",
    url: "/alarm/get",
    success:function(data){
      return data;
    },
    error:()=>{

    }
  });
}

// 알람 내용 셋팅
function setAlarmPopover(alarm){
  let popoverContent = '';

  alarm.forEach(item => {
    let alarmLink = '<a href="' + item.content + '" class="alarm-link" id="'+ item.notifyNo +'">' + item.mark + '</a>';
    popoverContent += `
      <div class="card mb-2">
        <div class="card-body p-2">
          ${alarmLink}
          <div class="text-muted small">${item.notifyDate}</div>
          <span class="float-end" style="cursor: pointer;" onclick="removeAlarm(this)">
            <i class="bi bi-x"></i>
          </span>
        </div>
      </div>`;
  });
  console.log("aaa");

  // 팝오버 내용 셋팅
  $('#notificationPopup').html(popoverContent);

  // 알림 카운터 업데이트
  $('#notificationCounter').text(alarm.length);

  // 팝오버 초기화
  $('#notificationIcon').popover('dispose');

  // 팝오버 재활성화
  $('#notificationIcon').popover({
    content: $('#notificationPopup').html(),
    placement: 'bottom',
    trigger: 'focus',
    html: true
  });

  // 알람 클릭 처리 및 삭제
  $(document).on('click', '.alarm-link', function(){
    const alarmId = $(this).attr('id');
    console.log("알람아이디 : " + alarmId);
    console.log("aaaaaa");
    markAsRead(alarmId);
  });
  
  // 읽음 버튼 클릭 이벤트 추가
  $('.mark-as-read').off('click').on('click', function() {
    const alarmId = $(this).closest('.card').data('alarm-id');
    markAsRead(alarmId);
  });
}

// 읽음으로 표시 함수
function markAsRead(alarmId) {
  $.ajax({
    type: "POST",
    url: "/alarm/read",
    data:{
      notifyNo: alarmId
    },
    success: function() {
      console.log("알람을 읽음으로 표시: " + alarmId);
      removeAlarmFromUI(alarmId);
    },
    error: function() {
      console.error("알람을 읽음으로 표시하는 중 오류 발생: " + alarmId);
    }
  });
}

// UI에서 알람 제거
function removeAlarmFromUI(alarmId) {
  $(`#${alarmId}`).closest('.card').remove(); // 해당 알람 아이디를 가진 요소의 가장 가까운 부모 카드를 제거합니다.

  // 알림 카운터 업데이트
  let currentCount = parseInt($('#notificationCounter').text());
  $('#notificationCounter').text(currentCount - 1);

  // 팝오버 초기화
  $('#notificationIcon').popover('dispose');

  // 팝오버 재활성화
  $('#notificationIcon').popover({
    content: $('#notificationPopup').html(),
    placement: 'bottom',
    trigger: 'focus',
    html: true
  });
}

window.sendAlarm = function(alarm, alarmType) {
  // 웹소켓이 연결되어 있을 때만 알림 전송
  console.log(window.ws);
  console.log(window.ws.readyState);
  if (window.ws && window.ws.readyState === WebSocket.OPEN) {
    const message = {
      message: "getAlarm",
      memberNo: alarm.memberNo,
      content: alarm.content,
      mark: alarm.mark,
      timestamp: alarm.notifyDate
    };
    window.ws.send(JSON.stringify(message));
    console.log("node로 정보 보냄");
  } else {
    console.log("웹소켓이 연결되어 있지 않음");
  }
};

