(g=>{var h,a,k,p="The Google Maps JavaScript API",c="google",l="importLibrary",q="__ib__",m=document,b=window;b=b[c]||(b[c]={});var d=b.maps||(b.maps={}),r=new Set,e=new URLSearchParams,u=()=>h||(h=new Promise(async(f,n)=>{await (a=m.createElement("script"));e.set("libraries",[...r]+"");for(k in g)e.set(k.replace(/[A-Z]/g,t=>"_"+t[0].toLowerCase()),g[k]);e.set("callback",c+".maps."+q);a.src=`https://maps.${c}apis.com/maps/api/js?`+e;d[q]=f;a.onerror=()=>h=n(Error(p+" could not load."));a.nonce=m.querySelector("script[nonce]")?.nonce||"";m.head.append(a)}));d[l]?console.warn(p+" only loads once. Ignoring:",g):d[l]=(f,...n)=>r.add(f)&&u().then(()=>d[l](f,...n))})({
    key: "AIzaSyCn7sXQ-7jFww0vK_pndKEMLEsJfMxAsmk",
    // Add other bootstrap parameters as needed, using camel case.
    // Use the 'v' parameter to indicate the version to load (alpha, beta, weekly, etc.)
  });

const si = sessionInfo;
const sl = loginUser;

console.log(sessionInfo);
console.log(loginUser);

// 지도 보기 / 목록보기 버튼
const mapButton = document.getElementById('mapButton');
const listContainer = document.querySelector('.listing-container');
const mapContainer = document.querySelector('.map-section');
const mapButtonText = document.querySelector('.mapButton-text');
const mapButtonIcon = document.querySelector('.mapButton-icon');
let isMapView = false;

// svg 아이콘 요소
const firstIcon = mapButtonIcon.children[0];
const secondIcon = mapButtonIcon.children[1];

// 초기에 보이지 않음
secondIcon.style.display = 'none';

// 지도로 보기 버튼
mapButton.addEventListener('click', function(){
  isMapView = !isMapView;

  if(isMapView){
    mapButtonText.textContent = "목록 보기";
    mapContainer.style.display = 'flex';
    listContainer.style.display = 'none';

    firstIcon.style.display = 'none';
    secondIcon.style.display = 'block';
  }else{
    mapButtonText.textContent = "지도 보기";
    mapContainer.style.display = 'none';
    listContainer.style.display = 'flex';

    firstIcon.style.display = 'block';
    secondIcon.style.display = 'none';
  }
  
  mapContainer.style.width = "1920px";
  mapContainer.style.height = "1028px";
  initMap();
});

// googleMap 초기화
async function initMap() {
  let rentalHomeProp = [];
  let rentalHome = rentalHomeList; // 숙소 목록
  
  // 숙소 목록에 대한 정보 작성
  rentalHomeList.forEach(rentalHome => {
    let photos = [];
    rentalHome.rentalHomePhotos.forEach(photo => {
      photos.push(photo.uuidPhotoName);
    });
    rentalHomeProp.push({
      rentalHomeNo: rentalHome.rentalHomeNo,
      photo: photos,
      name: rentalHome.name,
      price: rentalHome.price,
      position: { lat: Number(rentalHome.lat), lng: Number(rentalHome.lon) }
    });
  });

  // googleMap 변수 / 라이브러리
  const { Map } = await google.maps.importLibrary("maps");
  const { AdvancedMarkerElement } = await google.maps.importLibrary("marker");
  
  // google맵 초기 위치 설정
  let map = new Map(document.getElementById("map"), {
    zoom: 10,
    center: { lat: rentalHomeProp[0].position.lat, lng: rentalHomeProp[0].position.lng },
    mapId: '470e45162ba863f'
  });

  // 구글맵에 표시할 마커에 대한 정보 설정
  for (const property of rentalHomeProp) {
    const marker = new google.maps.Marker({
      position: property.position,
      map: map,
      title: property.name,
    });
    
    // 마커 클릭 이벤트 추가
    const infowindow = new google.maps.InfoWindow({
      content: buildContent(property)
    });
    
    marker.addListener("click", () => {
      infowindow.open(map, marker);
      // 캐러셀 초기화
      setTimeout(() => {
        const myCarousel = document.querySelector(`#carousel-${property.rentalHomeNo}`);
        const carousel = new bootstrap.Carousel(myCarousel, {
          interval: 2000
        });
      }, 0); // DOM이 갱신된 후 캐러셀을 초기화하기 위해 setTimeout 사용
    });
  }
}

// googleMap 마커 내용 설정
function buildContent(property) {
  let content = document.createElement("div");
  content.classList.add("property");

  let carouselItems = '';
  property.photo.forEach(function(photo, index) {
    carouselItems += `
      <div class="carousel-item ${index === 0 ? 'active' : ''}">
        <a href="rentalHome/view?rentalHomeNo=${property.rentalHomeNo}" name="rentalHomeNo">
          <img class="slide-image" src="https://5ns6sjke2756.edge.naverncp.com/nBMc0TCJiv/rentalHome/${photo}?type=f&w=265&h=252&faceopt=false&ttype=jpg" alt="숙소 이미지">
        </a>
      </div>
    `;
  });

  content.innerHTML = `
  <div class="details">
    <div class="card p-2 mx-auto mb-4">
      <div id="carousel-${property.rentalHomeNo}" class="carousel slide">
        <div class="carousel-inner">
          ${carouselItems}
        </div>
        <button class="carousel-control-prev" type="button" data-bs-target="#carousel-${property.rentalHomeNo}" data-bs-slide="prev">
          <span class="carousel-control-prev-icon" aria-hidden="true"></span>
          <span class="visually-hidden">Previous</span>
        </button>
        <button class="carousel-control-next" type="button" data-bs-target="#carousel-${property.rentalHomeNo}" data-bs-slide="next">
          <span class="carousel-control-next-icon" aria-hidden="true"></span>
          <span class="visually-hidden">Next</span>
        </button>
      </div>
      <div class="card-body">
        <div class="listing-text">
          <span>${property.name}</span>
        </div>
        <div class="listing-price">
          <span>$ </span>
          <span>${property.price}</span>
        </div>
      </div>
    </div>
  </div>
  `;

  return content;
}

function openFilterPopup(){ // 필터 팝업창 열기
  document.getElementById("filterPopup").style.display = "block";
}
function closeFilterPopup(){ // 필터 팝업창 닫기
  document.getElementById("filterPopup").style.display = "none";
}

// 버튼의 텍스트를 업데이트
function updateButtonLabel(count){
  const button = document.querySelector(".apply-filter-button");
  button.textContent = count+'개의 숙소 보기';
}

let filteredRentalHomeList;

function getFilterData(){ // 필터 적용
  console.log(rentalHomeList);
  let themes = [];
  let checkboxes = document.querySelectorAll('input[name="theme"]:checked');
  checkboxes.forEach(checkbox => {
    themes.push(checkbox.value);
  });
  let minPrice = document.getElementById('minPrice').value;
  let maxPrice = document.getElementById('maxPrice').value;

  // 숙소List filter
  filteredRentalHomeList = Object.values(rentalHomeList).filter(function(rentalHome){
    if( themes != null ){ // theme를 선택했을때, 조건에 맞는 rentalHome return
      return rentalHome.themes.some(theme => themes.includes(theme.themeName)) && rentalHome.price >= minPrice && rentalHome.price <= maxPrice;
    }
    if( themes == null ){ // theme를 선택 안했을때 해당 조건에 맞는 rentalHome return
      return rentalHome.price >= minPrice && rentalHome.price <= maxPrice; 
    }
  });

  console.log(filteredRentalHomeList.length);
  console.log(filteredRentalHomeList);

  // 필터링된 숙소 리스트 업데이트
  updateRentalHomeList(filteredRentalHomeList);
}

function scrollThemes(offset) { // 테마 스크롤 이벤트
  const container = document.querySelector('.theme-container');
  const currentScroll = container.scrollLeft;
  const newScroll = currentScroll + offset;
  container.scrollTo({
    left:newScroll,
    behavior:'smooth'
  });

  const scrollRightButton = document.querySelector('.scroll-right-button');
  const scrollLeftButton = document.querySelector('.scroll-left-button');
  const buttonWidth = scrollRightButton.offsetWidth;
  const containerWidth = container.offsetWidth;

  // 오른쪽 버튼
  if( newScroll < container.scrollWidth - containerWidth ){
    scrollRightButton.style.display = 'block';
    scrollRightButton.style.right = '${containerWidth - buttonWidth}px';
  }
  
  // 왼쪽 버튼
  if( newScroll > 0 ){
    scrollLeftButton.style.display = 'block';
    scrollLeftButton.style.left = '${containerWidth - buttonWidth}px';
  }
}

// 숙소 목록 재할당
function updateRentalHomeList(newRentalHomeList) {
  let listContainer = $('.listing-container'); // 가장 바깥 html 태그의 class 아이디
  listContainer.empty();

  newRentalHomeList.forEach(function(rentalHome) { // html을 재할당된 숙소 목록으로 재작성
    let cardDiv = $('<div class="card p-2 mx-auto mb-4" style="width: 18rem;">');

    let carouselDiv = $('<div class="carousel slide" data-bs-ride="carousel">');
    carouselDiv.attr('id', 'carousel-'+rentalHome.rentalHomeNo);
    let carouselInnerDiv = $('<div class="carousel-inner">');

    rentalHome.rentalHomePhotos.forEach(function(photo, index) {
      let carouselItemDiv = $('<div class="carousel-item">');
      if (index === 0) {
        carouselItemDiv.addClass('active');
      }
      let imageAnchor = $('<a>')
        .attr('href', 'rentalHome/view?rentalHomeNo=' + rentalHome.rentalHomeNo)
        .attr('name', 'rentalHomeNo');
      let image = $('<img class="slide-image">')
        .attr('src', 'https://5ns6sjke2756.edge.naverncp.com/nBMc0TCJiv/rentalHome/' + photo.uuidPhotoName + '?type=f&w=265&h=252&faceopt=false&ttype=jpg')
        .attr('alt', '숙소 이미지');
      imageAnchor.append(image);
      carouselItemDiv.append(imageAnchor);
      carouselInnerDiv.append(carouselItemDiv);
    });

    let prevButton = $('<button class="carousel-control-prev" type="button" data-bs-target="#carousel-'+ rentalHome.rentalHomeNo +'" data-bs-slide="prev">');
    prevButton.append('<span class="carousel-control-prev-icon" aria-hidden="true"></span><span class="visually-hidden">Previous</span>');

    let nextButton = $('<button class="carousel-control-next" type="button" data-bs-target="#carousel-'+rentalHome.rentalHomeNo+'" data-bs-slide="next">');
    nextButton.append('<span class="carousel-control-next-icon" aria-hidden="true"></span><span class="visually-hidden">Next</span>');

    carouselDiv.append(carouselInnerDiv);
    carouselDiv.append(prevButton);
    carouselDiv.append(nextButton);

    let cardBodyDiv = $('<div class="card-body">');
    let listingTextDiv = $('<div class="listing-text">');
    let nameSpan = $('<span>').text(rentalHome.name);
    let listingPriceDiv = $('<div class="listing-price">');
    let dollarSpan = $('<span>').text('$ ');
    let priceSpan = $('<span>').text(rentalHome.price);

    listingTextDiv.append(nameSpan);
    listingPriceDiv.append(dollarSpan);
    listingPriceDiv.append(priceSpan);

    cardBodyDiv.append(listingTextDiv);
    cardBodyDiv.append(listingPriceDiv);

    cardDiv.append(carouselDiv);
    cardDiv.append(cardBodyDiv);

    listContainer.append(cardDiv); // html 작성한것 적용
  });
}

// 테마 검색 후 숙소 목록 재할당
function handleNewRentalHomeList(newRentalHomeList){
  updateRentalHomeList(newRentalHomeList);
}

$(document).ready(function() {
  const rentalHome = rentalHomeList;
  rentalHome.forEach(item=>{
    $('#carousel-' + item.rentalHomeNo + ' .carousel-control-prev').attr('data-bs-target', '#carousel-' + item.rentalHomeNo);
    $('#carousel-' + item.rentalHomeNo + ' .carousel-control-next').attr('data-bs-target', '#carousel-' + item.rentalHomeNo);
  });
});

// function bindcarousel( rentalHomeList ){
//   const rentalHome = rentalHomeList;
//   rentalHome.forEach(item=>{
//     $('#carousel-' + item.rentalHomeNo + ' .carousel-control-prev').attr('data-bs-target', '#carousel-' + item.rentalHomeNo);
//     $('#carousel-' + item.rentalHomeNo + ' .carousel-control-next').attr('data-bs-target', '#carousel-' + item.rentalHomeNo);
//   });
// }