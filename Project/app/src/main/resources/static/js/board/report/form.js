// 신고 폼
  $(document).ready(function() {
    $('#submitBtn').click(function(event) {
      event.preventDefault(); // 기본 폼 제출 동작 방지
      var formData = new FormData($('#reportForm')[0]); // 폼 데이터 수집

      $.ajax({
        url: $('#reportForm').attr('action'), // 폼의 action 속성 사용
        type: 'POST',
        data: formData,
        processData: false,  // 데이터를 쿼리 문자열로 변환하지 않음
        contentType: false,  // contentType을 false로 설정하여 데이터를 multipart/form-data로 전송
        success: function(response) {
          alert('신고 완료');
          window.close(); // 팝업 창 닫기
        },
        error: function() {
          console.log(formData);
          alert('신고 처리에 실패했습니다. 다시 시도해주세요.');
        }
      });
    });
  });