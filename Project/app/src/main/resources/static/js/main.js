(g=>{var h,a,k,p="The Google Maps JavaScript API",c="google",l="importLibrary",q="__ib__",m=document,b=window;b=b[c]||(b[c]={});var d=b.maps||(b.maps={}),r=new Set,e=new URLSearchParams,u=()=>h||(h=new Promise(async(f,n)=>{await (a=m.createElement("script"));e.set("libraries",[...r]+"");for(k in g)e.set(k.replace(/[A-Z]/g,t=>"_"+t[0].toLowerCase()),g[k]);e.set("callback",c+".maps."+q);a.src=`https://maps.${c}apis.com/maps/api/js?`+e;d[q]=f;a.onerror=()=>h=n(Error(p+" could not load."));a.nonce=m.querySelector("script[nonce]")?.nonce||"";m.head.append(a)}));d[l]?console.warn(p+" only loads once. Ignoring:",g):d[l]=(f,...n)=>r.add(f)&&u().then(()=>d[l](f,...n))})({
    key: "AIzaSyCn7sXQ-7jFww0vK_pndKEMLEsJfMxAsmk",
    // Add other bootstrap parameters as needed, using camel case.
    // Use the 'v' parameter to indicate the version to load (alpha, beta, weekly, etc.)
  });

const si = sessionInfo;
const sl = loginUser;

console.log(sessionInfo);
console.log(loginUser);
sessionStorage.setItem("memberNo", loginUser.no);

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
async function initMap(){
  let rentalHomeProp = [];
  let rentalHome = rentalHomeList; // 숙소 목록
  
  let priceTag = document.createElement("div");
  priceTag.className = "price-tag"

  // 숙소 목록에 대한 정보 작성
  rentalHomeList.forEach(rentalHome =>{
    let photos = [];
    rentalHome.rentalHomePhotos.forEach(photo =>{
      photos.push( photo.uuidPhotoName );
    });
    rentalHomeProp.push( { 
      photo: photos,
      name: rentalHome.name,
      price: rentalHome.price,
      position: {lat:Number(rentalHome.lat) , lng:Number(rentalHome.lon)},
     });
  });

  // googleMap 변수 / 라이브러리
  const{ Map } = await google.maps.importLibrary("maps");
  const{ AdvancedMarkerElement, PinElement } = await google.maps.importLibrary("marker");
  
  // google맵 초기 위치 설정
  let map = new Map(document.getElementById("map"), {
    zoom : 10,
    center: {lat:rentalHomeProp[0].position.lat, lng:rentalHomeProp[0].position.lng},
    mapId: '470e45162ba863f'
  });

  // 구글맵에 표시할 마커에 대한 정보 설정
  for( const property of rentalHomeProp ){
    const advancedMarkerView = new google.maps.marker.AdvancedMarkerElement({
      map,
      // content: buildContent(property), // 구글맵 마커 안쪽에서 표시할 것들 설정
      position:property.position, // 구글맵 마커 위치 설정
      title:property.name,
    });
    
    // 마커 동작에 대한 event
    const element = advancedMarkerView.element;
    ["focus", "pointerenter"].forEach((event) => {
      element.addEventListener(event, ()=>{
        highlight(advancedMarkerView, property);
      });
    });
    ["blur","pointerleave"].forEach((event)=>{
      element.addEventListener(event, () =>{
        unhighlight(advancedMarkerView, property);
      });
    });
    advancedMarkerView.addListener("click",(event) =>{
      unhighlight(advancedMarkerView, property);
    });
  }
}

function highlight(markerView, property){
  markerView.content.classList.add("highlight");
  markerView.element.style.zIndex = 1;
}

function unhighlight(markerView, property){
  markerView.content.classList.remove("highlight");
  markerView.element.style.zIndex = "";
}

// googleMap 마커 내용 설정
function buildContent(property){
  let content = document.createElement("div");
  
  content.classList.add("property");
  content.innerHTML = "<div class='price-tag'><span class='mapPrice'>"+property.price+"</span></div>";
  
  let mapSlideContainer = document.createElement("div");
  mapSlideContainer.classList.add("mapSlide-container");
  
  // 숙소 이미지 설정 appendChild로 html에서 자식으로 부여되도록 함
  for( var i = 0; i < property.photo.length; i++ ){
    let img = document.createElement("img");
    img.classList.add("mapSlider-image");
    img.src = 'https://5ns6sjke2756.edge.naverncp.com/nBMc0TCJiv/rentalHome/" + property.photo[i] + "?type=f&w=150&h=150&ttype=jpg';
    img.alt = "숙소이미지";
    mapSlideContainer.appendChild(img);
  }
  content.appendChild(mapSlideContainer);

  content.innerHTML += "<button class='slide-button prev-button'>&lt;</button><button class='slide-button next-button'>&gt;</button><span>" + property.name + "</span><span>" + property.price + "</span>";

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

let filteredRentalHomeList = rentalHomeList;

function getFilterData(){ // 필터 적용
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

  // 필터링된 숙소의 갯수 업데이트
  updateButtonLabel( filteredRentalHomeList.length );

  // 필터링된 숙소 리스트 업데이트
  updateRentalHomeList(filteredRentalHomeList);

  // 팝업 닫기
  closeFilterPopup();
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

// 숙소 이미지 슬라이더 이벤트
function bindSliderEvents(listItem){
  let $slideImages = listItem.find('.slide-image');
  let currentIndex = 0;

  function showSlide(index){
    $slideImages.hide().eq(index).show();
  }

  function showNextSlide(){
    currentIndex = (currentIndex + 1) % $slideImages.length;
    showSlide(currentIndex);
  }

  function showPreviousSlide(){
    currentIndex = (currentIndex -1 + $slideImages.length) % $slideImages.length;
    showSlide(currentIndex);
  }

  listItem.find('.next-button').off('click').on('click', showNextSlide);
  listItem.find('.prev-button').off('click').on('click', showPreviousSlide);

  showSlide(currentIndex);
}

// 각 숙소의 이미지 슬라이더 처리
$('.listing-item').each(function () {
  const $slideImages = $(this).find('.slide-image');
  let currentIndex = 0;

  function showSlide(index) {
    $slideImages.hide().eq(index).show();
  }

  function showNextSlide() {
    currentIndex = (currentIndex + 1) % $slideImages.length;
    showSlide(currentIndex);
  }

  function showPreviousSlide() {
    currentIndex = (currentIndex - 1 + $slideImages.length) % $slideImages.length;
    showSlide(currentIndex);
  }

  $(this).find('.next-button').click(showNextSlide);
  $(this).find('.prev-button').click(showPreviousSlide);

  showSlide(currentIndex);
});

// 숙소 목록 재할당
function updateRentalHomeList(newRentalHomeList){
  let listContainer = $('.listing-container'); // 가장 바깥 html 태그의 class 아이디
  listContainer.empty();

  newRentalHomeList.forEach(function(rentalHome){ // html을 재할당된 숙소 목록으로 재작성
      let listItem = $( '<div class="listing-item">' );
      let sliderDiv = $( '<div class="listing-slider">' );
      
      rentalHome.rentalHomePhotos.forEach(function(photos){
        let image = $( '<img class="slide-image">' )
        .attr( 'src', 'https://5ns6sjke2756.edge.naverncp.com/nBMc0TCJiv/rentalHome/' + photos.uuidPhotoName + '?type=f&w=265&h=252&faceopt=false&ttype=jpg' )
        .attr( 'alt', '숙소이미지' );
        sliderDiv.append(image);
      });
      let imageLeftButton = $('<button class="slide-button prev-button"></button>');
      let imageRightButton = $('<button class="slide-button next-button"></button>');
      let nameSpan = $('<span>').text(rentalHome.name);
      let priceSpan = $('<span>').text(rentalHome.price);
      
      sliderDiv.append(imageLeftButton);
      sliderDiv.append(imageRightButton);
      sliderDiv.append(nameSpan);
      sliderDiv.append(priceSpan);
      listItem.append(sliderDiv);

      listContainer.append(listItem); // html 작성한것 적용
      
      // 사진 슬라이더 이벤트 재할당
      bindSliderEvents(listItem);
  });
}

// 테마 검색 후 숙소 목록 재할당
function handleNewRentalHomeList(newRentalHomeList){
  updateRentalHomeList(newRentalHomeList);
}
