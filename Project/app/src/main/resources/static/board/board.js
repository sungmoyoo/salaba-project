$(document).ready(function() {
  $('#summernote').summernote({
    height: 800,
    width: 1000,
    callbacks: {
      onInit: function() {
        console.log('Summernote 생성됨!');
      },
      onImageUpload: function(files) {

        let data = new FormData();
        for (const file of files) {
          data.append("files", file);
        }
        let categoryNo = $("#categoryNo").val();

        $.ajax({
          url: 'file/upload?categoryNo=' + categoryNo,
          type: 'POST',
          dataType: 'json',
          contentType: false,
          processData: false,
          data: data
        })
        .done(function(result) {
          if (result.length == 0) {
            alert('로그인 하세요!');
            return;
          }

          // 카테고리 번호에 따라 다른 경로를 설정
          let basePath = 'https://kr.object.ncloudstorage.com/tp3-salaba/board/';
          if (categoryNo == '0') {
            basePath += 'review/';
          } else if (categoryNo != '0') {
            basePath += 'community/';
          }

          for (const boardFile of result) {
            $('#summernote').summernote('insertImage', basePath + boardFile.uuidFileName);
          }
        });
      }
    }
  });
});


// 게시글 공개범위 설정
  function setScopeNo(input) {
    // 선택된 라디오 버튼의 값을 scopeNo에 설정
    document.getElementById("scopeNo").value = input.dataset.thValue;
  }

  // 이미지 슬라이더를 초기화하는 함수
  function initSlider() {
    const sliders = document.querySelectorAll('.slider');
    sliders.forEach(slider => {
      const slides = slider.querySelector('.slides');
      const slideCount = slides.children.length;
      let index = 0;

      // 다음 슬라이드로 이동하는 함수
      function nextSlide() {
        index = (index + 1) % slideCount;
        slides.style.transform = `translateX(-${index * 100}%)`;
      }

      // 이전 슬라이드로 이동하는 함수
      function prevSlide() {
        index = (index - 1 + slideCount) % slideCount;
        slides.style.transform = `translateX(-${index * 100}%)`;
      }

      // 자동으로 슬라이드를 전환하는 타이머 설정
      setInterval(nextSlide, 5000); // 5초마다 다음 슬라이드로 전환
    });
  }

  // 페이지 로드 후 이미지 슬라이더 초기화
  window.onload = function() {
    initSlider();
  };

  //이미지 슬라이더 처리
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


// 게시글 신고 버튼
  $('#board-report-btn').click(function() {
    var title = '신고하기';
    var width = 800;
    var height = 600;
    let targetNo = document.getElementById("noInput").getAttribute("value");
    console.log(targetNo);
    openPopup('report/form?targetType=0&targetNo=' + targetNo, title, width, height);
  });

// 댓글 신고 버튼
  $('#comment-report-btn').click(function() {
    var title = '신고하기';
    var width = 800;
    var height = 600;
    let targetNo = document.getElementById("noInput").getAttribute("value");
    console.log(targetNo);
    openPopup('report/form?targetType=0&targetNo=' + targetNo, title, width, height);
  });

// 답글 신고 버튼
    $('#reply-report-btn').click(function() {
    var title = '신고하기';
    var width = 800;
    var height = 600;
    let targetNo = document.getElementById("noInput").getAttribute("value");
    console.log(targetNo);
    openPopup('report/form?targetType=0&targetNo=' + targetNo, title, width, height);
  });

// 추천 수
$(document).on('click', '#likeButton', function() {
    console.log("aaaaaa");
    let boardNo = $("#boardNo").val();
    let myLikeCount = parseInt($("#myLikeCount").text());
    console.log(boardNo);
    if (myLikeCount === 0) {
        $.ajax({
            type: 'POST',
            url: '/board/like',
            data: { boardNo: boardNo },
            success: function(response) {
                if (response === "success") {
                    console.log("success like")
                    alert('추천하였습니다.');
                    $("#myLikeCount").text('1'); // 추천++
                    $('#likeButton').addClass('active'); // 꽉 찬 하트로 변경
                }
            },
        });
    } else {
        $.ajax({
            type: 'POST',
            url: '/board/unlike',
            data: { boardNo: boardNo },
            success: function(response) {
                if (response === "success") {
                    console.log("success like")
                    alert('추천 취소');
                    $("#myLikeCount").text('0'); // 추천 취소
                    $('#likeButton').removeClass('active'); // 빈 하트로 변경
                }
            },
        });
    }
});



// 팝업을 열기 위한 함수
function openPopup(url, title, width, height) {
  var left = (screen.width - width) / 2;
  var top = (screen.height - height) / 2;
  var options = 'toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=no, copyhistory=no, width=' + width + ', height=' + height + ', top=' + top + ', left=' + left;
  var popup = window.open(url, title, options);
  return popup;
}
