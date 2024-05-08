function fn_save(){

    var name = frm.name.value;
    var email = frm.email.value;

    if(name == ""){
      alert("이름을 입력하세요.");
      frm.name.focus();
      return;
    }

   if(email == ""){
      alert("이메일을 입력하세요.");
      frm.email.focus();
      return;
    }

    frm.submit();
    }