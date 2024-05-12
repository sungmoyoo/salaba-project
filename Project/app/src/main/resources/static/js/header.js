const userInfoMenuButton = document.querySelector('.userInfo-button');
const userInfoMenuContainer = document.querySelector('.userInfo-menu');

let isMenuOpen = false;

// 메뉴 버튼
userInfoMenuButton.addEventListener('click', function(event){
  if(isMenuOpen){
    closeUserInfoMenu();
  }else{
    openUserInfoMenu();
  }

  // 현재 메뉴 상태 업데이트
  isMenuOpen = !isMenuOpen;

  // 이벤트 전파 중단
  event.stopPropagation();
});

// userInfoMenu 열기
function openUserInfoMenu(){
  userInfoMenuContainer.style.display = 'block';
}

// userInfoMenu 닫기
function closeUserInfoMenu(){
  userInfoMenuContainer.style.display = 'none';
}

document.addEventListener('click', function(event){
  // 메뉴가 열려있는지 체크
  if(isMenuOpen && !userInfoMenuContainer.contains(event.target)){
    closeUserInfoMenu(); // 메뉴가 열려있는 경우 닫아줌
    isMenuOpen = false;
  }
});


// login modal 열기
function openUserLogin(){
  $.ajax({
    url: "/modal/loginModal.html",
    method: "GET",
    success: function(data){
      const modal = data;
      $('body').append(modal);
      $('#userLoginModal').modal('show');
    },
    error: function(){
      
    }
  });
}

// Login
function userLogin(){
  const email = $('#userEmail').val();
  const password = $('#userPassword').val();
  const saveEmail = $('#saveEmail').val();

  console.log(email);
  console.log([password]);
  console.log(saveEmail);

  if( email == "" ){
    alert("이메일을 입력하세요.");
    $('#userEmail').focus();
    return;
  }
  if( password == "" ){
    alert("비밀번호를 입력하세요.");
    $('#userPassword').focus();
    return;
  }

  $.ajax({
    type: "POST",
    url: "/auth/login",
    data: {
      email: email,
      password: password,
      saveEmail: saveEmail
    },
    success:function(response){
     checkLogin(response);
    },
    error:()=>{
      alert("다시 시도하세요.");
    }
  });
}

// 이메일 찾기
$(document).on("click","#findUserEmail-button",function(){
  findUserEmail();
});

function findUserEmail(){
  const userName = $('#find-email-username').val();
  const userBirth = $('#find-email-userbirth').val();
  
  if( userName == "" ){
    alert("이름을 입력하세요.");
    $('#find-email-username').focus();
    return
  }else if( userBirth == "" ){
    alert("생년월일을 입력하세요.");
    $('#find-email-userbirth').focus();
    return
  }
  
  $.ajax({
    type: "POST",
    url: "/member/searchEmail",
    contentType: "application/json",
    data:JSON.stringify({
      name: userName,
      birthday: userBirth
    }),
    success:function(data){
      console.log(data);
      const state = data.state;
      console.log(state);
      if( state == "0" ){
        console.log("asdasdasd");
        $('#find-email-username').val("");
        $('#find-email-userbirth').val("");
        $('#find-email-fail-button').click();
      }else if( state == "1" ){
        console.log("qweqweqweqwe");
        $('#find-email-username').val("");
        $('#find-email-userbirth').val("");
        $('#find-email-success-button').click();
      }
    },
    error: ()=>{
      alert("이메일 찾기 에러");
    }
  });
}

$(document).on("click","#findUserPassword-button", function(){
  findUserPassword();
});

// 비밀번호 찾기
function findUserPassword(){
  const userName = $('#find-password-username').val();
  const userEmail = $('#find-password-useremail').val();

  if( userName == "" ){
    alert("이름을 입력하세요.");
    $('#find-password-username').focus();
    return;
  }
  else if( userEmail == "" ){
    alert("이메일을 입력하세요.");
    $('#find-password-useremail').focus();
    return;
  }

  $.ajax({
    type: "POST",
    url: "/member/searchPassword",
    contentType: "application/json",
    data:JSON.stringify({
      name: userName,
      email: userEmail
    }),
    success: function(data){
      const state = data.state;
      if( state == "0" ){
        $('#find-password-username').val("");
        $('#find-password-useremail').val("");
        $('#find-password-fail-button').click();
      }else if( state == "1" ){
        $('#find-password-username').val("");
        $('#find-password-useremail').val("");
        $('#user-password-target').val(data.memberNo);
        $('#find-password-success-button').click();
      }
    },
    error: ()=>{
      alert("비밀번호 찾기 에러");
    }
  });
}

// 비밀번호 변경 모달
function changePassword(){
  const firstPassword = $('#password-first').val();
  const secondPassword = $('#password-second').val();
  const memberNo = $('#user-password-target').val();

  console.log(memberNo);
  if( firstPassword !== secondPassword ){
    alert("비밀번호가 일치하지 않습니다. 다시 확인해주세요.");
    $('#password-second').focus();
    return;
  }

  $.ajax({
    type: "POST",
    url: "/member/changePassword",
    contentType: "application/json",
    data: JSON.stringify({
      no: memberNo,
      password: secondPassword
    }),
    success: function(data){
      console.log(data);
      $('#password-first').val("");
      $('#password-second').val("");
      $('#user-password-target').val("");
      $('#change-password-back-button').click();
    },
    error: ()=>{
      alert("비밀번호 변경 에러");
    }

  })

}

// 로그인 결과 확인
function checkLogin( data ){
  const state = data.state;
  if( state == "0" ){
    location.href = "main";
  }else if( state == "1" ){
    alert("탈퇴된 회원입니다.");
  }else if( state == "3" ){
    alert("제재된 회원입니다.")
  }else if( state == "99" ){
    alert("이메일 또는 암호가 맞지 않습니다.");
  }else if( state == "4" ){
    
  }
}