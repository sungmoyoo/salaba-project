$(document).ready(function(){
  sideMenuActiveCommentHistory();
  activePageNav();
});

// SideMenu Active
function sideMenuActiveCommentHistory(){
  $('#sideMenu-nav a.active').removeClass('active');
  const sideMenu = $('#sideMenu-WriteHistory-Reply');
  $('#sideMenu-collapse-writeHistory').collapse('show');
  sideMenu.addClass('active');
}

// 페이지네이션 액티브
function activePageNav(){
  let pageNumber = pageNo;
  $('.pagination').find('a').each(function(){
      if($(this).text() === pageNumber.toString()){
          $(this).addClass('active');
      }
  });
}

// 작성 댓글이 삭제된 게시글인지 체크
function checkBoard(obj){
  const boardNo = $(obj).parent().find("#boardNo").val();
  const categoryNo = $(obj).parent().find("#categoryNo").val();
  console.log(boardNo);
  console.log(categoryNo);
    $.ajax({
      url: "/member/boardStateCheck",
      type: "post",
      data: {
        boardNo:boardNo
        ,categoryNo:categoryNo
      },
      success: function(data) {
        if(data == "1"){
          Swal.fire({
            icon: "error",
            title: "삭제된 게시글입니다."
          });
        }else{
          location.href = "/board/view?boardNo="+boardNo+"&categoryNo="+categoryNo;
        }
      }
    });
}