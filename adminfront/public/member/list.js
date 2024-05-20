const tbody = $("tbody");
let menu = pageContext.params.get("menu");

(function () {
    axiosInstance.get(`${RESTAPI_HOST}/member/list/${menu}`).then((response) => {
        let result = response.data;
        if (response.status == "failure") {
            Swal.fire({
                icon: "error",
                title: "목록을 불러오는중 오류가 발생하였습니다.",
                text: response.error,
                showConfirmButton: false,
                timer: 1500
              });
            return;
        }
        console.log(result);
        result.data.forEach((item) => item.joinDate =  dateFormatter(item.joinDate));
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
            axiosInstance.get(`${RESTAPI_HOST}/member/view/${here.find(".memberNo").text()}/${menu}`)
                .then((response2) => {
                    let result2 = response2.data.data;
                    result2.joinDate = dateFormatter(result2.joinDate);
                    console.log(result2);
                    if (response2.status == 'failure') {
                        Swal.fire({
                            icon: "error",
                            title: "목록 상세를 불러오는중 오류가 발생하였습니다.",
                            text: response2.error,
                            showConfirmButton: false,
                            timer: 1500
                          });
                        return;
                    }
                    if (menu == 2) {
                        result2.rentals.forEach((item) => {
                            item.price = formatToKRW(item.price);
                        });
                    }
                    let detailTemplate = Handlebars.compile($("#detail-template").html());
                    modalContent.html(detailTemplate(result2));
                    if (menu == 1) {
                        $("#subTitle").text("일반회원");
                        $('#rentals').hide()
                        $('.rentalList-title').hide();
                    } else {
                        $("#subTitle").text("호스트");
                        $('.grade').hide();
                        $('.rentalList-title').show();
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
                        axiosInstance.put(`${RESTAPI_HOST}/member/update`, requestData)
                        .then((response) => {
                            if (response.status == 'failure') {
                                Swal.fire({
                                    icon: "error",
                                    title: "등급 변경 오류",
                                    showConfirmButton: false,
                                    timer: 1500
                                  });
                                return;
                            }
                            
                            Swal.fire({
                                icon: "success",
                                title: "성공적으로 변경되었습니다.",
                                showConfirmButton: false,
                                timer: 1500
                            }).then(() => {
                                closeButton.click();
                            });
                            
                        })

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

$('.searchBtn').click((event) => {
    event.preventDefault();
    let keyword = $('.searchInput').val();
    let filter = $('#filter').val()
    console.log(keyword, filter)
    axiosInstance.get(`${RESTAPI_HOST}/member/search/${keyword}/${filter}/${menu}`)
    .then((response) => {
        let result = response.data;
        console.log(result);
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
    })
});

function dateFormatter(dateStr) {
    const date = new Date(dateStr);

    // 각 구성 요소 추출
    const year = String(date.getFullYear()).slice(-2);
    const month = ('0' + (date.getMonth() + 1)).slice(-2); // 월은 0부터 시작하므로 1을 더해줌
    const day = ('0' + date.getDate()).slice(-2);
    const hours = ('0' + date.getHours()).slice(-2);
    const minutes = ('0' + date.getMinutes()).slice(-2);

    // 원하는 형식으로 변환
    return `${year}-${month}-${day}-${hours}:${minutes}`;
}

function formatToKRW(number) {
    // Intl.NumberFormat을 사용하여 원화 표시 및 천 단위 구분 기호 추가
    return new Intl.NumberFormat('ko-KR', {
        style: 'currency',
        currency: 'KRW',
        minimumFractionDigits: 0, // 소수점 이하 자릿수 설정 (원하는 경우 변경 가능)
        maximumFractionDigits: 0
    }).format(number);
}