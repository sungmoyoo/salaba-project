stateList = document.querySelectorAll('.state');

stateList.forEach((stateDiv) => {
  let state = stateDiv.getAttribute('data-state');
  switch (state) {
    case '0':
      stateDiv.textContent = "등록 심사중";
      break;
    case '1':
      stateDiv.textContent = "운영중";
      break;
    case '2':
      stateDiv.textContent = "운영 중지";
      break;
    case '3':
      stateDiv.textContent = "삭제";
      break;
    case '4':
      stateDiv.textContent = "등록 거부";
      break;
    case '5':
      stateDiv.textContent = "제재";
      break;
    default:
      stateDiv.textContent = "등록 심사중";
      break;
  }
});

let el = document.querySelectorAll('.table-row.content');

el.forEach(function(e) {
  let stateDiv = e.querySelector('.state');
  let state = stateDiv.textContent;

  if (state == "운영중") {
    e.onclick = () => {
      window.location.href = "rentalHomeView?rentalHomeNo=" + e.getAttribute("data-no");
    };
  } else {
    e.onclick = () => {
      Swal.fire({
        position: "center",
        icon: "warning",
        title: "운영중인 숙소만 조회할 수 있습니다.",
        showConfirmButton: false,
        timer: 1000
      });
    };
  }
});

document.getElementById('addContainer').onclick = () => {
  location.href='hostStart';
};