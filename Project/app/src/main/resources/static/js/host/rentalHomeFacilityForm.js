const nextButton = document.getElementById('nextButton');
// + - 버튼 상호작용
document.addEventListener('DOMContentLoaded', function() {
  const plusButtons = document.querySelectorAll('.fa-circle-plus');
  const minusButtons = document.querySelectorAll('.fa-circle-minus');

  plusButtons.forEach(button => {
    button.addEventListener('click', function() {
      const input = this.parentNode.querySelector('input[type="number"]');
      input.value = parseInt(input.value) + 1;
      checkRequiredFields(); // 필수 입력 필드 확인
    });
  });

  minusButtons.forEach(button => {
    button.addEventListener('click', function() {
      const input = this.parentNode.querySelector('input[type="number"]');
      if (parseInt(input.value) > 0) {
        input.value = parseInt(input.value) - 1;
        checkRequiredFields(); // 필수 입력 필드 확인
      }
    });
  });
});

const facilityOptions =  document.querySelectorAll('.facility-option');
facilityOptions.forEach(option => {
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

function checkRequiredFields() {
  let roomFacility = Array.prototype.slice.call(document.querySelectorAll(".roomFacility"));
  let checkboxes = document.querySelectorAll('input[type="checkbox"]:checked');

  let isEmpty = roomFacility.some(facility => Number(facility.value) <= 0)

 if (isEmpty || checkboxes.length <= 0) {
    nextButton.disabled = true;
    nextButton.classList.add('disabled');
    nextButton.classList.remove('enabled');
  } else {
    nextButton.disabled = false;
    nextButton.classList.add('enabled');
    nextButton.classList.remove('disabled');
  }
}

nextButton.onclick = () => {
  let checkboxes = document.querySelectorAll('.checkboxes');
      checkboxes.forEach(function(checkbox) {
          let hiddenFacilityName = checkbox.nextElementSibling;
          let hiddenFacilityNo = hiddenFacilityName.nextElementSibling;

          if (!checkbox.checked) {
              hiddenFacilityName.remove();
              hiddenFacilityNo.remove();
          }
      });
}


// 페이지 로드 시, 입력 필드가 변경될 때마다 확인
window.onload = function() {
  // 입력 필드가 변경될 때마다 확인
  document.querySelectorAll('input').forEach(element => {
    element.addEventListener('change', checkRequiredFields);
  });

  document.querySelectorAll('input[type="checkbox"]').forEach(element => {
    element.addEventListener('change', checkRequiredFields);
  });

  checkRequiredFields();
};