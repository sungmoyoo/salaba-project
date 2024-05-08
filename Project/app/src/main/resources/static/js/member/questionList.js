function fn_save(){

    var title = frm.title.value;
    var content = frm.content.value;
    var questionFiles = frm.questionFiles.value;

    if(title == ""){
      alert("제목을 입력하세요.");
      frm.title.focus();
      return;
    }
    if(content == ""){
      alert("메세지를 입력하세요.");
      frm.content.focus();
      return;
    }
    if(questionFiles == ""){
      alert("첨부파일을 등록해주세요.");
      return;
    }
    frm.submit();
  }