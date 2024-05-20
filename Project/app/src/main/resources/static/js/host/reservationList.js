document.addEventListener('DOMContentLoaded', function () {
  approveButtons = document.querySelectorAll('.approveButton');
  approveButtons.forEach(button => {
      button.onclick = () => {
        Swal.fire({
          title: "예약 승인",
          icon: "info",
          text: "예약을 승인하시겠습니까?",
          showCancelButton: true,
          width: 600,
          confirmButtonText: "확인",
          confirmButtonColor: "#35C5B3",
          cancelButtonText: "취소",
          cancelButtonColor: "#aaa"

        }).then((result) => {
          if (result.isConfirmed) {
            Swal.fire({
              position: "center",
              icon: "success",
              title: "승인되었습니다.",
              showConfirmButton: false,
              timer: 2000
            }).then((result) => {
              reservationCheck(1, button);
            });
          }
        });
      };
  });

  rejectButtons = document.querySelectorAll('.rejectButton');
  rejectButtons.forEach(button => {
    button.onclick = () => {
        Swal.fire({
          title: "예약 거절",
          icon: "warning",
          text: "예약을 거절하시겠습니까?",
          showCancelButton: true,
          width: 600,
          confirmButtonText: "확인",
          confirmButtonColor: "#d33",
          cancelButtonText: "취소",
          cancelButtonColor: "#aaa"


        }).then((result) => {
          if (result.isConfirmed) {
            Swal.fire({
              position: "center",
              icon: "success",
              title: "거절되었습니다.",
              showConfirmButton: false,
              timer: 2000
            }).then((result) => {
              reservationCheck(2, button);
            });
          }
        });
      };
  });

  const today = new Date();
  const statusCells = document.querySelectorAll('.status');

  statusCells.forEach(cell => {
    const startDate = new Date(cell.dataset.start);
    const endDate = new Date(cell.dataset.end);

    if (today < startDate) {
      cell.textContent = '남은 일수: ' + Math.ceil((startDate - today) / (1000 * 60 * 60 * 24));
    } else if (today >= startDate && today <= endDate) {
      cell.textContent = '이용중';
    } else {
      cell.textContent = '이용 완료';
    }
  });
});

function reservationCheck(stateNo, button) {
  const reservationNo = button.getAttribute('data-reservation-no');
  const hostNo = button.getAttribute('data-host-no');

  let formData = new FormData();
  formData.append('reservationNo', reservationNo);
  formData.append('hostNo', hostNo);
  formData.append('state', stateNo);

  // 여기에서 AJAX 요청을 보내거나 필요한 작업을 수행합니다.
  fetch('reservationCheck', {
    method: 'POST',
    body: formData
  }).then(response => {
    if (!response.ok) {
      throw new Error('Network response was not ok');
    }
    return response.text();
  }).then(() => {
    // 요청이 성공적으로 처리된 후에 페이지를 새로고침합니다.
    location.reload();
  }).catch(error => {
    console.error('Error:', error);
  });
}