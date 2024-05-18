(function() {
  let mainPageNav = document.getElementById('mainPage');
  let boardPageNav = document.getElementById('boardPage');

  if (window.location.href.includes('board')) {
    boardPageNav.classList.add("active");
    mainPageNav.classList.remove('active');
  } else {
    mainPageNav.classList.add('active');
    boardPageNav.classList.remove("active");
  }
})()

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

// logout
function logout(){
  $.ajax({
    type: "POST",
    url: "/auth/logout",
    success: function(){
      location.href = "main";
    },
    error: ()=>{

    }

  });
}


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
    error:()=>{
      
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

// 로그인 결과 확인
function checkLogin( data ){
  const state = data.state;
  if( state == "0" ){
    location.href = "main";
  }else if( state == "1" ){
    alert("탈퇴된 회원입니다.");
  }else if( state == "3" ){
    alert("제재된 회원입니다.")
  }else if( state == "9" ){
    alert("이메일 또는 암호가 맞지 않습니다.");
  }else if( state == "4" ){
    
  }
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

// 회원가입
document.addEventListener('DOMContentLoaded', function(){
  'use strict';
  // 회원가입 모달이 열릴 때 유효성 검사 수행
  $(document).on('shown.bs.modal', '#joinMemberModal', function(){
    const forms = document.querySelectorAll('.needs-validation');
    let emailinput; // email
    let nickNameinput; // 닉네임
    let passwordinputFirst; // 비밀번호
    let passwordinputSecond; // 비밀번호 확인

    let checkedNickNameFlag = false; // 닉네임 중복 검사 시행여부 flag
    let checkedEmailFlag = false; // 이메일 중복 검사 시행여부 flag

    // 이메일 유효성 검사( 이메일 형식 체크 )
    function validateEmail(email) {
      const emailReg = /[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]+\.[a-zA-Z]+$/i;
      return emailReg.test(email);
    }

    // 닉네임 유효성 검사 ( 공백불가 , 한글영어 , 숫자 , 최소2글자 최대 20글자 )
    function validateNickname(nickname) {
      const nickNameReg = /^[a-zA-Z가-힣0-9\\\\s]{2,20}$/;
      return nickNameReg.test(nickname);
    }

    // 비밀번호 유효성 검사
    function validatePassword(password){ // 대,소문자,숫자,특수문자를 포함한 8글자 이상 20글자 이하
      const passwordReg = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*()_+])[A-Za-z\d!@#$%^&*()_+]{8,20}$/;
      return passwordReg.test(password);
    }

    Array.prototype.slice.call(forms).forEach(function (form){

      form.addEventListener('submit', function(event){

        if( !checkedNickNameFlag ){ // 닉네임 중복검사를 하지 않은경우
          alert("닉네임 중복 검사를 해주세요.");
          $('#joinMember-nickname').focus();
          return;
        }

        if( !checkedEmailFlag ){ // 이메일 중복검사를 하지 않은경우
          alert("이메일 중복 검사를 해주세요.");
          $('#joinMember-email').focus();
          return;
        }
        console.log(nickNameinput.val());
        // 닉네임 유효성 검사
        if(!validateNickname(nickNameinput.val()) && nickNameinput.val() != ""){
          event.preventDefault();
          event.stopPropagation();
          nickNameinput.addClass('is-invalid');
          nickNameinput.addClass('was-validated');
          return;
        }else{
          nickNameinput.removeClass('is-invalid');
          nickNameinput.addClass('is-valid');
        }

        // email 유효성 검사
        if(!validateEmail(emailinput.val()) && emailinput.val() != ""){
          event.preventDefault();
          event.stopPropagation();
          emailinput.addClass('is-invalid');
          emailinput.addClass('was-validated');
          return;
        }else{
          emailinput.removeClass('is-invalid');
          emailinput.addClass('is-valid');
        }
        
        // 비밀번호 유효성 검사
        if( passwordinputFirst.val() != passwordinputSecond.val() || (passwordinputFirst.val() == "" || passwordinputSecond.val() == "") ){
          event.preventDefault();
          event.stopPropagation();
          passwordinputFirst.addClass('is-invalid');
          passwordinputFirst.addClass('was-validated');
          passwordinputSecond.addClass('is-invalid');
          passwordinputSecond.addClass('was-validated');
          return;
        } else if(!validatePassword(passwordinputSecond.val())){
          event.preventDefault();
          event.stopPropagation();
          passwordinputFirst.addClass('is-invalid');
          passwordinputFirst.addClass('was-validated');
          passwordinputSecond.addClass('is-invalid');
          passwordinputSecond.addClass('was-validated');
          return;
        }else{
          passwordinputFirst.removeClass('is-invalid');
          passwordinputFirst.addClass('is-valid');
          passwordinputSecond.removeClass('is-invalid');
          passwordinputSecond.addClass('is-valid');
        }

        // html5 기본 유효성 검사
        if(!form.checkValidity()){
          event.preventDefault();
          event.stopPropagation();
        }

        form.classList.add('was-validated');

        if(!form.checkValidity()){
          return;
        }
        
        event.preventDefault();

        const member = {
          name: $('#joinMember-name').val(),
          nickname: nickNameinput.val(),
          birthday: $('#joinMember-birth').val(),
          email: emailinput.val(),
          password: passwordinputSecond.val()
        };

        console.log(member);

        $.ajax({
          type: "POST",
          url: "/member/addMember",
          contentType: "application/json",
          data: JSON.stringify(member),
          success: function(data){
            console.log(data);
            console.log(typeof data);
            if( data == 1 ){
              initJoinMemberModal();
              $('#back-button-in-memberjoin').click();
              alert("회원가입이 완료되었습니다.");
            }else{
              alert("회원가입 오류");
            }
          },
          error: ()=>{
            alert("회원가입 에러");
          }
        });

      }, false)
    });

    $('#joinMember-button').on('click', function(){
      emailinput = $('#joinMember-email');
      nickNameinput = $('#joinMember-nickname');
      passwordinputFirst = $('#joinMember-password-first');
      passwordinputSecond = $('#joinMember-password-second');
    })

    // 닉네임 중복 검사
    $('#nickNameCheck-addon').on('click', function(){
      const nickName = $('#joinMember-nickname').val();
      $.ajax({
        type: "POST",
        url: "/member/checkNickName",
        data: {
          nickName: nickName
        },
        success: function(data){
          if( data == 0 ){
            checkedNickNameFlag = true;
            alert("사용 가능한 닉네임입니다.");
            $('#joinMember-nickname').addClass('is-valid');
            $('#joinMember-nickname').addClass('was-validated');
          }
          else{
            alert("사용 중인 닉네임입니다.");
            $('#joinMember-nickname').focus();
          }
        },
        error: ()=>{
          alert("닉네임 중복검사 에러");
        }
      });
    });

    // 이메일 중복 검사
    $('#emailCheck-addon').on('click', function(){
      const email = $('#joinMember-email').val();
      $.ajax({
        type: "POST",
        url: "/member/checkEmail",
        data: {
          email: email
        },
        success: function(data){
          if( data == 0 ){
            checkedEmailFlag = true;
            alert("사용 가능한 이메일입니다.");
            $('#joinMember-email').addClass('is-valid');
            $('#joinMember-email').addClass('was-validated');
          }
          else{
            alert("사용 중인 이메일입니다.");
            $('#joinMember-email').focus();
          }
        },
        error: ()=>{
          alert("이메일 중복검사 에러");
        }
      })
    })

  });
});

// 회원가입모달 field 초기화
function initJoinMemberModal(){
  $('#joinMemberModal input[type="text"], #joinMemberModal input[type="date"], #joinMemberModal input[type="email"], #joinMemberModal input[type="password"]').val('');
  const forms = document.querySelectorAll('.needs-validation');
  forms.forEach(function(form){
    form.classList.remove('was-validated');
    form.classList.remove('is-valid');
  });
}