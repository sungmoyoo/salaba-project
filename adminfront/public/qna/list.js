"use strcit";
const tbody = $("tbody");

(function () {
  axiosInstance.get(`${RESTAPI_HOST}/qna/list`).then((response) => {
    let result = response.data;
    if (response.status == "failure") {
      alert(response.error);
      return;
    }
    var trTemplate = Handlebars.compile($("#tr-template").html());
    $("tbody").html(trTemplate(result));
    let incomplete = pageContext.params.get("incomplete");

    if (incomplete == 'true') {
        $('#incomplete').click();
    }

    $(".rowItem").click(function () {
      let modal = $("#detailModal");
      let modalContent = $("#modalContent");
      let rentalNo = $(this).find(".rentalNo").text();
      console.log(rentalNo, modal);
      console.log("modal!");
      modal.css("display", "block"); // 모달을 보이도록 설정
      modalContent.css("display", "block"); // 모달을 보이도록 설정
      axiosInstance
        .get(`${RESTAPI_HOST}/qna/view/${$(this).find(".qno").text()}`)
        .then((response) => {
          let data = response.data.data;
          console.log(data);
          let detailTemplate = Handlebars.compile(
            $("#detail-template").html()
          );
          modalContent.html(detailTemplate(data));
          console.log(data.state);
          if (data.state != "0") {
            $(".dealBox").hide();
          } else {
            $('.answer-info').hide();
          }

          $(".dealBtn").click((e) => {
            e.stopPropagation();
            e.preventDefault();
            console.log(data.questionNo);
            console.log($("#answer").val());
            const requestData = {
              questionNo: data.questionNo,
              answer: $("#answer").text(),
            };
            axiosInstance.post(`${RESTAPI_HOST}/qna/update`, requestData)
              .then((response) => {
                if (response.status == "failure") {
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
           
          });
        });

      $("#closeBtn").click(() => {
        modal.css("display", "none");
        modalContent.css("display", "node");
      });
    });
  });
})();