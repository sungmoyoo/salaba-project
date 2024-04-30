document.addEventListener('DOMContentLoaded', function(){
  const userInfoButton = document.querySelector('.userInfo-button');
  const userInfoMenu = document.querySelector('.userInfo-menu');

  userInfoButton.addEventListener('click', function() {
    userInfoMenu.classList.toggle('show');
  });
});