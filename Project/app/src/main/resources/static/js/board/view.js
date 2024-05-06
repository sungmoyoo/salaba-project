"use strict"

document.addEventListener("DOMContentLoaded", function() {
    // 댓글이 없는 게시글인지 확인
    var commentBox = document.getElementById("box");
    if (commentBox && commentBox.children.length === 0) {
      commentBox.style.display = "none"; // 댓글이 없는 경우 comment-box 숨기기
    }
  });

//댓글 작성
$('#addCommendBtn').click(function(e) {
    e.preventDefault();
    let boardNo = $('#boardNum').text();
    let input = $('#commentContent')
    let content = input.val();
    if (content != '') {
        $.ajax({
            url: "/board/comment/add",
            type: "POST",
            dataType: 'json',
            data: {
                boardNo: boardNo,
                content: content
            },
            success: function(data) {
                let newComment = $(`<div>
                                    <div class="comment">
                                        <span class="commentNo" hidden>${data.commentNo}</span>
                                        <span >${data.writer.nickname}</span>
                                        <span class="commentContent" data-th-text="*">${data.content}</span>
                                        <span data-th-text="*">${data.createdDate}</span>
                                        <button class="del">삭제</button>
                                        <button class="modi">수정</button>
                                        <button class="repo report-btn">신고</button>
                                    </div>
                                </div>`);
                $('#box').append(newComment);
                input.val('');
                newComment.children().first().click(addReply);
                newComment.children().find('.del').click(deleteComment);
                newComment.children().find('.modi').click(modifyComment);
                // $('#box').find('.repo')

            },
            error: function() {
                alert("권한이 없습니다.")
            }
        })
    } else {
        alert('내용을 작성하세요');
    }
});

//댓글 삭제
$('.del').click(deleteComment);

//댓글 수정
$('.modi').click(modifyComment);

//댓글 - 답글달기
$('.comment').click(addReply);

//대댓글 삭제
$('.del2').click(deleteReply);

//대댓글 수정
$('.modi2').click(modifyReply);

function deleteComment(e) {
    e.stopPropagation();
    let commentDiv = $(this).parent();
    let commentNo = commentDiv.children().first().text();
    console.log(commentNo);
    $.ajax({
        url: "/board/comment/delete",
        type: "GET",
        data: {commentNo: commentNo},
        success: () => {
            commentDiv.html('<span>삭제된 댓글입니다.</span>')
        },
        error: () => alert("권한이 없습니다.")
    })
}



function modifyComment(e) {
    e.stopPropagation();
    let commentDiv = $(this).parent();
    let commentNo = commentDiv.children().first().text();
    let oldContent = commentDiv.find('.commentContent').text();
    let modifyForm = $('.commentModifyForm');

    if (modifyForm.length != 0 && modifyForm.children().first().val() != commentNo) {
        modifyForm.remove();
    }

    const updateFormHTML = $(`<form class="commentModifyForm">
                                <input hidden value="${commentNo}">
                                <textarea class="newContent">${oldContent}</textarea>
                                <button type="button" class="comtModiConfirm">수정</button>
                                <button type="button" class="comtModiCancel">취소</button>
                            </form>`);

    if ($('.commentModifyForm').length == 0) {
        updateFormHTML.find('.newContent').click((e) => e.stopPropagation());
        commentDiv.append(updateFormHTML);
        
        //수정하기 폼의 수정버튼
        $('.comtModiConfirm').click(function(e) {
            e.stopPropagation();
            let newContent = updateFormHTML.find('.newContent').val();
            console.log(newContent);
            console.log(commentNo);
            if (newContent != '') {
                $.ajax({
                    url: "/board/comment/update",
                    type: 'POST',
                    dataType: 'json',
                    data: {
                        commentNo: commentNo,
                        content: newContent
                    },
                    success: function (data) {
                        updateFormHTML.remove();
                        commentDiv.find('.commentContent').text(newContent);
                    },
                    error: function (error) {
                        window.alert("수정 권한이 없습니다.");
                        updateFormHTML.remove();
                    }
                });
            } else {
                alert('내용을 작성하세요');
            }
        })

        //수정하기 폼의 취소 버튼
        $('.comtModiCancel').click(function(e) {
            e.stopPropagation();
            $('.commentModifyForm').remove();
        })

    }

}

function addReply(e) {
    e.stopPropagation();
    let replyForm = $('.replyForm');
    let commentNo = $(this).find('.commentNo').text();

    if (replyForm.length != 0 && replyForm.children().first().val() != commentNo) {
        replyForm.remove();
    }

    const replyFormHTML = $(`<form class="replyForm">
                                <input hidden value="${commentNo}">
                                <textarea id="content" name="content"></textarea>
                                <button type="button" id="addReplyBtn">답글 달기</button>
                            </form>`);
    replyFormHTML.find('#content').click((e) => e.stopPropagation());
    let here = $(this);
    if ($('.replyForm').length == 0 && here.find('.commentContent').length != 0) {
        here.append(replyFormHTML);
        here.find('#addReplyBtn').click(function (e) {
            let newContent = here.find('#content').val();
            //빈댓글 작성 금지
            if (newContent != '') {
                $.ajax({
                    url: "/board/reply/add",
                    type: 'POST',
                    dataType: 'json',
                    data: {
                        commentNo: commentNo,
                        content: newContent
                    },
                    success: function (data) {
                        here.find('.replyForm').remove();
                        let newReply = $(`<div class="reply">
                                            <span class="replyNo" hidden>${data.replyNo}</span>
                                            <span>${data.writer.nickname}</span>
                                            <span class="replContent">${data.content}</span>
                                            <span>${data.createdDate}</span>
                                            <button class="del2">삭제</button>
                                            <button class="modi2">수정</button>
                                            <button class="repo2 report-btn">신고</button>
                                        </div>`);
                        here.parent().append(newReply);
                        newReply.find('.del2').click(deleteReply);
                        newReply.find('.modi2').click(modifyReply);
                    },
                    error: function () {
                        window.alert("권한이 없습니다.");
                    }
                });
            } else {
                alert('내용을 작성하세요');
            }
        });
    } else {
        here.find('.replyForm').remove();
    }
}


function deleteReply(e) {
    let replyDiv = $(this).parent();
    let replyNo = replyDiv.find('.replyNo').text();
    console.log(replyNo);
    $.ajax({
        url: '/board/reply/delete',
        type: "GET",
        dataType: 'json',
        data: {replyNo: replyNo},
        success: function() {
            replyDiv.remove();
        },
        error: function() {
            alert("삭제 권한이 없습니다.");
        }
    })
}


function modifyReply(e) {
    let replyDiv = $(this).parent();
    let modifyForm = $('.replyModifyForm');
    let replyNo = replyDiv.find('.replyNo').text();
    let oldContent = replyDiv.find('.replContent').text();

    if (modifyForm.length != 0 && modifyForm.children().first().val() != replyNo) {
        modifyForm.remove();
    }

    const updateFormHTML = $(`<form class="replyModifyForm">
                                <input hidden value="${replyNo}">
                                <textarea class="newContent">${oldContent}</textarea>
                                <button type="button" class="replModiConfirm">수정</button>
                                <button type="button" class="replModiCancel">취소</button>
                            </form>`);
    //대댓글의 수정하기 버튼(수정하기 폼이 나타남)
    if ($('.replyModifyForm').length == 0) {
        replyDiv.append(updateFormHTML);
        //수정하기 폼의 수정버튼
        $('.replModiConfirm').click(function() {
            let newContent = updateFormHTML.find('.newContent').val();
            console.log(newContent);
            console.log(replyNo);
            if (newContent != '') {
                $.ajax({
                    url: "/board/reply/update",
                    type: 'POST',
                    dataType: 'json',
                    data: {
                        replyNo: replyNo,
                        content: newContent
                    },
                    success: function (data) {
                        updateFormHTML.remove();
                        replyDiv.find('.replContent').text(newContent);
                    },
                    error: function (error) {
                        window.alert("수정 권한이 없습니다.");
                        updateFormHTML.remove();
                    }
                });
            } else {
                alert('내용을 작성하세요');
            }
        })

        //수정하기 폼의 취소 버튼
        $('.replModiCancel').click(function() {
            $('.replyModifyForm').remove();
        })

    }
}


/*************************************************************************************/

//추천수


// let initialCount = myLikeCount;
// console.log("myLikecount: ", myLikeCount);

// 로그인 여부 확인
let isLoggedIn = $("#loginUser").length > 0;

// 로그인 필요 알림 함수
function requireLogin() {
    alert('로그인이 필요합니다.');
}

$(document).ready(function() {
  let likeButton = $("#likeButton");
  let myLikeCount = parseInt($("#myLikeCount").text()); // 내 추천수(0 or 1)
  let likeCount = parseInt($("#likeCount").text()); // 초기 전체 추천수

  console.log("Initial myLikeCount:", myLikeCount);
  console.log("Initial likeCount:", likeCount);

  likeButton.click(function () {
      console.log("Like button clicked.");
      if (myLikeCount > 0) {
          myLikeCount = 0;
          likeCount -= 1;
          likeButton.html('<i class="fa-regular fa-heart"></i>');
      } else {
          myLikeCount = 1;
          likeCount += 1;
          likeButton.html('<i class="fa-solid fa-heart"></i>');
      }
      $("#likeCount").text(likeCount);
      $("#myLikeCount").text(myLikeCount);
      
      console.log("Updated myLikeCount:", myLikeCount);
      console.log("Updated likeCount:", likeCount);
  });
});


$(window).on("beforeunload", function () {
  let url = "/board/updateLikes";
  boardNo = $("#boardNo").val();
  if (changeInLikes !== 0 || initialCount != myLikeCount) {
    $.ajax({
      type: "POST",
      url: url,
      data: {
        boardNo: boardNo,
        likeChange: changeInLikes,
        userLikeChange: (initialCount != myLikeCount) ? (myLikeCount - initialCount) : 0
      },
    });
  }
});


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