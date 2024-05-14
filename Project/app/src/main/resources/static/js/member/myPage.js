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
        alert("비밀번호가 맞지 않습니다.");
      }
    },
    error: ()=>{
      alert("비밀번호 체크 에러");
    }
  })
});
