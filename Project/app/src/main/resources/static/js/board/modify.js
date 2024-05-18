  //기존글의 공개범위 표시
  let scopeNo = $('#scope').find('span').text();
  $('#scope').find('input').each(function() {
    if ($(this).val() == scopeNo) {
      $(this).prop('checked', true);
    }
  });

  // 기존글의 지역 표시
  let regionNo = $('#region').find('span').text();
  let regionOptions = $('#region').find('option');
  regionOptions.each(function() {
    if ($(this).val() == regionNo) {
      $(this).prop('selected', true);
    }
  })

  //기존글의 말머리 표시
  let headNo = $('#head').find('span').text();
  let headOptions = $('#head').find('option');
  console.log(headNo);
  headOptions.each(function() {
    if ($(this).val() == headNo) {
      $(this).prop('selected', true);
      console.log("equal")
    }
  })