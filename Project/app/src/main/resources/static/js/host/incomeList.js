
let rentalHomeList = document.querySelectorAll(".rentalHome-container");
let filteredReservationList;

let formatter = new Intl.NumberFormat('ko-KR'); // 한국 통화 포맷을 사용하도록 설정

// 날짜 조회 버튼
let searchButton = document.getElementById("searchButton");
searchButton.addEventListener('click', () => {
  let startDate = document.getElementById("startDate").value;
  let endDate = document.getElementById("endDate").value;

  // 예약내역 필터링
  filteredReservationList = reservationList.filter(function(reservation) {
    return reservation.paymentDate >= startDate && reservation.paymentDate <= endDate;
  });

  getDefaultReservation(filteredReservationList);
})

// 상세 보기 이벤트 설정
rentalHomeList.forEach(el => {
  el.addEventListener('click', (e) => {
    let rentalHomeNo = e.currentTarget.getAttribute("data-no");
    if (filteredReservationList != null) {
      console.log("1");
      showDetail(rentalHomeNo, filteredReservationList);
    } else {
      console.log("2");
      showDetail(rentalHomeNo, reservationList);
    }
  })
});

// 이용건수, 총 수입 초기화하는 함수(기본, 전체)
document.addEventListener('DOMContentLoaded', function() {
  getDefaultReservation(reservationList);
});

// 상세 보기 출력(모달 방식)
function showDetail(rentalHomeNo, reservationList) {
  let rentalHomeEl = document.querySelector(`.rentalHome-container[data-no="${rentalHomeNo}"]`);
  let no = rentalHomeEl.getAttribute("data-no");

  let cleanFee = rentalHomeEl.getAttribute("data-cleanFee");
  let rentalHomeReservationList = [];

  // 숙소별 예약내역 리스트 받기
  reservationList.forEach(function(reservation) {
    if (reservation.rentalHomeNo == no) {
      rentalHomeReservationList.push(reservation);
    }
  });

  let detailContainer = document.getElementById("detailContainer");
  detailContainer.innerHTML = ""; // 기존 내용 초기화

  if (rentalHomeReservationList.length != 0) {
    rentalHomeReservationList.forEach(function(reservationEl) {
      let detailItem = `
        <div class="detail-item">
          <div class="info-div">
            <span>예약번호: ${reservationEl.reservationNo}</span>
            <span>결제일: ${reservationEl.paymentDate}</span>
            <span>게스트: ${reservationEl.memberName}</span>
            <span class="useDate">이용 기간: ${reservationEl.startDate} ~ ${reservationEl.endDate}</span>
          </div>
          <div class="content-div">
            <div class="name-div">
              <span>결제금액</span>
              <span>청소비</span>
              <span>수수료</span>
              <span>정산금액</span>
            </div>
            <div class="value-div">
              <span>${formatter.format(reservationEl.amount)}원</span>
              <span>${formatter.format(cleanFee)}원</span>
              <span>${formatter.format(Math.round(reservationEl.amount * 0.03))}원</span>
              <span>${formatter.format(reservationEl.amount - cleanFee - (Math.round(reservationEl.amount * 0.03)))}원</span>
            </div>
          </div>
        </div>
      `;
      detailContainer.innerHTML += detailItem;
    });
    let totalSum = rentalHomeReservationList.reduce((acc, curr) => acc + curr.amount, 0);

    document.getElementById("totalSumAmount").textContent = "총계: " + formatter.format(totalSum) + '원';
  } else {
    detailContainer.innerHTML = "<p>해당 기간 내 예약 내역이 없습니다.</p>";
  }
}


// 기본 예약 내역 출력 및 총 수입 계산
function getDefaultReservation(reservationList) {
  let totalIncome = 0;

  rentalHomeList.forEach(el => {
    let no = el.getAttribute("data-no");
    let cleanFee = el.getAttribute("data-cleanFee");
    let usageCount = 0;

    reservationList.forEach(function(reservation) {
      if (reservation.rentalHomeNo == no) {
        usageCount++;
        totalIncome += reservation.amount - cleanFee - (Math.round(reservation.amount * 0.03));
      }
    });

    document.getElementById("usageCount_" + no).textContent = usageCount;
  });

  document.getElementById("totalIncome").textContent = formatter.format(totalIncome) + '원';
}