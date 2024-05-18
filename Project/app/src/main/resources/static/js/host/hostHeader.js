(function() {
  let reservationPageNav = document.getElementById('reservationPage');
  let rentalHomePageNav = document.getElementById('rentalHomePage');
  let chatPageNav = document.getElementById('chatPage');
  let incomePageNav = document.getElementById('incomePage');

  if (window.location.href.includes('reservationList')) {
    reservationPageNav.classList.add("active");
    rentalHomePageNav.classList.remove('active');
    chatPageNav.classList.remove('active');
    incomePageNav.classList.remove('active');

  } else if (window.location.href.includes('rentalHomeList') || window.location.href.includes('rentalHomeView')){
    rentalHomePageNav.classList.add("active");
    reservationPageNav.classList.remove('active');
    chatPageNav.classList.remove('active');
    incomePageNav.classList.remove('active');

  } else if (window.location.href.includes('chat')){
    chatPageNav.classList.add("active");
    reservationPageNav.classList.remove('active');
    rentalHomePageNav.classList.remove('active');
    incomePageNav.classList.remove('active');

  } else if (window.location.href.includes('incomeList')){
    incomePageNav.classList.add('active');
    chatPageNav.classList.remove("active");
    reservationPageNav.classList.remove('active');
    rentalHomePageNav.classList.remove('active');
  }
})()

const userInfoMenuButton = document.querySelector('.userInfo-button');
const userInfoMenuContainer = document.querySelector('.userInfo-menu');

let isMenuOpen = false;

// 메뉴 버튼
userInfoMenuButton.addEventListener('click', function(event){
  if(isMenuOpen){
    closeUserInfoMenu();
  }else{
    openUserInfoMenu();
  }

  // 현재 메뉴 상태 업데이트
  isMenuOpen = !isMenuOpen;

  // 이벤트 전파 중단
  event.stopPropagation();
});

// userInfoMenu 열기
function openUserInfoMenu(){
  userInfoMenuContainer.style.display = 'block';
}

// userInfoMenu 닫기
function closeUserInfoMenu(){
  userInfoMenuContainer.style.display = 'none';
}

document.addEventListener('click', function(event){
  // 메뉴가 열려있는지 체크
  if(isMenuOpen && !userInfoMenuContainer.contains(event.target)){
    closeUserInfoMenu(); // 메뉴가 열려있는 경우 닫아줌
    isMenuOpen = false;
  }
});

// logout
function logout(){
  $.ajax({
    type: "POST",
    url: "/auth/logout",
    success: function(){
      location.href = "/main";
    },
    error: ()=>{

    }

  });
}

