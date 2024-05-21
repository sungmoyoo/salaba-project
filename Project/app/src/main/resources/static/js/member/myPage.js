let loginUser = session; // 세션 로그인 정보
const nationList = nationListModel;
let userPreference = [];

function sideMenuActiveMyPage(){
  $('#sideMenu-nav a.active').removeClass('active');
  const sideMenu = $('#sideMenu-MyPage');
  sideMenu.addClass('active');
}


$(document).ready(function(){
  sideMenuActiveMyPage(); // SideMenu Active
  myPageAuthorize(); // 마이페이지 접근
  chooseNation(); // 국가선택
  checkNickName(); // 닉네임 중복검사
  genderRadioButtonSet(); // 성별버튼 셋팅
  openPreferenceModal(); // 선호사항 Modal
  preferenceModalSetting(); // 선호사항 Modal 초기값 세팅
  previewPhoto(); // 사진 변경 바로 적용
  changePassword(); // 비밀번호 변경
  updateUserInfo(); // 저장 버튼 - 업데이트 처리
  memberWithdrawal() // 회원탈퇴
});

// 마이페이지 비밀번호 확인
function myPageAuthorize(){
  $('#checkPassword-button').on('click', function(){
    const password = $('#checkPassword').val();
    const memberNo = loginUser.no;
    console.log(password);
    console.log(memberNo);
    $.ajax({
      type: "POST",
      url: "/member/checkPassword",
      contentType: "application/json",
      data: JSON.stringify({
        no: memberNo,
        password: password
      }),
      success: function(result){
        if(result == 1){
          $('#checkPassword-container').hide();
          $('#myInformation-container').show();
        }else{
          Swal.fire({
            icon: "error",
            title: "비밀번호가 맞지 않습니다."
          });
        }
      },
      error: ()=>{
        Swal.fire({
          icon: "error",
          title: "비밀번호 체크 에러"
        });
      }
    })
  });
}


// 국가 선택
function chooseNation(){
  if(loginUser.nationNo == 0){
    $('#nationSelectedBox').text("국적을 선택하세요");
  }else{
    nationList.forEach(element => {
      if( element.nationNo == loginUser.nationNo ){
        $('#nationSelectedBox').text(element.nationName);
        return;
      }
    });
  }
}

// 국가 번호가져오기
function getNationNo(name){
  const nation = nationList.find(element => element.nationName === name);
  return nation ? nation.nationNo : null;
}

// 닉네임 중복검사
function checkNickName(){
  $('#nicknameCheck').on('click', function(){
    const nickName = $('#nicknameInput').val();
    console.log(nickName);
    if(nickName === ""){
      Swal.fire({
        icon: "error",
        title: "닉네임을 입력해주세요",
        confirmButtonText: "확인"
      }).then((result)=>{
        $('#nicknameInput').focus();
        return;
      });
    }
    if(!validateNickname(nickName)){
      Swal.fire({
        icon: "error",
        title: "공백불가,한글 및 영문 숫자 최소2글자 최대 20글자",
        confirmButtonText: "확인"
      }).then((result)=>{
        $('#nicknameInput').focus();
        return;
      });
    }

    $.ajax({
      type: "POST",
      url: "/member/checkNickName",
      data: {
        nickName: nickName
      },
      success: function(data){
        if(data == 0){
          Swal.fire({
            icon: "success",
            title: "사용 가능한 닉네임입니다."
          });
        }else{
          Swal.fire({
            icon: "error",
            title: "사용 중인 닉네임입니다.",
            confirmButtonText: "확인"
          }).then((result)=>{
            $('#nicknameInput').focus();
            return;
          });
        }
      },
      error: ()=>{
        Swal.fire({
          icon: "error",
          title: "닉네임 체크 에러",
          confirmButtonText: "확인"
        });
      }
    });
  });
}

// 닉네임 유효성 검사 ( 공백불가 , 한글영어 , 숫자 , 최소2글자 최대 20글자 )
function validateNickname(nickname) {
  const nickNameReg = /^[a-zA-Z가-힣0-9\\\\s]{2,20}$/;
  return nickNameReg.test(nickname);
}

// 성별 라디오버튼 설정
function genderRadioButtonSet(){
  if(loginUser.sex === "M"){
    $('#male').prop("checked", true);
  }else if(loginUser.sex === "W"){
    $('#female').prop("checked", true);
  }else{
    $('#none').prop("checked",true);
  }
}

// 선호사항 편집 modal 열기
function openPreferenceModal(){
  $('#userPreference-button').click(function(){
    $('#userPreferenceModal').modal('show');
  });
}
function preferenceModalSetting(){
  // 선호사항이 이미 선택되어 있는 경우 선택표시를 해줌
  if(loginUser.themes != null || loginUser.themes == ""){
    let userTheme = loginUser.themes;
    userTheme.forEach(element => {
      let no = '#themeNo'+element.themeNo;
      userPreference.push(element.themeNo); // 기존 선호사항 저장
      $(no).addClass('active');
      $(no).attr('aria-pressed', true);
    });
    console.log(userPreference);
  }
  
  // 선호사항 아이콘 클릭시 선택 표시
  $('.btn[data-toggle="button"]').on('click', function() {
    let isActive = $(this).hasClass('active');
    $(this).toggleClass('active');
    $(this).attr('aria-pressed', !isActive);
  });

  // 선호사항 Modal 저장버튼
  $('#save-user-preference').on('click', function(){
    userPreference = []; // 초기화
    $('.btn[data-toggle="button"]').each(function(){
      if ($(this).hasClass('active')) {
        let id = $(this).attr('id');
        let value = $('#'+id).attr('value');
        let name = $('#'+id+' span');
        userPreference.push(Number(value));
      }
    });
    console.log(userPreference);
  });

  // 선호사항 Modal 취소버튼
  $('#cancle-user-preference').on('click', function(){
    // 선택 했던 것들 모두 초기화
    document.querySelectorAll('.d-inline-flex a').forEach(element =>{
      element.classList.remove('active');
    });

    // 세션에 담겨있는 정보로 다시 설정
    if(loginUser.themes != null || loginUser.themes == ""){
      let userTheme = loginUser.themes;
      userTheme.forEach(element => {
        let no = '#themeNo'+element.themeNo;
        $(no).addClass('active');
        $(no).attr('aria-pressed', true);
      });
    }
  })
}

// 사진 미리보기
function previewPhoto(event){
  $('#inputUserPhoto').change(function(){
    if(this.files && this.files[0]){
      let reader = new FileReader();
      reader.onload = function(e){
        $('#userPhoto').attr('src', e.target.result);
      }
      reader.readAsDataURL(this.files[0]);
    }
  })
}

// 사진 파일명 가져오기
function getFileName(){
  const file = $('#inputUserPhoto')[0];
  if(file.files.length > 0){
    return file.files[0].name;
  }
  else{
    return "";
  }
}


// 비밀번호 변경
function changePassword(){
  // 비밀번호 변경버튼
  $('#changePassword-open-button').click(function(){
    let buttonText = $(this).text();
    if(buttonText === "변경"){
      $(this).text("취소");
    }else{
      $(this).text("변경");
      $('#changePassword-passwordInput').val("");
      $('#changePassword-passwordCheck').val("");
    }
  });

  // 비밀번호 변경
  $('#passwordChange-button').click(function(){
    const firstPasswordInput = $('#changePassword-passwordInput').val();
    const secondPasswordInput = $('#changePassword-passwordCheck').val();
  
    if( firstPasswordInput == "" || secondPasswordInput == "" ){
      Swal.fire({
        icon: "error",
        title: "변경할 비밀번호를 입력해주세요",
        confirmButtonText: "확인"
      }).then((result)=>{
        $('#changePassword-passwordInput').focus();
        return;
      });
    }
  
    if( firstPasswordInput != secondPasswordInput ){
      Swal.fire({
        icon: "error",
        title: "비밀번호가 일치하지 않습니다",
        confirmButtonText: "확인"
      }).then((result)=>{
        $('#changePassword-passwordInput').focus();
        return;
      });
    }
  
    if( !validatePassword(secondPasswordInput) ){
      Swal.fire({
        icon: "error",
        title: "대,소문자,숫자,특수문자를 포함한 8글자 이상 20글자 이하로 설정해주세요",
        confirmButtonText: "확인"
      }).then((result)=>{
        $('#changePassword-passwordInput').focus();
        return;
      });
    }
  
    $.ajax({
      type: "POST",
      url: "/member/changePassword",
      contentType: "application/json",
      data: JSON.stringify({
        no: loginUser.no,
        password: secondPasswordInput
      }),
      success: function(data){
        $('#changePassword-passwordCheck').val("");
        $('#changePassword-passwordInput').val("");
        $('#changePassword-open-button').click();
        Swal.fire({
          icon: "success",
          title: data,
          confirmButtonText: "확인"
        }).then((result)=>{
          location.href = "/member/myPage?memberNo="+loginUser.no;
        });
      }
    });
  });
}

// 비밀번호 유효성 검사
function validatePassword(password){ // 대,소문자,숫자,특수문자를 포함한 8글자 이상 20글자 이하
  const passwordReg = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*()_+])[A-Za-z\d!@#$%^&*()_+]{8,20}$/;
  return passwordReg.test(password);
}

// 회원정보 업데이트
function updateUserInfo(){
  // 저장버튼 눌렀을 때
  $('#myPage-UserInfo-Update').on('click',function(){
    let themes = [];
    userPreference.forEach(item =>{
      themes.push(item);
    });

    const formData = new FormData();
    if( $('#inputUserPhoto')[0].files[0] != null && $('#inputUserPhoto')[0].files[0] != undefined ){
      formData.append('file', $('#inputUserPhoto')[0].files[0]); // 파일 추가
    }

    if( $('input[name="gender"]:checked').val() != "none" ){
      formData.append('sex', $('input[name="gender"]:checked').val());
    }

    if( $('#telInput').val() != "" || $('#telInput').val().length > 0 ){
      formData.append('telNo', $('#telInput').val());
    }

    if( $('.form-select').val() != null ){
      formData.append('nationNo', $('.form-select').val());
    }

    if( $('#addressInput').val() != "" || $('#addressInput').val() != null ){
      formData.append('address', $('#addressInput').val());
    }

    formData.append('no', loginUser.no);
    formData.append('name', $('#nameInput').val());
    formData.append('nickname', $('#nicknameInput').val());
    
    if(themes.length > 0){
      formData.append('themes', themes);
    }
    if(getFileName() != ""){
      formData.append('photo', getFileName());
    }

    $.ajax({
      type: "POST",
      url: "/member/updateUserInfo",
      processData: false, // 데이터 처리 방식 설정
      contentType: false, // 컨텐츠 타입 설정
      data: formData, // 폼 데이터 전송
      success: function(data){
        Swal.fire({
          icon: "success",
          title: data
        });
      },
      error: ()=>{
        Swal.fire({
          icon: "error",
          title: "회원정보 수정 에러"
        });
      }
    });
  });
}

// 회원 탈퇴
function memberWithdrawal(){
  $('#memberWithdrawal-button').on('click',function(){
    $.ajax({
      type: "POST",
      url: "/member/withdrawal",
      data:{
        memberNo: loginUser.no
      },
      success: function(data){
        if(data == 1){
          Swal.fire({
            title: "회원탈퇴되었습니다.",
            icon: "success",
            confirmButtonText: "확인"
          }).then((result)=>{
            if(result.isConfirmed) {
              location.href = "/main";
            }
          });
        }
      },
      error:()=>{
        Swal.fire({
          icon: "error",
          title: "회원탈퇴 에러"
        });
        return;
      }
    });
  });
}
