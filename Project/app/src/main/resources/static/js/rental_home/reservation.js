(g=>{var h,a,k,p="The Google Maps JavaScript API",c="google",l="importLibrary",q="__ib__",m=document,b=window;b=b[c]||(b[c]={});var d=b.maps||(b.maps={}),r=new Set,e=new URLSearchParams,u=()=>h||(h=new Promise(async(f,n)=>{await (a=m.createElement("script"));e.set("libraries",[...r]+"");for(k in g)e.set(k.replace(/[A-Z]/g,t=>"_"+t[0].toLowerCase()),g[k]);e.set("callback",c+".maps."+q);a.src=`https://maps.${c}apis.com/maps/api/js?`+e;d[q]=f;a.onerror=()=>h=n(Error(p+" could not load."));a.nonce=m.querySelector("script[nonce]")?.nonce||"";m.head.append(a)}));d[l]?console.warn(p+" only loads once. Ignoring:",g):d[l]=(f,...n)=>r.add(f)&&u().then(()=>d[l](f,...n))})({
    key: "AIzaSyCn7sXQ-7jFww0vK_pndKEMLEsJfMxAsmk",
    // Add other bootstrap parameters as needed, using camel case.
    // Use the 'v' parameter to indicate the version to load (alpha, beta, weekly, etc.)
  });

// google map
async function initMap(){
  const rentalHome = reservationInform;
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

const checkInDate = checkIn;
const checkOutDate = checkOut;
const reservationInfo = reservationInform;
const guests = guest;
const memberNo = loginUser.no;
console.log("**********");
console.log(checkInDate);
console.log(checkOutDate);
console.log(reservationInform);

// 날짜 문자열 변환(yyyy-MM-dd)
function formatDate(input){
  let dateArray = input.replace(/년|월|일/g, '').split(' ');
  let year = dateArray[0];
  let month = dateArray[1].padStart(2,'0'); // 한자리 숫자의 경우 앞에 0을 붙여줌
  let day = dateArray[2].padStart(2, '0');  // 한자리 숫자의 경우 앞에 0을 붙여줌
  return year + "-" + month + "-" + day;
}

$("#reservationButton").click(function(){
  let reservation = {
    memberNo:memberNo,
    rentalHomeNo:reservationInfo.rentalHomeNo,
    startDate:formatDate(checkInDate),
    endDate:formatDate(checkOutDate),
    numberOfPeople:guests
  }
  console.log(reservation);
  $.ajax({
    url: "/rentalHome/reservation",
    type: "POST",
    contentType: "application/json",
    data: JSON.stringify(reservation),
    success: function(data){
      alert(data);
    },
    error: function(){
      alert("예약 신청 오류");
    }
  });
});
