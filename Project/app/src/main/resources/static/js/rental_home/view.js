(g=>{var h,a,k,p="The Google Maps JavaScript API",c="google",l="importLibrary",q="__ib__",m=document,b=window;b=b[c]||(b[c]={});var d=b.maps||(b.maps={}),r=new Set,e=new URLSearchParams,u=()=>h||(h=new Promise(async(f,n)=>{await (a=m.createElement("script"));e.set("libraries",[...r]+"");for(k in g)e.set(k.replace(/[A-Z]/g,t=>"_"+t[0].toLowerCase()),g[k]);e.set("callback",c+".maps."+q);a.src=`https://maps.${c}apis.com/maps/api/js?`+e;d[q]=f;a.onerror=()=>h=n(Error(p+" could not load."));a.nonce=m.querySelector("script[nonce]")?.nonce||"";m.head.append(a)}));d[l]?console.warn(p+" only loads once. Ignoring:",g):d[l]=(f,...n)=>r.add(f)&&u().then(()=>d[l](f,...n))})({
    key: "AIzaSyCn7sXQ-7jFww0vK_pndKEMLEsJfMxAsmk",
    // Add other bootstrap parameters as needed, using camel case.
    // Use the 'v' parameter to indicate the version to load (alpha, beta, weekly, etc.)
  });

const reportModal = document.getElementById("reportModal");
const reportButton = document.getElementById("reportButton");
const cancelButton = document.getElementById("cancelButton");

// 신고 버튼 클릭 시
reportButton.addEventListener("click",()=>{
  reportModal.style.display = "block";
});

// 취소 버튼 클릭 시
cancelButton.addEventListener("click", ()=>{
  reportModal.style.display = "none";
});

// 외부 클릭시 닫기
window.addEventListener("click",(event)=>{
  if( event.target == reportModal ){
    reportModal.style.display = "none";
  }
});

// form 제출 시 데이터 전송 AJAX 사용
document.getElementById("reportForm").addEventListener("submit",(event)=>{
  event.preventDefault(); // 기본 동작 취소

  const category = document.getElementById("category").value;
  const content = document.getElementById("rentalHome-report-content").value;
  const memberNo = sessionMemberNo;
  const rentalHomeNo = rentalHomeNoModel;

  // RentalHomeReport 객체 생성
  const rentalHomeReport = {
    rentalHomeNo: rentalHomeNo,
    memberNo: memberNo,
    content: content,
    reportCategoryNo: category
  }

  // Ajax 요청 보내기
  $.ajax({
    type: "POST",
    url: "/rentalHome/report",
    contentType: "application/json",
    data: JSON.stringify(rentalHomeReport),
    success: function(response) {
      alert("신고가 접수되었습니다.");
      // 신고 닫기
      $("#reportModal").css("display", "none");
    },
    error: function() {
      alert("오류가 발생했습니다. 다시 시도해주세요.");
    }
  });
});

document.addEventListener("DOMContentLoaded", function(){
  const explanationContent = rentalHomeModel.explanation; // 숙소 소개
  const modal = document.querySelector('.explanationModal'); // modal 창
  const rentalHomeExplanation = document.querySelector('.explanation-content'); // modal 안쪽에 표현할 숙소 소개
  const showMoreExplanation = document.querySelector('.showMoreExplanation'); // 더 보기 버튼
  const closeBtn = document.querySelector('.explanation-modal-close'); // modal 닫기

  const rentalHomeContent = document.querySelector('.rentalHome-explanation-h');
  
  const maxLength = 20; // 숙소 소개 보여줄 최대 글자 수

  if(explanationContent.length > maxLength){
    // 최대 글자수 만큼 자르고 뒤에 ... 표시
    rentalHomeContent.textContent = explanationContent.substring(0, maxLength) + "...";
    showMoreExplanation.style.display = "block"; // 더 보기 버튼 표시
  }else{
    rentalHomeExplanation.innerHTML = explanationContent;
    showMoreExplanation.style.display = none;
  }

  // 더 보기 버튼 클릭 시
  showMoreExplanation.addEventListener("click", function(){
    rentalHomeExplanation.innerHTML = explanationContent;
    modal.style.display = "block"
  });

  // modal 의 닫기 버튼
  closeBtn.addEventListener("click", function(){
    modal.style.display = "none";
  });

  // modal 외부를 클릭 시
  window.addEventListener("click",function(event){
    if(event.target == modal){
      modal.style.display = "none";
    }
  });
});

let startIndex = 0;
const batchSize = 5;
let rentalHomeReviews = rentalHomeReviewModel;

function showNextBatchOfReview() {
  const reviewsContainer = document.getElementById('reviewsContainer');
  const endIndex = Math.min(startIndex + batchSize, rentalHomeReviews.length);
  for (let i = startIndex; i < endIndex; i++) {
    const review = rentalHomeReviews[i];
    const reviewElement = document.createElement('div');
    reviewElement.innerHTML = '<div>' +
      '<h4>평점</h4>' +
      '<p>' + review.review + '</p>' + // 리뷰 내용
      '<p>' + review.createdDate + '</p>' + // 작성일시
      '</div>';
    reviewsContainer.appendChild(reviewElement);
  }
  startIndex = endIndex;
  if (startIndex >= rentalHomeReviews.length) {
    document.getElementById('showMoreReview').style.display = 'none';
  }
}

// 리뷰 더 보기 버튼 클릭 시
document.getElementById('showMoreReview').addEventListener('click', function() {
showNextBatchOfReview();
});

// 초기에 최초의 2개 리뷰 표시
window.addEventListener('DOMContentLoaded', function() {
const reviewsContainer = document.getElementById('reviewsContainer');
for (let i = 0; i < Math.min(2, rentalHomeReviews.length); i++) {
  const review = rentalHomeReviews[i];
  const reviewElement = document.createElement('div');
  reviewElement.innerHTML = '<div>' +
    '<h4>평점</h4>' +
    '<p>' + review.review + '</p>' + // 리뷰 내용
    '<p>' + review.createdDate + '</p>' + // 작성일시
    '</div>';
  reviewsContainer.appendChild(reviewElement);
}
startIndex = 2;
if (rentalHomeReviews.length <= 2) {
  document.getElementById('showMoreReview').style.display = 'none'; // 버튼 숨기기
}
});

async function initMap(){
const rentalHome = rentalHomeModel;
const position = { lat:Number(rentalHome.lat) , lng:Number(rentalHome.lon) };

console.log( typeof rentalHome.lat );
console.log( typeof rentalHome.lon );

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

initMap();
