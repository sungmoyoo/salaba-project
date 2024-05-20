
"use strict"
const tbody = $('tbody');
let menu = pageContext.params.get("menu");
(function () {
    console.log(`${RESTAPI_HOST}/report/list/${menu}`);
    axiosInstance.get(`${RESTAPI_HOST}/report/list/${menu}`)
        .then((response) => {
            let result = response.data;
            console.log(result);
            if (response.status == "failure") {
                Swal.fire({
                    icon: "error",
                    title: response.error,
                    showConfirmButton: false,
                    timer: 1500
                  });
                return;
            }
            var trTemplate = Handlebars.compile($("#tr-template").html());
            $("tbody").html(trTemplate(result));
            let incomplete = pageContext.params.get("incomplete");

            if (incomplete == 'true') {
                $('#incomplete').click();
            }


            $('.rowItem').click(function () {
                let modal = $('#detailModal');
                let modalContent = $('#modalContent')
                let rentalNo = $(this).find('.rentalNo').text();
                let reporterNo = $(this).find('.reporterNo').text();
                let targetNo = $(this).find('.targetNo').text();
                let targetType = $(this).find('.targetType').text();
                modal.css('display', 'block'); // 모달을 보이도록 설정
                modalContent.css('display', 'block'); // 모달을 보이도록 설정

                if (menu == 1) {
                    axiosInstance.get(`${RESTAPI_HOST}/report/view/${rentalNo}/${reporterNo}`)
                        .then((response) => {
                            let data = response.data.data;
                            console.log(data);
                            let rentalDetailTemplate = Handlebars.compile($("#rental-detail-template").html());
                            modalContent.html(rentalDetailTemplate(data));
                            if (data.state != '0') {
                                $('.dealBox').hide();
                            }
                            $('#rentalDealBtn').click((e) => {
                                e.stopPropagation();
                                console.log(data.reporter.memberNo);
                                let requestData = {
                                    targetNo: targetNo
                                }
                                axiosInstance.put(`${RESTAPI_HOST}/report/update/0/${$('#selection2').val()}/${data.reporter.memberNo}`, requestData)
                                    .then((response) => {
                                        if (response.status == 'failure') {
                                            Swal.fire({
                                                icon: "error",
                                                title: "이미 처리되었습니다",
                                                showConfirmButton: false,
                                                timer: 1500
                                              });
                                            return;
                                        }
                                        closeButton.click();
                                    })
                                   
                                    
                            })
                        })
                } else {
                    console.log(targetNo, targetType, reporterNo);
                    axiosInstance.get(`${RESTAPI_HOST}/report/view/${targetNo}/${targetType}/${reporterNo}`)
                        .then((response) => {
                            let data = response.data.data;
                            console.log(data);
                            let textDetailTemplate = Handlebars.compile($("#text-detail-template").html());
                            modalContent.html(textDetailTemplate(data));
                            if (menu == 2) {
                                $('.partTitle').first().text('게시글 신고');
                            } else if (menu == 3) {
                                $('.partTitle').first().text('댓글 신고');
                            }

                            if (data.state != '0') {
                                $('.dealBox').hide();
                            }
                            $('#textDealBtn').click((e) => {
                                e.stopPropagation();
                                console.log('click');
                                console.log($('#selection').val());
                                console.log(data.writer.memberNo);
                                const requestData = {
                                    reportNo: data.reportNo,
                                    targetNo: targetNo,
                                    targetType: targetType
                                }
                                axiosInstance.put(`${RESTAPI_HOST}/report/update/${$('#selection').val()}/0/${data.reporter.memberNo}`, requestData)
                                    .then((response) => {
                                        if (response.status == 'failure') {
                                            Swal.fire({
                                                icon: "error",
                                                title: "처리중 오류 발생",
                                                showConfirmButton: false,
                                                timer: 1500
                                              });
                                            return;
                                        }
                                        Swal.fire({
                                            icon: "success",
                                            title: "성공적으로 처리되었습니다.",
                                            showConfirmButton: false,
                                            timer: 1500
                                        }).then(() => {
                                            closeButton.click();
                                        });
                                    })
                                    .catch((error) => {
                                        // 에러가 발생했을 때의 처리
                                        if (error.response.status === 403) {
                                            Swal.fire({
                                                icon: "error",
                                                title: "권한이 없습니다.",
                                                showConfirmButton: false,
                                                timer: 1500
                                              });
                                            // 특정 작업 수행
                                        }
                                    });
                            })
                        })
                }


                let closeButton = $('#closeBtn');
                closeButton.click(() => {
                    modal.css('display', 'none');
                    modalContent.css('display', 'node');
                });


            })
        })
})();

if (menu == 1) {
    $('h3').text('숙소 신고 내역')
} else if (menu == 2) {
    $('h3').text('게시글 신고 내역')
} else {
    $('h3').text('댓글 신고 내역')
}