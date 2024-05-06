"use strict";

let replyForm = $("#replyForm");
let reportForm = $("#report-form");
let boardNo = $("#boardNum").text(); // 게시글 번호


document.addEventListener("DOMContentLoaded", function() {
  // 댓글이 없는 게시글인지 확인
  var commentBox = document.getElementById("comment-box");
  if (commentBox && commentBox.children.length === 0) {
    commentBox.style.display = "none"; // 댓글이 없는 경우 comment-box 숨기기
  }
});


//댓글 작성
$("#addCommentBtn").click(function (e) {
  e.preventDefault(); // 기본 이벤트 방지
  let input = $("#commentContent"); // 댓글 내용
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
        let newComment = $(`<div>
                                    <div class="comment" data-no="${data.commentNo}">
                                        <div class="comment-text">
                                          <span >${data.writer.nickname}</span>
                                          <span class="commentContent" data-th-text="*">${data.content}</span>
                                          <span data-th-text="*">${data.createdDate}</span>
                                        </div>
                                        <button class="del">삭제</button>
                                        <button class="modi">수정</button>
                                        <button class="report-btn comment-report-btn">신고</button>
                                    </div>
                                </div>`);
        $("#comment-box").append(newComment); // 새 댓글 추가
        input.val(""); // 새 댓글 입력 필드 비우기
        newComment.children().first().click(addReply);
        newComment.children().find(".del").click(deleteComment);
        console.log("Delete button clicked");

        newComment.children().find(".modi").click(modifyComment);

        // $('#box').find('.repo')
      },
      error: function () {
        // 오류 메시지 날리기
        alert("권한이 없습니다.");
      },
    });
  } else {
    // 내용이 비어있는 경우
    alert("내용을 작성하세요");
  }
});

//댓글 삭제
$("#comment-box").on("click", ".del", deleteComment);
console.log("Delete button clicked");


//댓글 수정
$(".modi").click(modifyComment);

//댓글 - 답글달기
$("#comment-box").on("click", ".comment-text", addReplyForm);

$("#addReplyBtn").click((event) => {
  let commentNo = replyForm.parent().attr("data-no");
  let replyContent = $("#reply-content").val();
  
    // AJAX 요청
  //빈댓글 작성 금지
  if (replyContent != "") {
    console.log(commentNo, replyContent);
    $.ajax({
      url: "/board/reply/add",
      type: "POST",
      dataType: "json",
      data: {
        commentNo: commentNo,
        content: replyContent
      },
      success: function (data) {
        console.log(data);
        // 기존의 답글달기 폼을 안보여준다.
        replyForm.addClass("form-hidden");
                  let newReply = $(`<div>
                                      <div class="reply">
                                        <div class="textContent comment-text">
                                            <span class="targetNo replyNo" hidden>${data.replyNo}</span>
                                            <span>${data.writer.nickname}</span>
                                            <span class="replContent">${data.content}</span>
                                            <span>${data.createdDate}</span>
                                            <button class="del2">삭제</button>
                                            <button class="modi2">수정</button>
                                            <button class="report-btn reply-report-btn">신고</button>
                                        </div>
                                      </div>
                                    </div>`);
        $(this).parents('.reply-box').append(newReply);
        console.log(newReply);

    
        newReply.find(".del2").click(deleteReply);
        newReply.find(".modi2").click(modifyReply);

      },
      error: function () {
        window.alert("권한이 없습니다.");
      },
    });
  } else {
    alert("내용을 작성하세요");
  }

  // replyForm.addClass("form-hidden");
  // $("#replyContent").val(""); // 내용 지우기
});


//답글 삭제
$(".del2").click(deleteReply);

//답글 수정
$(".modi2").click(modifyReply);

function deleteComment(e) {
  console.log($(this));
  let commentDiv = $(this).parent();
  let commentNo = commentDiv.children().find(".commentNo").text();
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
  let commentDiv = $(this).closest('.comment');
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


/*답글 달기*/
function addReplyForm(e) {
  e.stopPropagation(); // 이벤트 버블링 방지
  let commentContainer = $(e.currentTarget).closest('.comment');

  // 수정 폼이 활성화된 경우, 답글 폼을 표시하지 않음
  if (commentContainer.find('.commentModifyForm').length > 0) {
    console.log("수정 폼이 활성화되어 있어 답글 폼을 표시하지 않습니다.");
    return;
  }

  console.log("답글 폼 추가");
  
  // 댓글 번호를 추출하여 답글 폼의 hidden input에 설정
  var commentNo = commentContainer.find('.commentNo').text();
  replyForm.find('input[name="commentNo"]').val(commentNo);
  
  // 답글 폼을 해당 댓글의 'reply-box' 클래스를 가진 하위 요소에 삽입
  commentContainer.find('.reply-box').append(replyForm);
  
  // 답글 폼을 보이게 함
  replyForm.removeClass("form-hidden");
}

// 답글 폼 취소(닫기)
function cancelReplyForm() {
  replyForm.addClass("form-hidden");
  replyForm.find('textarea').val(''); 
}

$(document).ready(function() {
  $("#comment-box").on("click", ".comment-text", addReplyForm);
  $("#cancelReplyBtn").click(cancelReplyForm);
});



/* 답글 삭제 */
function deleteReply(e) {
  let replyDiv = $(this).parent();
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
    modifyForm.find(".replModiCancel").on("click", function () {
      modifyForm.remove();
    });
  }
}

/*************************************************************************************/

//추천수
let myLikeCount = parseInt($("#myLikeCount").text()); // 내 추천수(0 or 1)
let initialCount = myLikeCount;
console.log("myLikecount: ", myLikeCount);

// 로그인 여부 확인
let isLoggedIn = $("#loginUser").length > 0; // 예를 들어, 로그인 상태를 나타내는 DOM 요소의 존재 여부로 판단

// 로그인 필요 알림 함수
function requireLogin() {
    alert('로그인이 필요합니다.');
}

let likeButton = $("#likeButton");
// 하트 아이콘 색깔 변경
likeButton.click(function () {
  console.log(myLikeCount);
  // 추천 상태에 따라 하트 아이콘 업데이트
  if (myLikeCount > 0) {
    myLikeCount = 0;
    likeButton.html('<i class="fa-regular fa-heart"></i>'); // 빈 하트
  } else {
    myLikeCount = 1;
    likeButton.html('<i class="fa-solid fa-heart"></i>'); // 꽉 찬 하트
  }

  console.log("현재 Like Count: ", myLikeCount);
  console.log(initialCount, myLikeCount);
});

// beforeunload 이벤트 핸들러 추가
$(window).on("beforeunload", function () {
  let url = myLikeCount === 1 ? "/board/like" : "/board/unlike";
  boardNo = $("#boardNo").val();
  if (initialCount != myLikeCount) {
    $.ajax({
      type: "POST",
      url: url,
      data: { boardNo: boardNo },
    });
  }
});

// 로그인한 사용자만 추천수 버튼을 누를 수 있음
function requireLogin() {
  alert('로그인이 필요합니다.');
}

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