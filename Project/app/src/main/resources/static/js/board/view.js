"use strict";

let replyForm = $('#reply-form');
let reportForm = $('#report-form')
let boardNo = $("#boardNum").text(); // 게시글 번호
// 댓글 작성
$("#addCommentBtn").click(function (e) {
  e.preventDefault(); // 기본 이벤트 방지
  let input = $("#commentContent"); // 댓글 내용
  let content = input.val(); // 입력 필드에 값 가져오기
  if (content != "") { // 댓글 내용이 비어있지 않은 경우
    $.ajax({
      url: "/board/comment/add",  // 요청할 서버의 URL
      type: "POST",        // HTTP 요청 방식
      dataType: "json",    // 서버에서 반환되는 데이터 타입
      data: {              // 서버로 보낼 데이터
        boardNo: boardNo,
        content: content,
      },
      success: function (data) {
        let newComment = $(`<div>
                                    <div class="comment">
                                        <div class="comment-text">
                                          <span class="commentNo" hidden>${data.commentNo}</span>
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
        newComment.children().find(".modi").click(modifyComment);
        // $('#box').find('.repo')
      },
      error: function () { // 오류 메시지 날리기
        alert("권한이 없습니다.");
      },
    });
  } else { // 내용이 비어있는 경우
    alert("내용을 작성하세요");
  }
});

//댓글 삭제
$(".del").click(deleteComment);

//댓글 수정
$(".modi").click(modifyComment);

//댓글 - 답글달기
$('#comment-box').on('click', '.comment-text', addReply);

$('#addReplyBtn').click(event => {
  replyForm.addClass('form-hidden');
});

//대댓글 삭제
$(".del2").click(deleteReply);

//대댓글 수정
$(".modi2").click(modifyReply);

function deleteComment(e) {
  console.log($(this));
  let commentDiv = $(this).parent();
  let commentNo = commentDiv.children().find('.commentNo').text();
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

function modifyComment(e) {
  e.stopPropagation();
  let commentDiv = $(this).parent();
  let commentNo = commentDiv.children().first().text();
  let oldContent = commentDiv.find(".commentContent").text();
  let modifyForm = $(".commentModifyForm");

  if (
    modifyForm.length != 0 &&
    modifyForm.children().first().val() != commentNo
  ) {
    modifyForm.remove();
  }

  const updateFormHTML = $(`<form class="commentModifyForm">
                                <input hidden value="${commentNo}">
                                <textarea class="newContent">${oldContent}</textarea>
                                <button type="button" class="comtModiConfirm">수정</button>
                                <button type="button" class="comtModiCancel">취소</button>
                            </form>`);

  if ($(".commentModifyForm").length == 0) {
    updateFormHTML.find(".newContent").click((e) => e.stopPropagation());
    commentDiv.append(updateFormHTML);

    //수정하기 폼의 수정버튼
    $(".comtModiConfirm").click(function (e) {
      e.stopPropagation();
      let newContent = updateFormHTML.find(".newContent").val();
      console.log(newContent);
      console.log(commentNo);
      if (newContent != "") {
        $.ajax({
          url: "/board/comment/update",
          type: "POST",
          dataType: "json",
          data: {
            commentNo: commentNo,
            content: newContent,
          },
          success: function (data) {
            updateFormHTML.remove();
            commentDiv.find(".commentContent").text(newContent);
          },
          error: function (error) {
            window.alert("수정 권한이 없습니다.");
            updateFormHTML.remove();
          },
        });
      } else {
        alert("내용을 작성하세요");
      }
    });

    //수정하기 폼의 취소 버튼
    $(".comtModiCancel").click(function (e) {
      e.stopPropagation();
      $(".commentModifyForm").remove();
    });
  }
}

/*답글 달기*/ 
function addReply(e) {
  console.log('==============>');
  $(e.currentTarget).parent().append(replyForm);
  replyForm.removeClass('form-hidden');  
  
  //e.stopPropagation();
  // console.log("이벤트 전파 중지 완료");

  // let replyForm = $(".replyForm");// 댓글에 대한 답글 폼 가져오기
  // console.log("현재 페이지의 답글 폼:", replyForm.length > 0 ? "Y" : "N");

  // let commentNo = $(this).find(".commentNo").text(); // 클릭한 댓글 번호
  // console.log(commentNo);

  if (
    replyForm.length != 0 &&
    replyForm.children().first().val() != commentNo
  ) {
    replyForm.remove();
  }

  const replyFormHTML = $(`<form class="replyForm">
                                <input hidden value="${commentNo}">
                                <textarea id="content" name="content"></textarea>
                                <button type="button" id="addReplyBtn">답글 달기</button>
                            </form>`);
  replyFormHTML.find("#content").click((e) => e.stopPropagation());
  let here = $(this);
  if ($(".replyForm").length == 0 && here.find(".commentContent").length != 0) {
    here.append(replyFormHTML);
    here.find("#addReplyBtn").click(function (e) {
      let newContent = here.find("#content").val();
      //빈댓글 작성 금지
      if (newContent != "") {
        $.ajax({
          url: "/board/reply/add",
          type: "POST",
          dataType: "json",
          data: {
            commentNo: commentNo,
            content: newContent,
          },
          success: function (data) {
            here.find(".replyForm").remove();
            let newReply = $(`<div class="reply">
                                            <span class="replyNo" hidden>${data.replyNo}</span>
                                            <span>${data.writer.nickname}</span>
                                            <span class="replContent">${data.content}</span>
                                            <span>${data.createdDate}</span>
                                            <button class="del2">삭제</button>
                                            <button class="modi2">수정</button>
                                            <button class="report-btn reply-report-btn">신고</button>
                                        </div>`);
            here.parent().append(newReply);
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
    });
  } else {
    here.find(".replyForm").remove();
  }
}

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
  let replyDiv = $(this).parent();
  let modifyForm = $(".replyModifyForm");
  let replyNo = replyDiv.find(".replyNo").text();
  let oldContent = replyDiv.find(".replContent").text();

  if (
    modifyForm.length != 0 &&
    modifyForm.children().first().val() != replyNo
  ) {
    modifyForm.remove();
  }

  const updateFormHTML = $(`<form class="replyModifyForm">
                                <input hidden value="${replyNo}">
                                <textarea class="newContent">${oldContent}</textarea>
                                <button type="button" class="replModiConfirm">수정</button>
                                <button type="button" class="replModiCancel">취소</button>
                            </form>`);
  //대댓글의 수정하기 버튼(수정하기 폼이 나타남)
  if ($(".replyModifyForm").length == 0) {
    replyDiv.append(updateFormHTML);
    //수정하기 폼의 수정버튼
    $(".replModiConfirm").click(function () {
      let newContent = updateFormHTML.find(".newContent").val();
      console.log(newContent);
      console.log(replyNo);
      if (newContent != "") {
        $.ajax({
          url: "/board/reply/update",
          type: "POST",
          dataType: "json",
          data: {
            replyNo: replyNo,
            content: newContent,
          },
          success: function (data) {
            updateFormHTML.remove();
            replyDiv.find(".replContent").text(newContent);
          },
          error: function (error) {
            window.alert("수정 권한이 없습니다.");
            updateFormHTML.remove();
          },
        });
      } else {
        alert("내용을 작성하세요");
      }
    });

    //수정하기 폼의 취소 버튼
    $(".replModiCancel").click(function () {
      $(".replyModifyForm").remove();
    });
  }
}

//추천수 
let myLikeCount = parseInt($("#myLikeCount").text()); // 내 추천수(0 or 1)
let initialCount = myLikeCount;
console.log("myLikecount: ", myLikeCount);

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
    let boardNo = $("#boardNo").val();
    if (initialCount != myLikeCount) {
        $.ajax({
        type: "POST",
        url: url,
        data: { boardNo: boardNo },
        });
    }
  });


// 신고창: 모달 사용
$(document.body).on('click', '.report-btn', function (event) {
   console.log(event.target);
  let div = $(this).parent()
  let targetNo = div.find('.textContent').find('.targetNo').text();

  let targetType;

  if (div.attr('class') == 'comment') {
    targetType = 1;
  } else if (div.attr('class') == 'reply'){
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
  $("#reportModal").modal('show');

});


  // 신고 제출 버튼을 클릭했을 때 폼을 AJAX로 전송
  $("#submitBtn").click(function(e) {
      e.preventDefault(); // 기본 폼 제출을 막음

      // 폼 데이터 수집
      let formData = new FormData($("#reportForm")[0]);

      $.ajax({
          type: "POST",
          url: "/board/report/add", 
          data: formData,
          processData: false,
          contentType: false,
          success: function (response) {
              // 성공 콜백 함수
              console.log("신고가 성공적으로 제출되었습니다.");
              // 모달 닫기
              $("#reportModal").modal("hide");
              // 필요한 추가 로직
          },
          error: function(xhr, status, error) {
              // 에러 콜백 함수
              console.log("에러가 발생했습니다: ", error);
          }
      });
  });
