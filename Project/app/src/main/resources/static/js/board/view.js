"use strict";


const reportForm = $("#report-form");
let boardNo = $("#boardNum").text();  // jQuery를 사용하여 data 속성에서 boardNo 추출: 게시글 번호

//-------------------------------------댓글-------------------------------------
//댓글 작성하기
$("#addCommentBtn").click(function (e) {
  e.preventDefault(); // 기본 이벤트 방지
  let input = $("#comment-content"); // 댓글 내용
  let content = input.val(); // 입력 필드에 값 가져오기
  if (content != "") {
    // 댓글 내용이 비어있지 않은 경우
    $.ajax({
      url: "/board/comment/add", // 요청할 서버의 URL
      type: "POST", // HTTP 요청 방식
      dataType: "json", // 서버에서 반환되는 데이터 타입
      data: {
        // 서버로 보낼 데이터
        boardNo: boardNo,
        content: content,
      },
      success: function (data) {
        let newComment = $(`<div class="comment-container">
                              <div class="comment">
                                <div class="textContent comment-text">
                                  <span class="targetNo commentNo" hidden>${data.commentNo}</span>
                                  <img class="profile-img1" height='40px' src='/img/default-photo.jpeg'></a>
                                  <span class="nickname">${data.writer.nickname}</span>
                                  <span class="comment-date">${data.createdDate}</span><br>
                                  <span class="commentContent">${data.content}</span><br>
                                </div>
                                <div class="button-container1">
                                  <button class="del"><i class="fa-regular fa-trash-can"></i></button> <!--삭제 버튼 아이콘-->
                                  <button class="modi">수정</button>
                                  <button type="button" class="report-btn comment-report-btn">신고</button><br>
                                </div>
                              </div>
                                <div class="reply-box"></div>
                            </div>`);
        $("#comment-box").append(newComment); // 새 댓글 추가
        input.val(""); // 새 댓글 입력 필드 비우기
        newComment.click(addReplyForm);
        newComment.children().find(".del").click(deleteComment);
        newComment.children().find(".modi").click(modifyComment);
      },
      error: function () {
        // 오류 메시지 
        alert("권한이 없습니다.");
      }
    });
  } else {
    // 내용이 비어있는 경우
    alert("내용을 작성하세요");
  }
});


//모든 댓글의 삭제버튼에 액션을 추가(새로 추가된 폼애는 적용안됨)
$(".del").click(deleteComment);

//모든 댓글의 수정버튼에 액션을 추가(새로 추가된 폼애는 적용안됨)
$(".modi").click(modifyComment);


//-----------------답글폼 준비하기-----------------
const replyForm = $("#replyForm");
replyForm.hide()
// 
replyForm.find('#reply-content').click(function(e) {
  e.stopPropagation();
})
replyForm.find('#addReplyBtn').click(addReply)



// 답글 달기 버튼  --- 오류**

function addReply(event) {
  event.stopPropagation();
  let commentNo = replyForm.find("input[name='commentNo']").val();
  let replyContent = replyForm.find("textarea[name='content']").val();

  // console.log("commentNo:", commentNo);
  // console.log("replyContent:", replyContent);
  
  // AJAX 요청
  // 빈답글 작성 금지
  if (replyContent != "") {
    console.log("dsfas",commentNo, replyContent);
    $.ajax({
      url: "/board/reply/add",
      type: "POST",
      dataType: "json",
      data: {
        commentNo: commentNo,
        content: replyContent
      },
      success: function (data) {
        let newReply = $(`<div>
                          <div class="reply">
                            <div>
                            <div class="textContent reply-text">
                              <span class="targetNo replyNo" hidden>${data.replyNo}</span>
                              <!--프로필사진-->
                              <img class="profile-img2"  height='40px' src='/img/default-photo.jpeg'></a>
                              <i class="fa-solid fa-reply"></i>
                              <span class="nickname">${data.writer.nickname}</span>
                              <span class="reply-date">${data.createdDate}</span><br>
                              <span class="replContent">${data.content}</span><br>
                            </div>
                            <div class="button-container2">
                              <button class="del2"><i class="fa-regular fa-trash-can"></i></button>
                              <button class="modi2">수정</button>
                              <button type="button" class="report-btn reply-report-btn">신고</button><br>
                            </div>
                            </div>
                          </div>
                          </div>`);
        replyForm.parent().append(newReply);
        // console.log(newReply);
        replyForm.hide();
        replyForm.find('#reply-content').val("");
        newReply.find('.button-container2').find(".del2").click(deleteReply);
        newReply.find('.button-container2').find(".modi2").click(modifyReply);
      },
      error: function () {
        window.alert("권한이 없습니다.");
      },
    });
  } else {
    alert("내용을 작성하세요");
  }
  replyForm.hide();
  $("#replyContent").val(""); // 내용 지우기

}

// 댓글 클릭시 답글폼 추가하기
$('.comment-container').click(addReplyForm)

// 답글폼 추가하기
function addReplyForm(e) {
  e.stopPropagation();
  var replyBox = $(this).find('.reply-box');
  console.log(replyBox);
  // 클릭된 댓글 아래로 답글 폼을 이동
  replyBox.append(replyForm);
  
  replyForm.toggle();
  
  
  // 클릭된 댓글의 번호 가져오기
  var commentNo = $(this).find('.commentNo').text();
  replyForm.find("input[name='commentNo']").val(commentNo);
};




//답글 삭제
$(".del2").click(deleteReply);

//답글 수정
$(".modi2").click(modifyReply);

function deleteComment(e) {
  console.log($(this));
  let commentDiv = $(this).closest('.comment'); // 삭제 버튼의 가장 가까운 상위 '.comment' 요소를 찾습니다.
  let commentNo = commentDiv.find(".commentNo").text(); // '.comment' 내부에서 '.commentNo'를 찾습니다.
  console.log(commentNo);
  $.ajax({
    url: "/board/comment/delete",
    type: "GET",
    data: { commentNo: commentNo },
    success: () => {
      commentDiv.html("<span>삭제된 댓글입니다.</span>");
    },
    error: () => alert("권한이 없습니다."),
  });
}

// 댓글 수정 기능
function modifyComment(e) {
  e.stopPropagation();
  let commentDiv = $(this).parent();
  let commentNo = commentDiv.find('.commentNo').text();
  let oldContent = commentDiv.find(".commentContent").text();
  let modifyForm = $(".commentModifyForm");

  // 기존의 수정 폼 제거 조건 검사
  if (modifyForm.length != 0 && modifyForm.find("input[type='hidden']").val() != commentNo) {
    modifyForm.remove();
  }

  // 수정 폼 생성 및 삽입
  if ($(".commentModifyForm").length == 0) {
    const updateFormHTML = $(`<form class="commentModifyForm">
                                <input type="hidden" value="${commentNo}">
                                <textarea class="newContent">${oldContent}</textarea>
                                <button type="button" class="comtModiConfirm">수정</button>
                                <button type="button" class="comtModiCancel">취소</button>
                            </form>`);

    updateFormHTML.insertAfter(commentDiv); // 댓글 바로 다음에 폼 삽입

    // 수정 확인 버튼 클릭 이벤트
    updateFormHTML.find(".comtModiConfirm").click(function (e) {
      e.stopPropagation();
      let newContent = updateFormHTML.find(".newContent").val();
      if (newContent.trim() !== "") { // 공백만 있는 내용을 제거
        $.ajax({
          url: "/board/comment/update",
          type: "POST",
          dataType: "json",
          data: {
            commentNo: commentNo,
            content: newContent
          },
          success: function (data) {
            updateFormHTML.remove();
            commentDiv.find(".commentContent").text(newContent);
          },
          error: function (error) {
            window.alert("수정 권한이 없습니다.");
            updateFormHTML.remove();
          }
        });
      } else {
        alert("내용을 작성하세요.");
      }
    });

    // 취소 버튼
    updateFormHTML.find(".comtModiCancel").click(function (e) {
      e.stopPropagation();
      updateFormHTML.remove();
    });
  }
}


/* 답글 삭제 */
function deleteReply(e) {
  e.stopPropagation();
  let replyDiv = $(this).closest('.reply');
  let replyNo = replyDiv.find(".replyNo").text();
  console.log(replyNo);
  $.ajax({
    url: "/board/reply/delete",
    type: "GET",
    dataType: "json",
    data: { replyNo: replyNo },
    success: function () {
      replyDiv.remove();
    },
    error: function () {
      alert("삭제 권한이 없습니다.");
    },
  });
}

/* 답글 수정 */
function modifyReply(e) {
  e.preventDefault();
  let replyDiv = $(this).closest('.reply');
  let replyNo = replyDiv.find(".replyNo").text();
  let oldContent = replyDiv.find(".replContent").text();
  let modifyForm = replyDiv.find(".replyModifyForm");

  // 기존 폼이 있고, 현재 답글 번호와 다르면 기존 폼을 제거
  if (modifyForm.length > 0 && modifyForm.find("input[type='hidden']").val() != replyNo) {
    modifyForm.remove();
    modifyForm = $(); // 폼 객체를 초기화
  }

  // 수정 폼이 없으면 새로 생성
  if (modifyForm.length === 0) {
    modifyForm = $(`<form class="replyModifyForm">
                      <input type="hidden" value="${replyNo}">
                      <textarea class="newContent">${oldContent}</textarea>
                      <button type="button" class="replModiConfirm">수정</button>
                      <button type="button" class="replModiCancel">취소</button>
                    </form>`);

    replyDiv.append(modifyForm); // 답글 div에 폼을 추가

    // 수정 확인
    modifyForm.find(".replModiConfirm").on("click", function () {
      let newContent = modifyForm.find(".newContent").val();
      if (newContent.trim() !== "") {
        $.ajax({
          url: "/board/reply/update",
          type: "POST",
          dataType: "json",
          data: { replyNo: replyNo, content: newContent },
          success: function () {
            replyDiv.find(".replContent").text(newContent);
            modifyForm.remove();
          },
          error: function () {
            alert("수정 권한이 없습니다.");
            modifyForm.remove();
          }
        });
      } else {
        alert("내용을 작성하세요");
      }
    });

    // 수정 취소
    modifyForm.find(".replModiCancel").click(function () {
      modifyForm.remove();
    });
  }
}

/*************************************************************************************/

//추천수
// $(document).ready(function() {
  // 로그인 여부 확인
  let isLoggedIn = $("#loginUser").length > 0;
  
  // 로그인 필요 알림 함수
  function requireLogin() {
      alert('로그인이 필요합니다.');
  }

  let likeButton = $("#likeButton");
  let myLikeCount = parseInt($("#myLikeCount").text()); // 내 추천수(0 or 1)
  let likeCount = parseInt($("#likeCount").text()); // 초기 전체 추천수
  let initialMyLikeCount = myLikeCount; // 초기 추천수 저장

  console.log(likeCount);

  function debounce(func, wait) {
    let timeout;

    return function executedFunction(...args) {
        const later = () => {
            clearTimeout(timeout);
            func(...args);
        };

        clearTimeout(timeout);
        timeout = setTimeout(later, wait);
    };
};

const processChanges = debounce(function() {
  let url = myLikeCount > 0 ? "/board/like" : "/board/unlike";
    console.log(initialMyLikeCount, myLikeCount)
      if (initialMyLikeCount != myLikeCount) {
          $.ajax({
              type: "POST",
              url: url,
              data: {
                  boardNo: boardNo
              },
              success: function() {
                initialMyLikeCount = initialMyLikeCount == 1 ? 0 : 1;
              },
              error: function() {
                // alert('실패');
              }
          });
      }
}, 2000);


  likeButton.click(function () {
      console.log('추천!')
      if (!isLoggedIn) {
          requireLogin();
          return;
      }
      
      if (myLikeCount === 1) { // 0 또는 1
        myLikeCount = 0;
        likeCount -= 1;
        likeButton.html(`<span id="likeCount">${likeCount}</span><i class="fa-regular fa-heart"></i>`); // 추천 버튼을 클릭했을 때 버튼을 빈 하트 아이콘으로 바꿔준다. 
      } else if (myLikeCount === 0) {
        myLikeCount = 1;
        likeCount += 1; 
        
        likeButton.html(`<span id="likeCount">${likeCount}</span><i class="fa-solid fa-heart"></i>`); 
        
      }
  });

  likeButton.click(processChanges);


  $(window).on("beforeunload", function () {
    
  });
// });

/**********************************************************************************************/

// 신고창: 모달 사용
$(document.body).on("click", ".report-btn", function (event) {
  console.log(event.target);
  let div = $(this).closest(".comment, .reply"); // 가장 가까운 comment 또는 reply 요소 찾기
  let targetNo = div.find(".targetNo").text();

  let targetType;
  if (div.hasClass("comment")) {
    targetType = 1;
  } else if (div.hasClass("reply")) {
    targetType = 2;
  } else {
    targetNo = boardNo;
    targetType = 0;
  }
  console.log("Target No:", targetNo);
  console.log("Target Type:", targetType);

  // 모달 내 신고 대상 번호와 타입을 업데이트
  $("#reportModal").find("input[name='targetNo']").val(targetNo);
  $("#reportModal").find("input[name='targetType']").val(targetType);

  // 모달 열기
  $("#reportModal").modal("show");
});

$("#submitBtn").click(function(e) {
  e.preventDefault();
  let formData = new FormData($("#reportForm")[0]);

  $.ajax({
      type: "POST",
      url: "/board/report/add",
      data: formData,
      processData: false,
      contentType: false,
      success: function(response) {
          alert("신고가 성공적으로 제출되었습니다.");
          window.location.href = "/board/main"; // 성공 시 메인 페이지로 이동
      },
      error: function(response) {
          alert(response.responseText); // 서버로부터의 응답 메시지를 경고창으로 표시
      }
  });
});


/************************************************************************************/

// 상단으로 올라가기
let scrollToTopBtn = document.getElementById("scrollToTopBtn2");

// 20px 내려가면 버튼이 보이도록 하기
window.onscroll = function() {
  if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {
    scrollToTopBtn.style.display = "block";
  } else {
    scrollToTopBtn.style.display = "none";
  }
};

// 상단으로 올리기
function scrollToTop() {
  window.scrollTo({
    top: 0,
    behavior: "smooth"
  });
}