function movePage(gbn){
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

// 작성 댓글이 삭제된 게시글인지 체크
function checkBoard(obj){
  const boardNo = $(obj).parent().find("#boardNo").val();
  const categoryNo = $(obj).parent().find("#categoryNo").val();

    $.ajax({
      url: "/member/boardStateCheck",
      type: "post",
      data: {
        boardNo:boardNo
        ,categoryNo:categoryNo
      },
      success: function(data) {
        if(data == "1"){
          alert("삭제된 게시글입니다.");
        }else{
          location.href = "/board/view?boardNo="+boardNo+"&categoryNo="+categoryNo;
        }
      }
    });
  }

// // 검색
//     // 페이지가 로드될 때 저장된 검색어가 있는지 확인 -> 있으면 검색창에 남겨두기
//   window.addEventListener('DOMContentLoaded', (event) => {
//     initSlider();
//     var savedKeyword = localStorage.getItem('searchKeyword');
//     if (savedKeyword) {
//       document.getElementById('bsearch-input').value = savedKeyword;
//       // 검색어를 가져왔으면 -> 다시 로컬 스토리지에서 삭제
//       localStorage.removeItem('searchKeyword');
//     }
//   });

//   // 검색 버튼 클릭 시 검색어를 로컬 스토리지에 저장
//   document.getElementById('bsearch-button').addEventListener('click', function(event) {
//     event.preventDefault();
//     var keyword = document.getElementById('bsearch-input').value;
//     localStorage.setItem('searchKeyword', keyword);
//     document.getElementById('bsearch-form').submit();
//   });