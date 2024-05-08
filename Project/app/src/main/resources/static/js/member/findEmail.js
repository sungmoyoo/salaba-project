function fn_save(){

  var name = frm.name.value;
  var birthday = frm.birthday.value;

  if(name == ""){
    alert("이름을 입력하세요.");
    frm.name.focus();
    return;
  }

  if(birthday == ""){
    alert("생년월일을 입력하세요.");
    frm.birthday.focus();
    return;
  }

  frm.submit();
  }