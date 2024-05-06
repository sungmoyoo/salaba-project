const tbody = $("tbody");
let menu = pageContext.params.get("menu");
(function () {
    axios.get(`${RESTAPI_HOST}/member/list/${menu}`).then((response) => {
        let result = response.data;
        console.log(result);
        if (response.status == "failure") {
            alert(response.error);
            return;
        }
        let trTemplate = Handlebars.compile($("#tr-template").html());
        tbody.html(trTemplate(result));
        if (menu == 1) {
            $("h3").text("일반 회원 목록");
            $("#changing-th").text("상태");
            $(".rentalCount").hide();
        } else {
            $("h3").text("호스트 목록");
            $("#changing-th").text("등록 숙소 수");
            $(".stateStr").hide();
        }

        $(".rowItem").click(function () {
            let here = $(this);
            let modal = $("#detailModal");
            let modalContent = $("#modalContent");
            let rentalNo = here.find(".rentalNo").text();
            // detail.html이 로드된 후에 실행되는 부분
            axios.get(`${RESTAPI_HOST}/member/view/${here.find(".memberNo").text()}/${menu}`)
                .then((response2) => {
                    let result2 = response2.data.data;
                    if (response2.status == 'failure') {
                        alert(response2.error);
                    }
                    console.log(result2);
                    let detailTemplate = Handlebars.compile($("#detail-template").html());
                    modalContent.html(detailTemplate(result2));
                    if (menu == 1) {
                        $("#subTitle").text("일반회원");
                        $('#rentals').hide()
                    } else {
                        $("#subTitle").text("호스트");
                        $('.grade').hide();
                    }

                    modal.css("display", "block");
                    modalContent.css("display", "block");

                    $('.dealBtn').click((e) => {
                        e.stopPropagation();
                        let selection = $('.dealSelect').val();
                        let memberNo = $(this).find('.memberNo').text() 
                        const requestData = {
                            memberNo: memberNo,
                            gradeNo: selection
                            
                        }
                        axios.put(`${RESTAPI_HOST}/member/update`, requestData)
                        .then((response) => {
                            if (response.status == 'failure') {
                                alert('등급 변경 오류');
                                return;
                            }
                            closeButton.click();
                        })
                        .catch((error) => {
                            // 에러가 발생했을 때의 처리
                            if (error.response.status === 403) {
                                alert('권한이 없습니다.');
                              // 특정 작업 수행
                            }
                        });
                        

                    })

                });


            let closeButton = $("#closeBtn");
            closeButton.click(() => {
                modal.css("display", "none");
                modalContent.css("display", "none");
            });
        });
    });
})();
