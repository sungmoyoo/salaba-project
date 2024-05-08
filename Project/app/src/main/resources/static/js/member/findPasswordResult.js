function fn_save(){
    var password = cps.password.value;
    var repassword = cps.repassword.value;
    if(password == ""){
      alert("비밀번호를 입력하세요.");
      cps.password.focus();
      return;
    }
    if(repassword == ""){
      alert("비밀번호 확인을 입력하세요.");
      cps.repassword.focus();
      return;
    }
    if(password != repassword){
      alert("비밀번호가 일치하지 않습니다.");
      return;
    }
    cps.submit();
  }