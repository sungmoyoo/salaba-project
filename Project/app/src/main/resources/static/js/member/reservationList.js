// SideMenu Active
function sideMenuActivereservationList(){
  $('#sideMenu-nav a.active').removeClass('active');
  const sideMenu = $('#sideMenu-reservationList');
  sideMenu.addClass('active');
}


$(document).ready(function() {
  sideMenuActivereservationList();
  reservationListPageSet();
});

function reservationListPageSet(){
  const reservationList = reservationInfo;
  // bookMarkList에서 각 bookMark를 순회하며 카드 형태의 캐러셀을 생성합니다.
  $.each(reservationList, function(reservationIndex, reservation) {
      // 각 bookMark에 대한 카드를 생성합니다.
      let cardHtml = '<div class="card mb-4"><div class="row g-0"><div class="col-md-4">';
      // 캐러셀을 추가합니다.
      cardHtml += '<div class="carousel slide" id="carousel-' + reservationIndex + '" data-bs-ride="carousel">';
      cardHtml += '<div class="carousel-indicators">';

      // 캐러셀 인디케이터를 추가합니다.
      $.each(reservation.photoList, function(photoIndex, photo) {
          let activeClass = (photoIndex === 0) ? 'active' : '';
          cardHtml += '<button type="button" data-bs-target="#carousel-' + reservationIndex + '" data-bs-slide-to="' + photoIndex + '" class="' + activeClass + '" aria-current="true" aria-label="Slide ' + (photoIndex + 1) + '"></button>';
      });

      cardHtml += '</div>';
      cardHtml += '<div class="carousel-inner">';

      // 캐러셀 아이템을 추가합니다.
      $.each(reservation.photoList, function(photoIndex, photo) {
          let activeClass = (photoIndex === 0) ? 'active' : '';
          cardHtml += '<div class="carousel-item ' + activeClass + '">';
          cardHtml += '<img src="https://5ns6sjke2756.edge.naverncp.com/nBMc0TCJiv/rentalHome/' + photo.uuidPhotoName + '?type=f&w=265&h=252&faceopt=false&ttype=jpg" class="d-block w-100" alt="...">';
          cardHtml += '</div>';
      });

      cardHtml += '</div>';
      cardHtml += '<button class="carousel-control-prev" type="button" data-bs-target="#carousel-' + reservationIndex + '" data-bs-slide="prev">';
      cardHtml += '<span class="carousel-control-prev-icon" aria-hidden="true"></span><span class="visually-hidden">Previous</span></button>';
      cardHtml += '<button class="carousel-control-next" type="button" data-bs-target="#carousel-' + reservationIndex + '" data-bs-slide="next">';
      cardHtml += '<span class="carousel-control-next-icon" aria-hidden="true"></span><span class="visually-hidden">Next</span></button></div></div>';

      // 카드 내용을 추가합니다.
      cardHtml += '<div class="col-md-8"><div class="card-body row">';
      cardHtml += '<div class="col-sm-10"><span class="card-title">'+ reservation.name +'</span></div>';
      cardHtml += '<div class="col-sm-2" id="link-box"><a id="rentalHome-Link" href="/member/reservationView?reservationNo='+ reservation.reservationNo +'"><span>바로가기</span></a></div>';
      cardHtml += '<div id="card-text-box"><span class="card-text" id="card-text-start-date">이용기간 : '+ reservation.startDate +' ~ </span>';
      cardHtml += '<span class="card-text" id="card-text-end-date">'+ reservation.endDate +'</span>';
      cardHtml += '<span class="card-text" id="card-text-people"> 이용인원 : '+ reservation.numberOfPeople +'</span></div>'
      
      // // 상태가 "완료됨"인 경우 리뷰 쓰러가기 링크 추가
      // if (reservation.state === "완료됨") {
      //   cardHtml += '<div id="review-link-box"><a id="review-Link" href="/member/reviewWrite?reservationNo='+ reservation.reservationNo +'"><span>리뷰 쓰러가기</span></a></div>';
      // }

      cardHtml += '<div class="col align-self-end card-delete-box"><span id="card-text-reservation-state">'+ reservation.state +'</span></div>';
      cardHtml += '</div></div></div></div>';

      // 페이지에 카드를 추가합니다.
      $('#reservation-contents-container').append(cardHtml);
  });
}
