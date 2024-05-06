    "use strict"

    const tbody = $("tbody");
    console.log(RESTAPI_HOST);
    let menu = pageContext.params.get("menu");
    (function() {
        axiosInstance.get(`${RESTAPI_HOST}/rental/list/${menu}`)
        .then((response) => {
            let result = response.data;
            if (result.status == 'failure') {
                alert(response.error);
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
                console.log(rentalNo, modal);
                
                console.log("modal!")
                modal.css('display', 'block'); // 모달을 보이도록 설정
                modalContent.css('display', 'block'); // 모달을 보이도록 설정
                axiosInstance.get(`${RESTAPI_HOST}/rental/view/${pageContext.params.get("menu")}/${rentalNo}`)
                .then((response) => {
                    let data = response.data.data;
                    console.log(data);
                    let detailTemplate = Handlebars.compile($("#detail-template").html());
                    modalContent.html(detailTemplate(data));
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
                                alert('숙소 승인/거부 오류')
                                return;
                            }
                        })
                        .catch((error) => {
                            // 에러가 발생했을 때의 처리
                            if (error.response.status === 403) {
                                alert('권한이 없습니다.');
                              // 특정 작업 수행
                            }
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
            if(result.status == 'failure') {
                alert(response.error);
                console.log("failure")
                return;
            }

            var trTemplate = Handlebars.compile($("#tr-template").html());
            $("#modalContent").html(trTemplate(result));
            
           
        })
    });

    function approveOrReject(value) {
        let form = document.querySelector('form');
        let rentalNo = document.querySelector('input[name="rentalNo"]').value;
        let valueInput = document.createElement('input')
        valueInput.type = 'hidden';
        valueInput.name = 'value';
        valueInput.value = value;
        form.appendChild(valueInput);
        form.submit();
    }

