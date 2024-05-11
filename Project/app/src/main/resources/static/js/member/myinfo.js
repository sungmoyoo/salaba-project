     const Tel = (target) => {
     target.value = target.value
       .replace(/[^0-9]/g, '')
       .replace(/^(\d{3})(\d{4})(\d{4})$/, `$1-$2-$3`);
    }

    function fn_showChangePassword(){
      $("#passwordChangeTable").show();
    }

    function fn_passwordSave(){
      var password = frm.password.value;
      var repassword = frm.repassword.value;
      if(password == ""){
        alert("비밀번호를 입력하세요.");
        frm.password.focus();
        return;
      }
      if(repassword == ""){
        alert("비밀번호 확인을 입력하세요.");
        frm.repassword.focus();
        return;
      }
      if(password != repassword){
        alert("비밀번호가 일치하지 않습니다.");
        return;
      }
      frm.action = "/member/myInfoChangePasswordSave";
      frm.submit();
    }

    function fn_save(){
      if(frm.oldNickname.value != frm.nickname.value){
        alert("닉네임 중복체크를 하시기 바랍니다.");
        frm.nickname.focus();
        return;
      }

      if(frm.name.value == ""){
        alert("이름은 필수 입력 항목입니다.");
        frm.name.focus();
        return;
      }
      if(frm.nickname.value == ""){
        alert("닉네임은 필수 입력 항목입니다.");
        frm.nickname.focus();
        return;
      }
      if(frm.email.value == ""){
        alert("이메일은 필수 입력 항목입니다.");
        frm.email.focus();
        return;
      }
      if(frm.telNo.value == ""){
        alert("전화번호는 필수 입력 항목입니다.");
        frm.telNo.focus();
        return;
      }
      if(frm.birthday.value == ""){
        alert("생년월일은 필수 입력 항목입니다.");
        frm.birthday.focus();
        return;
      }
      if(frm.address.value == ""){
        alert("주소는 필수 입력 항목입니다.");
        frm.address.focus();
        return;
      }

      frm.action = "/member/myinfoUpdate";
      frm.submit();
    }

    function fn_nickCheck(){
      frm.action = "/member/myinfoNickNameCheck";
      frm.submit();
    }

    //선호사항 편집 버튼 클릭 시 호출되는 함수
    function fn_mytheme(){
      frm.method = "get";
      frm.action = "/member/mytheme";
      frm.submit();
    }
    //회원탈퇴 버튼 클릭 시 호출되는 함수
    function fn_memberDelete(){
       frm.method = "get";
      frm.action = "/member/delete";
      frm.submit();
    }