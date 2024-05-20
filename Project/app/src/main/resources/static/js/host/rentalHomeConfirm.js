(g=>{var h,a,k,p="The Google Maps JavaScript API",c="google",l="importLibrary",q="__ib__",m=document,b=window;b=b[c]||(b[c]={});var d=b.maps||(b.maps={}),r=new Set,e=new URLSearchParams,u=()=>h||(h=new Promise(async(f,n)=>{await (a=m.createElement("script"));e.set("libraries",[...r]+"");for(k in g)e.set(k.replace(/[A-Z]/g,t=>"_"+t[0].toLowerCase()),g[k]);e.set("callback",c+".maps."+q);a.src=`https://maps.${c}apis.com/maps/api/js?`+e;d[q]=f;a.onerror=()=>h=n(Error(p+" could not load."));a.nonce=m.querySelector("script[nonce]")?.nonce||"";m.head.append(a)}));d[l]?console.warn(p+" only loads once. Ignoring:",g):d[l]=(f,...n)=>r.add(f)&&u().then(()=>d[l](f,...n))})({
    key: "AIzaSyCn7sXQ-7jFww0vK_pndKEMLEsJfMxAsmk",
    // Add other bootstrap parameters as needed, using camel case.
    // Use the 'v' parameter to indicate the version to load (alpha, beta, weekly, etc.)
});

async function initMap(){
  const position = { lat:Number(rentalHome.lat) , lng:Number(rentalHome.lon) };

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

// 등록 최종 확인 및 안내메시지
document.querySelector("#saveButton").onclick= () => {
 Swal.fire({
    text: "숙소 등록에 필요한 심사가 영업일 기준 7~14일 소요될 수 있습니다.",
    showCancelButton: true,
    width: 600,
    confirmButtonText: "등록",
    confirmButtonColor: "#35C5B3",
    cancelButtonText: "취소",
    cancelButtonColor: "#aaa"

  }).then((result) => {
    if (result.isConfirmed) {
      Swal.fire({
        position: "center",
        icon: "success",
        title: "등록되었습니다.",
        showConfirmButton: false,
        timer: 2000
      }).then((result) => {
        window.location.href = "rentalHomeAdd";
      });
    }
  });
}