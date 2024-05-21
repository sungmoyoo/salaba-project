(g => { var h, a, k, p = "The Google Maps JavaScript API", c = "google", l = "importLibrary", q = "__ib__", m = document, b = window; b = b[c] || (b[c] = {}); var d = b.maps || (b.maps = {}), r = new Set, e = new URLSearchParams, u = () => h || (h = new Promise(async (f, n) => { await (a = m.createElement("script")); e.set("libraries", [...r] + ""); for (k in g) e.set(k.replace(/[A-Z]/g, t => "_" + t[0].toLowerCase()), g[k]); e.set("callback", c + ".maps." + q); a.src = `https://maps.${c}apis.com/maps/api/js?` + e; d[q] = f; a.onerror = () => h = n(Error(p + " could not load.")); a.nonce = m.querySelector("script[nonce]")?.nonce || ""; m.head.append(a) })); d[l] ? console.warn(p + " only loads once. Ignoring:", g) : d[l] = (f, ...n) => r.add(f) && u().then(() => d[l](f, ...n)) })({
  key: "AIzaSyCn7sXQ-7jFww0vK_pndKEMLEsJfMxAsmk",
  // Add other bootstrap parameters as needed, using camel case.
  // Use the 'v' parameter to indicate the version to load (alpha, beta, weekly, etc.)
});



// 숙소 소개
document.addEventListener("DOMContentLoaded", function () {
  const explanationContent = rentalHomeModel.explanation; // 숙소 소개
  const modal = document.querySelector('.explanationModal'); // modal 창
  const rentalHomeExplanation = document.querySelector('.explanation-content'); // modal 안쪽에 표현할 숙소 소개
  const showMoreExplanation = document.querySelector('.showMoreExplanation'); // 더 보기 버튼
  const closeBtn = document.querySelector('.explanation-modal-close'); // modal 닫기

  const rentalHomeContent = document.querySelector('.rentalHome-explanation-h');

  const maxLength = 20; // 숙소 소개 보여줄 최대 글자 수

  if (explanationContent.length > maxLength) {
    // 최대 글자수 만큼 자르고 뒤에 ... 표시
    rentalHomeContent.textContent = explanationContent.substring(0, maxLength) + "...";
    showMoreExplanation.style.display = "block"; // 더 보기 버튼 표시
  } else {
    rentalHomeExplanation.innerHTML = explanationContent;
    showMoreExplanation.style.display = "none";
  }

  // 더 보기 버튼 클릭 시
  showMoreExplanation.addEventListener("click", function () {
    rentalHomeExplanation.innerHTML = explanationContent;
    modal.style.display = "block"
  });

  // modal 의 닫기 버튼
  closeBtn.addEventListener("click", function () {
    modal.style.display = "none";
  });

  // modal 외부를 클릭 시
  window.addEventListener("click", function (event) {
    if (event.target == modal) {
      modal.style.display = "none";
    }
  });
});

// 숙소 시설
document.addEventListener("DOMContentLoaded", function () {
  const showMoreFacilitiesBtn = document.querySelector('.facility-modal-button');
  const modal = document.querySelector('.facility-modal');
  const closeBtn = modal.querySelector('.facility-modal-close');

  if (showMoreFacilitiesBtn) {
    showMoreFacilitiesBtn.addEventListener("click", function () {
      modal.style.display = "block";
    });
  }

  closeBtn.addEventListener("click", function () {
    modal.style.display = "none";
  });

  window.addEventListener("click", function (event) {
    if (event.target == modal) {
      modal.style.display = "none";
    }
  });
});

document.addEventListener("DOMContentLoaded", function () {
  const showReportFormBtn = document.querySelector('#reportButton');
  const modal = document.querySelector('.report-modal');
  const closeBtn = modal.querySelector('.report-modal-close');
  modal.style.display = "none";

  showReportFormBtn.addEventListener("click", function () {
    modal.style.display = "block";
  });

  closeBtn.addEventListener("click", function () {
    modal.style.display = "none";
  });

  window.addEventListener("click", function (event) {
    if (event.target == modal) {
      modal.style.display = "none";
    }
  });

});


document.getElementById("reportForm").addEventListener("submit", (event) => {
  event.preventDefault(); // 기본 동작 취소

  const category = document.getElementById("category").value;
  const content = document.getElementById("rentalHome-report-content").value;
  const rentalHomeNo = rentalHomeNoModel;
  
  const memberNo = sessionUser.no;
  // RentalHomeReport 객체 생성
  const rentalHomeReport = {
    rentalHomeNo: rentalHomeNo,
    memberNo: memberNo,
    content: content,
    reportCategoryNo: category
  }
  console.log(rentalHomeReport)
  
  if (sessionUser == null) {
    Swal.fire({
      icon: "error",
      title: "로그인이 필요합니다.",
    });
    return;
  }
  
  // Ajax 요청 보내기
  $.ajax({
    type: "POST",
    url: "/rentalHome/report",
    contentType: "application/json",
    data: JSON.stringify(rentalHomeReport),
    success: function (response) {
      Swal.fire({
        icon: "success",
        title: "신고가 접수되었습니다.",
        showConfirmButton: false,
        timer: 1500
      });
      // 신고 닫기
      $(".report-modal").css("display", "none");
    },
    error: function () {
      Swal.fire({
        icon: "error",
        title: "이미 접수되었습니다.",
        showConfirmButton: false,
        timer: 1500
      });
    }
  });
});

// 숙소 리뷰
let startIndex = 0;
const batchSize = 5;
let rentalHomeReviews = rentalHomeReviewModel;

function showNextBatchOfReview() {
  const reviewsContainer = document.getElementById('reviewsContainer');
  const endIndex = Math.min(startIndex + batchSize, rentalHomeReviews.length);
  for (let i = startIndex; i < endIndex; i++) {
    const review = rentalHomeReviews[i];
    const reviewElement = document.createElement('div');
    let stars = '★'.repeat(review.score);
    reviewElement.innerHTML += `<div class="writer-profile">
    <img class='profile-photo' src='https://kr.object.ncloudstorage.com/tp3-salaba/member/${review.writer.photo}'>
    <span>${review.writer.name}</span>
  </div>  
  <div class="review-body">
    <div class="reivew-scoreAndDate">
    <span>${stars}</span>
    <span class='createdDate'>${review.createdDate}</span>
  </div>
  <div class="review-content">
    <span>${review.review}</span>
  </div>
    <span hidden>${review.state}</span>
</div>`;
    reviewsContainer.appendChild(reviewElement);
  }
  startIndex = endIndex;
  if (startIndex >= rentalHomeReviews.length) {
    document.getElementById('showMoreReview').style.display = 'none';
  }
}

// 리뷰 더 보기 버튼 클릭 시
document.getElementById('showMoreReview').addEventListener('click', function () {
  showNextBatchOfReview();
});

// 초기에 최초의 2개 리뷰 표시
window.addEventListener('DOMContentLoaded', function () {
  const reviewsContainer = document.getElementById('reviewsContainer');
  for (let i = 0; i < Math.min(2, rentalHomeReviews.length); i++) {
    const review = rentalHomeReviews[i];
    const reviewElement = document.createElement('div');
    let stars = '★'.repeat(review.score);
    reviewElement.innerHTML +=
      `<div class="writer-profile">
          <img class='profile-photo' src='https://kr.object.ncloudstorage.com/tp3-salaba/member/${review.writer.photo}'>
          <span>${review.writer.name}</span>
        </div>  
        <div class="review-body">
          <div class="reivew-scoreAndDate">
          <span>${stars}</span>
          <span class='createdDate'>${review.createdDate}</span>
        </div>
        <div class="review-content">
          <span>${review.review}</span>
        </div>
          <span hidden>${review.state}</span>
      </div>`;
    reviewsContainer.appendChild(reviewElement);
  }
  startIndex = 2;
  if (rentalHomeReviews.length <= 2) {
    document.getElementById('showMoreReview').style.display = 'none'; // 버튼 숨기기
  }
});


// google map
async function initMap() {
  const rentalHome = rentalHomeModel;
  const position = { lat: Number(rentalHome.lat), lng: Number(rentalHome.lon) };

  console.log(typeof rentalHome.lat);
  console.log(typeof rentalHome.lon);

  const { Map } = await google.maps.importLibrary("maps");
  const { AdvancedMarkerElement, PinElement } = await google.maps.importLibrary("marker");

  let map = new Map(document.getElementById("map"), {
    zoom: 16,
    center: position,
    mapId: '470e45162ba863f'
  });

  const marker = new google.maps.marker.AdvancedMarkerElement({
    map: map,
    position: position,
  });
}

initMap();


document.addEventListener("DOMContentLoaded", function () {
  const checkInDateInput = document.getElementById("checkInDateInput");
  const checkOutDateInput = document.getElementById("checkOutDateInput");
  // const calendar = document.querySelector(".calendar");
  const priceElement = document.querySelector('.reservation-price');
  const durationElement = document.querySelector('.reservation-duration');
  const priceTotalElement = document.querySelector('.reservation-price-total');
  let calendarPrevButton = null;
  let calendarNextButton = null;

  let currentYear = null;
  let currentMonth = null;

  let checkInDate = null;
  let checkOutDate = null;
  let isSelectCheckOut = false; // 이전에 선택한 것이 체크아웃인지 여부

  $('#checkInDateInput').daterangepicker({
    autoUpdateInput: false,
    locale: {
      cancelLabel: 'Clear'
    }
  });

  $('#checkInDateInput').on('apply.daterangepicker', function (ev, picker) {
    $('#checkInDateInput').val(picker.startDate.format('YYYY년 MM월 DD일'));
    $('#checkOutDateInput').val(picker.endDate.format('YYYY년 MM월 DD일'));
  });

  $('#checkInDateInput').on('cancel.daterangepicker', function (ev, picker) {
    $('#checkInDateInput').val('');
    $('#checkOutDateInput').val('');
  });

  const reservationGuestsInput = document.getElementById('reservationGuests');

  reservationGuestsInput.addEventListener('input', function () {
    if (this.value < 1) {
      this.value = 1;
    }
  })

  $('#reservationGuests').on('blur', function () {
    if ($(this).val() < 1) {
      $(this).val(1);
    }
  })


  // 예약 정보 업데이트
  function updateReservationInfo() {
    if (checkInDate && checkOutDate) {
      const rentalHome = rentalHomeModel;
      const duration = caculateDuration(checkInDate, checkOutDate);
      const price = calculatePrice(duration, rentalHome);
      const total = price + rentalHome.cleanFee;
      const cleanFee = document.querySelector('.reservateion-cleanFee');

      durationElement.textContent = "이용 기간 : " + duration + "박 " + (duration + 1) + "일";
      priceElement.textContent = "가격 : " + price;
      priceTotalElement.textContent = "총액 : " + total;
      cleanFee.textContent = "청소비 : " + rentalHome.cleanFee;
    }
  }

  updateReservationInfo();

  // 날짜 계산
  function caculateDuration(checkInDate, checkOutDate) {
    const millisecondsInOneDay = 1000 * 60 * 60 * 24;
    const durationMilliseconds = checkOutDate.getTime() - checkInDate.getTime();
    return Math.floor(durationMilliseconds / millisecondsInOneDay);
  }

  // 가격 계산 함수
  function calculatePrice(duration, rentalHome) {
    const pricePerNight = rentalHome.price;
    return duration * pricePerNight;
  }

});

document.addEventListener("DOMContentLoaded", function () {
  console.log(111111);
  const reservationBtn = document.querySelector(".reservation-button");

  reservationBtn.addEventListener("click", function () {
    let checkInDate = document.getElementById("checkInDateInput").value;
    let checkOutDate = document.getElementById("checkOutDateInput").value;
    let guests = document.getElementById("reservationGuests").value;
    console.log("-----");
    console.log(checkInDate);
    console.log(checkOutDate);
    console.log(guests);
    console.log(rentalHomeNoModel);
    console.log(typeof rentalHomeNoModel);
    console.log("-----");
    if (checkInDate == "" || checkOutDate == "" || guests == "") {
      Swal.fire({
        icon: "error",
        title: "날짜 및 인원수를 확인해주세요.",
        showConfirmButton: false,
        timer: 1500
      });
    } else {
      $.ajax({
        url: "/rentalHome/reservation?rentalHomeNo=" + rentalHomeNoModel + "&checkInDate=" + checkInDate + "&checkOutDate=" + checkOutDate + "&guests=" + guests,
        type: "GET",
        success: (data) => {
          location.href = "/rentalHome/reservation?rentalHomeNo=" + rentalHomeNoModel + "&checkInDate=" + checkInDate + "&checkOutDate=" + checkOutDate + "&guests=" + guests;
        },
        error: () => {
          Swal.fire({
            icon: "error",
            title: "예약 오류.",
            showConfirmButton: false,
            timer: 1500
          });
        }
      })
    }
  })
});

// 즐겨찾기 추가
$('#bookMarkAdd').on('click', function(){
  if (sessionUser == null) {
    Swal.fire({
      icon: "error",
      title: "로그인이 필요합니다.",
    });
    return;
  }
  const memberNo = sessionUser.no;
  const rentalHomeNo = rentalHomeNoModel;
  $.ajax({
    type: "POST",
    url: "/bookmark/add",
    data:{
      memberNo: memberNo,
      rentalHomeNo: rentalHomeNo
    },
    success: function(data){
      Swal.fire({
        icon: "success",
        title: data,
        confirmButtonText: "확인"
      }).then((result)=>{
        if(result.isConfirmed){
          location.reload();
        }
      });
    },
    error: ()=>{
      Swal.fire({
        icon: "error",
        title: "즐겨찾기 추가 에러"
      });
    }
  });
});

// 즐겨찾기 삭제
$('#bookMarkDel').on('click',function(){
  const rentalHomeNo = rentalHomeNoModel;

  $.ajax({
    type: "POST",
    url: "/bookmark/delete",
    data:{
      rentalHomeNo: rentalHomeNo
    },
    success: function(data){
      Swal.fire({
        icon: "success",
        title: data,
        confirmButtonText: "확인"
      }).then((result)=>{
        if(result.isConfirmed){
          location.reload();
        }
      });
    },
    error: ()=>{
      Swal.fire({
        icon: "error",
        title: "즐겨찾기 삭제 에러"
      });
    }
  })
})