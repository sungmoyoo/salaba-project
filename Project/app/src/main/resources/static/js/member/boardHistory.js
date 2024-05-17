$(document).ready(function(){
  sideMenuActiveBoardHistory();
  activePageNav();
});

// SideMenu Active
function sideMenuActiveBoardHistory(){
  $('#sideMenu-nav a.active').removeClass('active');
  const sideMenu = $('#sideMenu-WriteHistory-Board');
  $('#sideMenu-collapse-writeHistory').collapse('show');
  sideMenu.addClass('active');
}

// 페이지네이션 액티브
function activePageNav(){
  let pageNumber = pageNo;
  $('.pagination').find('a').each(function(){
      if($(this).text() === pageNumber.toString()){
          $(this).addClass('active');
      }
  });
}