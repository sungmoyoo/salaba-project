document.addEventListener('DOMContentLoaded', function() {
  const typeOptions = document.querySelectorAll('.type-option');
  const themeOptions =  document.querySelectorAll('.theme-option');

  typeOptions.forEach(option => {
    option.addEventListener('click', function() {
      // 선택된 항목 해제
      typeOptions.forEach(otherOption => {
        otherOption.classList.remove('selected');
        otherOption.querySelector('input[type="radio"]').checked = false;
      });
      // 현재 항목 선택
      this.classList.add('selected');
      this.querySelector('input[type="radio"]').checked = true;
      checkRequiredFields(); // 필수 입력 필드 확인
    });
  });

  themeOptions.forEach(option => {
    option.addEventListener('click', function() {
      const checkbox = this.querySelector('input[type="checkbox"]');
      checkbox.checked = !checkbox.checked; // 클릭할 때마다 체크 여부를 반전시킴
      // 선택 클래스를 토글
      if (checkbox.checked) {
        this.classList.add('selected');
      } else {
        this.classList.remove('selected');
      }
      checkRequiredFields(); // 필수 입력 필드 확인
    });
  });


  const nextButton = document.getElementById('nextButton');

  function checkRequiredFields() {
    const radioChecked = document.querySelector('input[type="radio"]:checked');
    const checkboxes = document.querySelectorAll('input[type="checkbox"]:checked');

    if (radioChecked && checkboxes.length > 0) {
      nextButton.disabled = false;
      nextButton.classList.add('enabled');
      nextButton.classList.remove('disabled');
    } else {
      nextButton.disabled = true;
      nextButton.classList.add('disabled');
      nextButton.classList.remove('enabled');
    }
  }

  // 입력 필드가 변경될 때마다 확인
  document.querySelectorAll('input[type="radio"]').forEach(element => {
    element.addEventListener('change', checkRequiredFields);
  });

  document.querySelectorAll('input[type="checkbox"]').forEach(element => {
    element.addEventListener('change', checkRequiredFields);
  });

  checkRequiredFields(); // 페이지 로드 시 확인
});