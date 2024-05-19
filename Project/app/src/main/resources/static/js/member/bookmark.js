// SideMenu Active
function sideMenuActiveBookMark(){
  $('#sideMenu-nav a.active').removeClass('active');
  const sideMenu = $('#sideMenu-BookMark');
  sideMenu.addClass('active');
}


$(document).ready(function() {
  sideMenuActiveBookMark();
  bookMarkPageSet();
  deleteBookMarkModalOpen();
  deleteBookMark();
});

function bookMarkPageSet(){
  const bookMarkList = bookmarkInfo;
  // bookMarkList에서 각 bookMark를 순회하며 카드 형태의 캐러셀을 생성합니다.
  $.each(bookMarkList, function(bookMarkIndex, bookMark) {
      // 각 bookMark에 대한 카드를 생성합니다.
      let cardHtml = '<div class="card mb-4"><div class="row g-0"><div class="col-md-4">';
      // 캐러셀을 추가합니다.
      cardHtml += '<div class="carousel slide" id="carousel-' + bookMarkIndex + '" data-bs-ride="carousel">';
      cardHtml += '<div class="carousel-indicators">';

      // 캐러셀 인디케이터를 추가합니다.
      $.each(bookMark.rentalHomePhotos, function(photoIndex, photo) {
          let activeClass = (photoIndex === 0) ? 'active' : '';
          cardHtml += '<button type="button" data-bs-target="#carousel-' + bookMarkIndex + '" data-bs-slide-to="' + photoIndex + '" class="' + activeClass + '" aria-current="true" aria-label="Slide ' + (photoIndex + 1) + '"></button>';
      });

      cardHtml += '</div>';
      cardHtml += '<div class="carousel-inner">';

      // 캐러셀 아이템을 추가합니다.
      $.each(bookMark.rentalHomePhotos, function(photoIndex, photo) {
          let activeClass = (photoIndex === 0) ? 'active' : '';
          cardHtml += '<div class="carousel-item ' + activeClass + '">';
          cardHtml += '<img src="https://5ns6sjke2756.edge.naverncp.com/nBMc0TCJiv/rentalHome/' + photo.uuidPhotoName + '?type=f&w=265&h=252&faceopt=false&ttype=jpg" class="d-block w-100" alt="...">';
          cardHtml += '</div>';
      });

      cardHtml += '</div>';
      cardHtml += '<button class="carousel-control-prev" type="button" data-bs-target="#carousel-' + bookMarkIndex + '" data-bs-slide="prev">';
      cardHtml += '<span class="carousel-control-prev-icon" aria-hidden="true"></span><span class="visually-hidden">Previous</span></button>';
      cardHtml += '<button class="carousel-control-next" type="button" data-bs-target="#carousel-' + bookMarkIndex + '" data-bs-slide="next">';
      cardHtml += '<span class="carousel-control-next-icon" aria-hidden="true"></span><span class="visually-hidden">Next</span></button></div></div>';

      // 카드 내용을 추가합니다.
      cardHtml += '<div class="col-md-8"><div class="card-body row">';
      cardHtml += '<div class="col-sm-10"><span class="card-title">'+ bookMark.name +'</span></div>';
      cardHtml += '<div class="col-sm-2" id="link-box"><a id="rentalHome-Link" href="/rentalHome/view?rentalHomeNo='+ bookMark.rentalHomeNo +'"><span>바로가기</span></a></div>';
      cardHtml += '<div id="card-text-box"><span class="card-text" id="card-text-address">'+ bookMark.address +'</span></div>';
      cardHtml += '<div class="col-sm-7" id="card-text-box"><span class="card-text">가격 : ￦'+ bookMark.price +'/주</span></div>';
      cardHtml += '<div class="col-sm-3 align-self-end" id="card-text-like-box"><svg xmlns="http://www.w3.org/2000/svg" height="24px" viewBox="0 -960 960 960" width="24px" fill="#000000"><path d="M720-120H320v-520l280-280 50 50q7 7 11.5 19t4.5 23v14l-44 174h218q32 0 56 24t24 56v80q0 7-1.5 15t-4.5 15L794-168q-9 20-30 34t-44 14ZM240-640v520H80v-520h160Z"/></svg><span>'+ bookMark.rentalHomeLikeCount +'</span></div>'
      cardHtml += '<div class="col-sm-2 align-self-end card-delete-box"><span id="delete-target" hidden>'+ bookMark.rentalHomeNo +'</span>';
      cardHtml += '<svg xmlns="http://www.w3.org/2000/svg" height="28px" viewBox="0 -960 960 960" width="28px" fill="#000000"><path d="M258.33-92q-44.47 0-74.9-30.23Q153-152.47 153-196.67V-731H93.33v-104.67H333v-53h292.67v53h241V-731H807v534.33q0 43.7-30.68 74.19Q745.64-92 701.67-92H258.33Zm443.34-639H258.33v534.33h443.34V-731ZM350-271.67h85.67v-386H350v386Zm174.33 0h86.34v-386h-86.34v386ZM258.33-731v534.33V-731Z"/></svg></div>';
      cardHtml += '</div></div></div>';
      // 페이지에 카드를 추가합니다.
      $('#bookMark-contents-container').append(cardHtml);
  });
}

let deleteBookMarkTarget = "";
function deleteBookMarkModalOpen(){
  $('.card-delete-box').on('click',function(){
    deleteBookMarkTarget = $(this).find('#delete-target').text();
    console.log(deleteBookMarkTarget);
    $('#deleteBookMark-Modal').modal('show');
  });
}

function deleteBookMark(){
  $('#deleteBookMark-button').on('click',function(){
    $.ajax({
      type: "POST",
      url: "/bookmark/delete",
      data:{
        rentalHomeNo: deleteBookMarkTarget
      },
      success:function(data){
        Swal.fire({
          title: data,
          icon: "success",
          confirmButtonText: "확인"
        }).then((result)=>{
          const index = bookmarkInfo.findIndex(function(item){
            return item.rentalHomeNo === deleteBookMarkTarget;
          });
          if(index > -1){
            bookmarkInfo.splice(index, 1);
          }
          location.href = "/member/bookmark";
        });
      },
      error:()=>{
        Swal.fire({
          icon: "error",
          title: "즐겨찾기 삭제 에러"
        });
      }
    });
  });
}