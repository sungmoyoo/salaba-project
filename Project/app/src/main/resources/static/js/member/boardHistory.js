function fn_goPage(gbn){
   if(gbn == '1'){
     frm.method = "get";
     frm.action = "/member/boardHistory";
     frm.submit();
  }else{
     frm.method = "get";
     frm.action = "/member/commentHistory";
     frm.submit();
  }
}