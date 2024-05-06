"use strcit";
const tbody = $("tbody");

(function () {
  axios.get(`${RESTAPI_HOST}/qna/list`).then((response) => {
    let result = response.data;
    if (response.status == "failure") {
      alert(response.error);
      return;
    }
    var trTemplate = Handlebars.compile($("#tr-template").html());
    $("tbody").html(trTemplate(result));

    $(".rowItem").click(function () {
      let modal = $("#detailModal");
      let modalContent = $("#modalContent");
      let rentalNo = $(this).find(".rentalNo").text();
      console.log(rentalNo, modal);
      console.log("modal!");
      modal.css("display", "block"); // 모달을 보이도록 설정
      modalContent.css("display", "block"); // 모달을 보이도록 설정
      axios
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
            $("#answer").prop("readonly", true);
            $(".dealBtn").hide();
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
            axios.post(`${RESTAPI_HOST}/qna/update`, requestData)
              .then((response) => {
                if (response.status == "failure") {
                  alert("이미 처리되었습니다.");
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
          });
        });

      $("#closeBtn").click(() => {
        modal.css("display", "none");
        modalContent.css("display", "node");
      });
    });
  });
})();