// const userInfoMenuButton = document.querySelector('.userInfo-button');
// const userInfoMenuContainer = document.querySelector('.userInfo-menu');

// let isMenuOpen = false;

// // 메뉴 버튼
// userInfoMenuButton.addEventListener('click', function (event) {
//   if (isMenuOpen) {
//     closeUserInfoMenu();
//   } else {
//     openUserInfoMenu();
//   }

//   // 현재 메뉴 상태 업데이트
//   isMenuOpen = !isMenuOpen;

//   // 이벤트 전파 중단
//   event.stopPropagation();
// });

// // userInfoMenu 열기
// function openUserInfoMenu() {
//   userInfoMenuContainer.style.display = 'block';
// }

// // userInfoMenu 닫기
// function closeUserInfoMenu() {
//   userInfoMenuContainer.style.display = 'none';
// }

// document.addEventListener('click', function (event) {
//   // 메뉴가 열려있는지 체크
//   if (isMenuOpen && !userInfoMenuContainer.contains(event.target)) {
//     closeUserInfoMenu(); // 메뉴가 열려있는 경우 닫아줌
//     isMenuOpen = false;
//   }
// });
(function() {
  let mainPageNav = document.getElementById('mainPage');
  let boardPageNav = document.getElementById('boardPage');

  if (window.location.href.includes('board')) {
    boardPageNav.classList.add("active");
    mainPageNav.classList.remove('active');
  } else {
    mainPageNav.classList.add('active');
    boardPageNav.classList.remove("active");
  }
})()
