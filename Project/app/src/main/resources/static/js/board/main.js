// 썸네일
  function initSlider() {
  const sliders = document.querySelectorAll('.slider');
  sliders.forEach(slider => {
    const slides = slider.querySelector('.slides');
    const slideCount = slides.children.length;
    let index = 0;

    // 다음 슬라이드로 넘기기
    function nextSlide() {
      index = (index + 1) % slideCount;
      slides.style.transform = `translateX(-${index * 100}%)`;
    }

    // 5초마다 자동으로 슬라이드 넘기기
    setInterval(nextSlide, 5000);
  });
}

window.onload = function() {
  initSlider();
};


// 슬라이드 화면 (코드 통일성을 위해 바닐라 js로 변경함)
document.addEventListener('DOMContentLoaded', function() {
  const sliders = document.querySelectorAll('.slider');

  sliders.forEach(function(slider) {
    const slides = slider.querySelector('.slides');
    const slideCount = slides.children.length;
    let index = 0;

    setInterval(function() {
      index = (index + 1) % slideCount;
      slides.style.transform = `translateX(-${index * 100}%)`;
    }, 5000);
  });
});


// 후기 게시판 목록
// 카드 컨테이너에 대한 클릭 이벤트 리스너
document.querySelectorAll(".card-deck .card").forEach(card => {
  card.addEventListener("click", function(event) {
    // 이벤트가 발생한 위치에서 가장 가까운 a 태그를 찾기
    const link = card.querySelector("a");

    // a 태그가 있고, 이벤트가 a 태그 자체에서 발생하지 않았을 경우에만 동작
    if (link && event.target !== link) {
        event.preventDefault(); // 기본 이벤트 중지
        window.location.href = link.href; // 해당 링크로 페이지 이동
    }
  });
});

// 정보공유, 자유 게시판 목록
// 모든 행을 선택하여 클릭 이벤트를 추가
    document.querySelectorAll("table").forEach(table => {
      table.addEventListener("click", function(event) {
        let target = event.target;

        // 클릭된 요소에서 가장 가까운 tr 요소를 찾기
        while (target && target.nodeName !== "TR") {
          target = target.parentNode;
        }

        // tr 요소 내부에 있는 a 태그를 찾기
        if (target) {
          const link = target.querySelector("a");
          if (link && link.href) {
            event.preventDefault(); // 기본 이벤트 중지
            window.location.href = link.href; // 해당 링크로 페이지 이동
          }
        }
      });
    });

/*게시판 버튼 색상*/
function setActiveAndNavigate(selectedId, url) {
  // 모든 버튼의 스타일을 초기화하고 로컬 스토리지 저장된 선택된 ID 제거
  clearButtonStyles();

  // 선택된 버튼의 스타일 설정
  const selectedButton = document.getElementById(selectedId);
  selectedButton.classList.add('active');

  // 선택된 버튼의 ID를 로컬 스토리지에 저장
  localStorage.setItem('selectedButtonId', selectedId);

  // 페이지 이동
  location.href = url;
}

function clearButtonStyles() {
  const buttons = document.querySelectorAll('#board-header button');
  buttons.forEach(button => {
      button.classList.remove('active');
  });
}

// 게시판: 공간적 여유를 위해 제목에 말줄임표 표시
  document.addEventListener("DOMContentLoaded", function() {
    let titles = document.querySelectorAll('.board-title');
    titles.forEach(function(title) {
      let maxLength = 15; // 제목의 최대 길이 설정
      console.log(title.textContent.length);
      if (title.textContent.length > maxLength) {

        title.textContent = title.textContent.slice(0, maxLength) + '...';
        console.log(title.textContent);

      }
    });
  });

