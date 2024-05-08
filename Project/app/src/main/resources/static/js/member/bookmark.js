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