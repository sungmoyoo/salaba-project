"use strict"
//댓글 작성
$('#addCommendBtn').click(function(e) {
    e.preventDefault();
    let boardNo = $('#boardNum').text();
    let content = $('#commentContent').val();
    let alarmContent = window.location.href;
    let commentWriterNo = $('#commentWriterNo').text();
    $.ajax({
        url: "/board/comment/add",
        type: "POST",
        dataType: 'json',
        data: {
            boardNo: boardNo,
            content: content,
            alarmContent: alarmContent,
            commentWriterNo: commentWriterNo
        },
        success: function(data) {
            let newComment = `<div>
                                <div class="comment">
                                    <span class="commentNo" hidden>${data.commentNo}</span>
                                    <span >${data.writer.nickname}</span>
                                    <span data-th-text="*">${data.content}</span>
                                    <span data-th-text="*">${data.createdDate}</span>
                                    <button class="del">삭제</button>
                                    <button class="modi">수정</button>
                                    <button class="repo">신고</button>
                                </div>
                            </div>`;
            $('#box').append(newComment);

        },
        error: function() {
            alert("권한이 없습니다.")
        }
    })
});

//댓글 삭제
$('.del').click(function(e) {
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
})

//댓글 수정
$('.modi').click(function(e) {
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
        commentDiv.append(updateFormHTML);

        //수정하기 폼의 수정버튼
        $('.comtModiConfirm').click(function() {
            let newContent = updateFormHTML.find('.newContent').val();
            console.log(newContent);
            console.log(commentNo);
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
        })

        //수정하기 폼의 취소 버튼
        $('.comtModiCancel').click(function() {
            $('.commentModifyForm').remove();
        })

    }

});


//댓글 - 답글달기
$('.comment').click(function(e) {
    let replyForm = $('.replyForm');
    let commentNo = $(this).find('.commentNo').text();
    let alarmContent = window.location.href;
    let memberNoForAlarm = $('#memberNoForAlarm').text();

    if (replyForm.length != 0 && replyForm.children().first().val() != commentNo) {
        replyForm.remove();
    }

    const replyFormHTML = `<form class="replyForm">
                                <input hidden value="${commentNo}">
                                <textarea id="content" name="content"></textarea>
                                <button type="button" id="addReplyBtn">답글 달기</button>
                            </form>`;

    if ($('.replyForm').length == 0) {
        let here = $(this);
        here.append(replyFormHTML);
        here.find('#addReplyBtn').click(function (e) {
            let content = here.find('#content').val();
            $.ajax({
                url: "/board/reply/add",
                type: 'POST',
                dataType: 'json',
                data: {
                    commentNo: commentNo,
                    content: content,
                    alarmContent: alarmContent,
                    memberNoForAlarm: memberNoForAlarm
                },
                success: function (data) {
                    here.find('.replyForm').remove();
                    let newReply = `<div class="reply">
                                        <span class="replyNo" hidden>${data.replyNo}</span>
                                        <span>${data.writer.nickname}</span>
                                        <span class="replContent">${data.content}</span>
                                        <span>${data.createdDate}</span>
                                        <button class="del2">삭제</button>
                                        <button class="modi2">수정</button>
                                        <button class="repo2">신고</button>
                                    </div>`;
                    here.parent().append(newReply);
                },
                error: function () {
                    window.alert("권한이 없습니다.");
                }
            });
        });
    }
});




//대댓글 삭제
$('.del2').click(function (e) {
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

});

//대댓글 수정
$('.modi2').click(function(e) {
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
        })

        //수정하기 폼의 취소 버튼
        $('.replModiCancel').click(function() {
            $('.replyModifyForm').remove();
        })

    }
});
