// 각 숙소의 이미지 슬라이더 처리
  $('.listing-item').each(function () {
    const $slideImages = $(this).find('.slide-image');
    console.log($slideImages);
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

initMap();