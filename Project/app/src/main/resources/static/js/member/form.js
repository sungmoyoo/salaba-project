function fn_save(){ // submit하기 전에 확인하는 옵션

    var email = "";
    var email1 = $("#email1").val();
    var email2 = $("#email2").val();
    var email3 = $("#email3").val();

    if(email3 == ""){
      email = email1 + "@" + email2;
    }else{
      email = email1 + "@" + email3;
    }
    $("#email").val(email);

    var name = frm.name.value;
    var nickname = frm.nickname.value;
    var birthday = frm.birthday.value;
    var email = frm.email.value;
    var password = frm.password.value;
    var repassword = frm.repassword.value;

    if(name == ""){
      alert("이름을 입력하세요.");
      frm.name.focus();
      return;
    }
    if(nickname == ""){
      alert("닉네임을 입력하세요.");
      frm.nickname.focus();
      return;
    }
    if(birthday == ""){
      alert("생년월일을 입력하세요.");
      frm.birthday.focus();
      return;
    }
    if(email == ""){
      alert("이메일을 입력하세요.");
      frm.email.focus();
      return;
    }
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

    frm.submit();
  }

  function fn_emailChange(obj){
    var selVal = $(obj).val();

    if(selVal == ""){
      $("#email2").show();
    }else{
      $("#email2").hide();
    }
  }