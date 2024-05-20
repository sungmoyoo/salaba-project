    "use strict"

    const tbody = $("tbody");
    console.log(RESTAPI_HOST);
    let menu = pageContext.params.get("menu");
    if (menu != 1) {
        $('.searchBar').hide();
    }
    (function() {
        axiosInstance.get(`${RESTAPI_HOST}/rental/list/${menu}`)
        .then((response) => {
            let result = response.data;
            if (result.status == 'failure') {
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

            if (menu == 1) {
                $('h3').text('등록된 숙소 목록');
                $('#changing-th').text('호스트명');
                // $('.applier').hide();
            } else {
                $('h3').text('숙소 등록 심사');
                $('#changing-th').text('신청인');
                // $('.hostName').hide();
            }
            

            $('.rowItem').click(function() {
                let modal = $('#detailModal');
                let modalContent = $('#modalContent')
                let rentalNo = $(this).find('.rentalNo').text();
                
                modal.css('display', 'block'); // 모달을 보이도록 설정
                modalContent.css('display', 'block'); // 모달을 보이도록 설정
                axiosInstance.get(`${RESTAPI_HOST}/rental/view/${pageContext.params.get("menu")}/${rentalNo}`)
                .then((response) => {
                    let data = response.data.data;
                    data.price = formatToKRW(data.price);
                    data.cleanFee = formatToKRW(data.cleanFee);
                    let detailTemplate = Handlebars.compile($("#detail-template").html());
                    modalContent.html(detailTemplate(data));
                    hideFacility();
                    if (menu != 2) {
                        $('.dealBtn').hide();
                    }

                    $('.dealBtn').click((e) => {
                        e.stopPropagation();
                        e.preventDefault(); 
                        let rentalNo = $('#rentalNo').text();
                        let value = (e.target.id == 'approve') ? '1' : '4';
                        const requestData = {
                            rentalNo: rentalNo,
                            state: value
                        }
                        
                        axiosInstance.put(`${RESTAPI_HOST}/rental/update`, requestData)
                        .then((response) => {
                            if (response.state == 'failure') {
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
                        });
                    }) 
                })
                
                let closeButton = $('#closeBtn');
                closeButton.click(() => {
                    modal.css('display', 'none');
                    modalContent.css('display', 'node');
                }); 
            
            
            })
            
        });
    })();

    $('.searchBtn').click((event) => {
        event.preventDefault();
        let keyword = $('.searchInput').val();
        let filter = $('#filter').val()
        axiosInstance.get(`${RESTAPI_HOST}/rental/search/${keyword}/${filter}`)
        .then((response) => {
            let result = response.data;
            console.log(result);

            var trTemplate = Handlebars.compile($("#tr-template").html());
            console.log($("#tr-template").html())
            console.log($("tbody").html())
            $("tbody").html(trTemplate(result));
            console.log($("tbody").html())
            
           
        })
    });


    function formatToKRW(number) {
        // Intl.NumberFormat을 사용하여 원화 표시 및 천 단위 구분 기호 추가
        return new Intl.NumberFormat('ko-KR', {
            style: 'currency',
            currency: 'KRW',
            minimumFractionDigits: 0, // 소수점 이하 자릿수 설정 (원하는 경우 변경 가능)
            maximumFractionDigits: 0
        }).format(number);
    }

    function hideFacility() {
        $('.typeBox').each((index, element) => {
            let ele = $(element);
            if (ele.find('span').first().text() > 3) {
                ele.hide();
            }
        })
    }
