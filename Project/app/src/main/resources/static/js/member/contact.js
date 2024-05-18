$(document).ready(function(){
  sideMenuActiveContact();
  showUploadFileList();
  checkInput();
});


// SideMenu Active
function sideMenuActiveContact(){
  $('#sideMenu-nav a.active').removeClass('active');
  const sideMenu = $('#sideMenu-contact');
  $('#sideMenu-collapse-HelpCenter').collapse('show');
  sideMenu.addClass('active');
}

function showUploadFileList(){
  $('#contact-files').on('change', function() {
    let fileList = $('#file-list');
    fileList.empty(); // 이전 파일 목록을 지웁니다.

    let files = this.files;

    if (files.length > 0) {
      let ul = $('<ul></ul>');
      for (var i = 0; i < files.length; i++) {
        let li = $('<li></li>').text(files[i].name);
        ul.append(li);
      }
      fileList.append(ul);
    }
  });
}

// 입력값 공백 방지
function checkInput(){
  $('#contact-title').on('blur', function(event){
    let inputTitle = $('#contact-title');
    if( inputTitle.val() == "" ){
      inputTitle.removeClass('is-valid');
      inputTitle.addClass('is-invalid');
    }else{
      inputTitle.removeClass('is-invalid');
      inputTitle.addClass('is-valid');
    }
    checkValidInput()
  });

  $('#contact-content').on('blur',function(event){
    let inputContent = $('#contact-content');
    if( inputContent.val() == "" ){
      inputContent.removeClass('is-valid');
      inputContent.addClass('is-invalid');
    }else{
      inputContent.removeClass('is-invalid');
      inputContent.addClass('is-valid');
    }
    checkValidInput()
  });
}

// 버튼 활성화/비활성화
function checkValidInput(){
  let isTitleValid = $('#contact-title').hasClass('is-valid');
  let isContentValid = $('#contact-content').hasClass('is-valid');

  if( isTitleValid && isContentValid ){
    $('#submit-button').prop('disabled', false);
  }else{
    $('#submit-button').prop('disabled', true);
  }
}