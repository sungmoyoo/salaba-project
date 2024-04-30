"use strict"
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
                                        <button class="repo">신고</button>
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
                                            <button class="repo2">신고</button>
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



// 추천 수
$(document).on('click', '#likeButton', function() {
    let boardNo = $("#boardNo").val();
    let myLikeCount = parseInt($("#myLikeCount").text());
    if (myLikeCount === 0) {
        $.ajax({
            type: 'POST',
            url: '/board/like',
            data: { boardNo: boardNo },
            success: function(response) {
                if (response.status === "success") {
                    alert('추천하였습니다.');
                    $("#myLikeCount").text('1');
                    $('#likeButton').addClass('active'); // 하트 버튼에 'active' 클래스 추가
                    $('#likeButton i').removeClass('fa-regular fa-heart').addClass('fa-solid fa-heart'); // 꽉 찬 하트로 변경
                    window.location = window.location.href;
                }
            },
            error: function() {
              console.log("like 실패!");
            }
        });
    } else {
        $.ajax({
            type: 'POST',
            url: '/board/unlike',
            data: { boardNo: boardNo },
            success: function(response) {
                if (response.status === "success") {
                    alert('추천 취소');
                    $("#myLikeCount").text('0');
                    $('#likeButton').removeClass('active'); // 'active' 클래스 제거
                    $('#likeButton i').removeClass('fa-solid fa-heart').addClass('fa-regular fa-heart'); // 빈 하트로 변경
                    window.location = window.location.href;
                }
            },
            error: function() {
              console.log("unlike 실패!");
            }
        });
    }
});

// 게시글 신고 버튼
  $('#board-report-btn').click(function() {
    console.log("aaaa");
    var title = '신고하기';
    var width = 800;
    var height = 600;
    let targetNo = document.getElementById("noInput1").getAttribute("value");
    console.log(targetNo);
    openPopup('report/form?targetType=0&targetNo=' + targetNo, title, width, height);
  });

// 댓글 신고 버튼
  $('#comment-report-btn').click(function() {
    var title = '신고하기';
    var width = 800;
    var height = 600;
    let targetNo = document.getElementById("noInput2").getAttribute("value");
    console.log(targetNo);
    openPopup('report/form?targetType=1&targetNo=' + targetNo, title, width, height);
  });

// 답글 신고 버튼
    $('#reply-report-btn').click(function() {
    var title = '신고하기';
    var width = 800;
    var height = 600;
    let targetNo = document.getElementById("noInput3").getAttribute("value");
    console.log(targetNo);
    openPopup('report/form?targetType=2&targetNo=' + targetNo, title, width, height);
  });

// 팝업을 열기 위한 함수
function openPopup(url, title, width, height) {
  var left = (screen.width - width) / 2;
  var top = (screen.height - height) / 2;
  var options = 'toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=no, copyhistory=no, width=' + width + ', height=' + height + ', top=' + top + ', left=' + left;
  var popup = window.open(url, title, options);
  return popup;
}

