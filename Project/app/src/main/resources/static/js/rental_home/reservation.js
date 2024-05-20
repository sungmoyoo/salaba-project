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
// const memberNo = loginUser.no;
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


const inicis = 'html5_inicis';
const kakaopay = 'kakaopay';

$("#paymentButton").click(() => requestPay(inicis));
$("#kakaopay").click(() => requestPay(kakaopay));

function requestPay(paymethod) {
  $.ajax({
    url: '/payment/getLoginUser',
    type: 'get',
    success: function(loginUser) {
      var uid = '';
      IMP.init('imp86666655');
      const total = $('#total').text().replace(/\D/g, '');
      const paymentNo = createOrderNum()
      IMP.request_pay({ // param
          pg: paymethod,
          pay_method: "card",
          merchant_uid: paymentNo,
          name: $('#rentalHome-name').find('span').text(), //결제창에 노출될 상품명
          amount: total, //금액
          buyer_email : loginUser.email, 
          buyer_name : loginUser.name,
          buyer_tel : loginUser.telNo,
      }, function (rsp) { // callback
          if (rsp.success) { // 결제 성공 시: 결제 승인 또는 가상계좌 발급에 성공한 경우
              uid = rsp.imp_uid;
              // 결제검증
              $.ajax({
                  url: '/payment/validation/'+ rsp.imp_uid,
                  type: 'post'
              }).done(function(data) {
                  // 결제를 요청했던 금액과 실제 결제된 금액이 같으면 해당 주문건의 결제가 정상적으로 완료된 것으로 간주한다.
                  if (total == data.response.amount) {
                      // jQuery로 HTTP 요청
                      // 주문정보 생성 및 테이블에 저장 
                          // 데이터를 json으로 보내기 위해 바꿔준다.
                          data = JSON.stringify({
                              memberNo: loginUser.no,
                              rentalHomeNo: reservationInfo.rentalHomeNo,
                              startDate: formatDate(checkInDate),
                              endDate: formatDate(checkOutDate),
                              numberOfPeople: guests,
                              payment: {
                                paymentNo: uid,
                                amount: data.response.amount,
                                payMethod: paymethod
                              }
                              
                          });
            
                          $.ajax({
                              url: "/reservation/payment/complete", 
                              type: "POST",
                              dataType: 'json',
                              contentType: 'application/json',
                              data : data,
                              success: function(data) {
                                console.log("success", data);
                                Swal.fire({
                                  icon: "success",
                                  title: "결제가 완료되었습니다.",
                                  showConfirmButton: false,
                                  timer: 1000
                                });
                              },
                              error: function(error) {
                                console.log("error", error);
                                Swal.fire({
                                  icon: "error",
                                  title: "결제 실패.",
                                  showConfirmButton: false,
                                  timer: 1000
                                });
                              }
                          });
                    
                  }
                  else {
                    Swal.fire({
                      icon: "error",
                      title: "결제에 실패하였습니다.",
                      showConfirmButton: false,
                      timer: 1500
                    });
                  }
              })
              } else {
                Swal.fire({
                  icon: "error",
                  title: "결제에 실패하였습니다.",
                  text: rsp.error_msg,
                  showConfirmButton: false,
                  timer: 1500
                });
              }
          });
    },
    error: function() {
      Swal.fire({
        icon: "error",
        title: "로그인이 필요합니다.",
        showConfirmButton: false,
        timer: 1500
      });
      return;
    }
  });


}

function createOrderNum(){
	const date = new Date();
	const year = date.getFullYear();
	const month = String(date.getMonth() + 1).padStart(2, "0");
	const day = String(date.getDate()).padStart(2, "0");
	
	let orderNum = year + month + day;
	for(let i=0;i<10;i++) {
		orderNum += Math.floor(Math.random() * 8);	
	}
	return orderNum;
}
