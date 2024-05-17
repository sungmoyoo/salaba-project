$(document).ready(function(){
  sideMenuActivePointHistory();
});


// SideMenu Active
function sideMenuActivePointHistory(){
  $('#sideMenu-nav a.active').removeClass('active');
  const sideMenu = $('#sideMenu-PointHistory');
  sideMenu.addClass('active');
}
