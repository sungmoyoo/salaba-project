$(document).ready(function(){
  
  reservationViewPageSet();
  rentalHomeRuleSet();
  initMap();
  setRefundContent();
  reservationCancel();
  reviewWrite();
});

const reservation = reservationInfo;


// 예약보기 페이지 셋팅
function reservationViewPageSet(){
  const session = sessionUser;
  let cardHtml = '<div class="container-fluid card mb-4"><div class="row g-0"><div class="col-md-4 align-self-center" style="max-height: 252px; max-width: 265px;">';
  cardHtml += '<div class="carousel slide" id="carousel-' + reservation.reservationNo + '" data-bs-ride="carousel">';
  cardHtml += '<div class="carousel-indicators">';

  $.each(reservation.photoList, function(photoIndex, photo) {
      let activeClass = (photoIndex === 0) ? 'active' : '';
      cardHtml += '<button type="button" data-bs-target="#carousel-' + reservation.reservationNo + '" data-bs-slide-to="' + photoIndex + '" class="' + activeClass + '" aria-current="true" aria-label="Slide ' + (photoIndex + 1) + '"></button>';
  });

  cardHtml += '</div>';
  cardHtml += '<div class="carousel-inner">';

  $.each(reservation.photoList, function(photoIndex, photo) {
      let activeClass = (photoIndex === 0) ? 'active' : '';
      cardHtml += '<div class="carousel-item ' + activeClass + '">';
      cardHtml += '<img src="https://5ns6sjke2756.edge.naverncp.com/nBMc0TCJiv/rentalHome/' + photo.uuidPhotoName + '?type=f&w=265&h=252&faceopt=false&ttype=jpg" class="d-block w-100" alt="...">';
      cardHtml += '</div>';
  });

  cardHtml += '</div>';
  cardHtml += '<button class="carousel-control-prev" type="button" data-bs-target="#carousel-' + reservation.reservationNo + '" data-bs-slide="prev">';
  cardHtml += '<span class="carousel-control-prev-icon" aria-hidden="true"></span><span class="visually-hidden">Previous</span></button>';
  cardHtml += '<button class="carousel-control-next" type="button" data-bs-target="#carousel-' + reservation.reservationNo + '" data-bs-slide="next">';
  cardHtml += '<span class="carousel-control-next-icon" aria-hidden="true"></span><span class="visually-hidden">Next</span></button></div></div>';

  cardHtml += '<div class="container col-md-9"><div class="card-body row">';
  cardHtml += '<div class="col-sm-10"><span class="card-title">'+ reservation.name +'</span></div>';
  cardHtml += '<div class="col-md-6 mt-3"><div class="row" id="card-text-box"><div class="col"><img src="https://5ns6sjke2756.edge.naverncp.com/nBMc0TCJiv/member/'+reservation.photo+ '?type=f&w=80&h=80&ttype=jpg"></div>';
  cardHtml += '<div class="col align-self-center"><span class="card-text" id="card-text-host-nickname">'+ reservation.nickname +'님의 숙소</span></div></div>'
  cardHtml += '<div id="card-text-box" class="mt-2"><span class="card-text" id="card-text-payment-date">결제일 : '+ reservation.payment.paymentDate +'</span></div>';
  cardHtml += '<div id="card-text-box" class="mt-2"><a href="/chat?sender=1&reservationNo='+reservation.reservationNo+'&name='+ session.name +'&memberNo='+ session.no +'&opponentName='+reservation.nickname+'"><span class="card-text" id="card-text-chatWithHost">호스트와 대화하기</span></a></div>';
  cardHtml += '<div id="card-text-box" class="mt-2"><span class="card-text" id="card-text-host-tel">'+ reservation.telNo +'</span></div></div>'
  cardHtml += '<div class="col-md-6 mt-3"><div class="card-text-box"><span class="card-text" id="card-text-start-date">체크인 : '+ reservation.startDate +'</span></div>';
  cardHtml += '<div id="card-text-box" class="mt-2"><span class="card-text" id="card-text-end-date">체크아웃'+ reservation.endDate +'</span></div>';
  cardHtml += '<div id="card-text-box" class="mt-2"><span class="card-text" id="card-text-people"> 이용인원 : '+ reservation.numberOfPeople +'명</span></div>';
  cardHtml += '</div>';
  cardHtml += '<div class="text-md-end mb-2"><button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#rentalHomePhotoList">숙소사진 전체보기</button></div></div></div></div></div>';
  
  $('#reservationView-photo-container').append(cardHtml);
}


 // 구글 지도
    (g=>{var h,a,k,p="The Google Maps JavaScript API",c="google",l="importLibrary",q="__ib__",m=document,b=window;b=b[c]||(b[c]={});var d=b.maps||(b.maps={}),r=new Set,e=new URLSearchParams,u=()=>h||(h=new Promise(async(f,n)=>{await (a=m.createElement("script"));e.set("libraries",[...r]+"");for(k in g)e.set(k.replace(/[A-Z]/g,t=>"_"+t[0].toLowerCase()),g[k]);e.set("callback",c+".maps."+q);a.src=`https://maps.${c}apis.com/maps/api/js?`+e;d[q]=f;a.onerror=()=>h=n(Error(p+" could not load."));a.nonce=m.querySelector("script[nonce]")?.nonce||"";m.head.append(a)}));d[l]?console.warn(p+" only loads once. Ignoring:",g):d[l]=(f,...n)=>r.add(f)&&u().then(()=>d[l](f,...n))})({
      key: "AIzaSyCn7sXQ-7jFww0vK_pndKEMLEsJfMxAsmk",
      // Add other bootstrap parameters as needed, using camel case.
      // Use the 'v' parameter to indicate the version to load (alpha, beta, weekly, etc.)
    });

async function initMap() {
  const position = { lat:Number(reservation.lat) , lng:Number(reservation.lon) };

   console.log( typeof reservation.lat );
   console.log( typeof reservation.lon );

  const{ Map } = await google.maps.importLibrary("maps");
  const{ AdvancedMarkerElement, PinElement } = await google.maps.importLibrary("marker");

  let map = new Map(document.getElementById("map"), {
    zoom : 16,
    center: position,
    mapId: '470e45162ba863f'
  });

  const marker = new google.maps.marker.AdvancedMarkerElement({
    map: map,
    position: position,
  });
}

// 숙소 이용 규칙
function rentalHomeRuleSet(){
  const rentalHomeRule = $("#rentalHomeRule-text");
  const hiddenRentalHomeRule = $("#hiddenRentalHomeRule");
  const showMoreButton = $("#showMoreButton");

  // 이용규칙 길이 확인
  const ruleLength = rentalHomeRule.text().length;
  const maxLength = 20; // 원하는 최대 길이 설정

  if (ruleLength > maxLength) {
    hiddenRentalHomeRule.text(rentalHomeRule.text().substring(maxLength));
    rentalHomeRule.text(rentalHomeRule.text().substring(0, maxLength) + "...");
    showMoreButton.css("display", "block");
  }
}

// 예약 환불 정책
function setRefundContent(){
  // 체크인 날짜
  const checkInDate = reservation.startDate;

  // 체크인 날짜의 2일 전
  const refundableDate = new Date(checkInDate);
  refundableDate.setDate(refundableDate.getDate() - 2);

  // 체크인 날짜의 2일 전 12:00
  refundableDate.setHours(12, 0, 0, 0);

  // 환불 가능한 날짜
  const formattedDate = refundableDate.toLocaleString('ko-KR', { year: 'numeric', month: 'long', day: 'numeric', hour: 'numeric', minute: 'numeric', hour12: true });
  const refundPolicyText = `체크인 날짜의 2일 전 오후 12:00 전에 취소하면 환불을 받으실 수 있습니다. (최대 환불일시: ${formattedDate})`;
  
  // span 요소에 텍스트 추가
  $('#refund-content span').text(refundPolicyText);
}

// 예약 취소
function reservationCancel(){
  $('#reservationCancelButton').on('click', function(){
    $.ajax({
      type: "POST",
      url: "/member/reservation/cancel",
      data:{
        reservationNo: reservation.reservationNo
      },
      success:function(data){
        if( data == 1 ){
          Swal.fire({
            title: "예약이 취소되었습니다",
            icon: "success",
            confirmButtonText: "확인"
          }).then((result)=>{
            if(result.isConfirmed) {
              location.href = "/member/reservationList";
            }
          });
        }else{
          Swal.fire({
            icon: "error",
            title: "예약취소 실패"
          });
        }
      },
      error:()=>{
        Swal.fire({
          icon: "error",
          title: "예약취소 에러"
        });
      }
    });
  });
}

// 리뷰 작성
function reviewWrite(){
  $('#reservationReviewButton').on('click', function(){
    let review = $('#reviewText').val();
    let score = $('#reviewScore').val();
    $.ajax({
      type: "POST",
      url: "/rentalHome/addReview",
      data:{
        review: review,
        score: score,
        reservationNo: reservation.reservationNo,
        rentalHomeNo: reservation.rentalHomeNo
      },
      success: function(data){
        Swal.fire({
          icon:"success",
          title: data
        });
      },
      error: () =>{
        Swal.fire({
          icon: "error",
          title: "리뷰 작성 에러"
        });
      }
    });
  });
}