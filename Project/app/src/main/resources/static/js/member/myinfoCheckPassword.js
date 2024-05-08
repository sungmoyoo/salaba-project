function fn_save(){ // submit하기 전에 확인하는 옵션

    var password = frm.password.value;

    if(password == ""){
      alert("비밀번호를 입력하세요.");
      frm.password.focus();
      return;
    }

    frm.submit();
  }
