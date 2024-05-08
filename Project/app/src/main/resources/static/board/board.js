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



// 팝업을 열기 위한 함수
function openPopup(url, title, width, height) {
  var left = (screen.width - width) / 2;
  var top = (screen.height - height) / 2;
  var options = 'toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=no, copyhistory=no, width=' + width + ', height=' + height + ', top=' + top + ', left=' + left;
  var popup = window.open(url, title, options);
  return popup;
}
<<<<<<< HEAD

// 공지글 색상처
.announcement {
  color: red;
}

=======
>>>>>>> 3667633f51a6773f80c4a3d1d3509f275563aeae
