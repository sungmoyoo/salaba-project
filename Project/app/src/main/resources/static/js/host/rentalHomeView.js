let filesArray = []; // 이미지 배열
let startDate;
let endDate;
let formData = new FormData(); // 폼데이터

document.addEventListener('DOMContentLoaded', function() {
  // 서버에서 가져온 DB 값으로 기본값 설정
  let stateElement = document.getElementById('state').value;

  // select 요소를 가져오기
  let selectElement = document.getElementById('stateSelect');

  // DB에서 가져온 값과 일치하는 option을 선택
  for (let i = 0; i < selectElement.options.length; i++) {
    if (selectElement.options[i].value == stateElement) {
      selectElement.options[i].selected = true;
      break;
    }
  }

  // 날짜 초기화
  const startInput = document.getElementById('hostingStartDate');
  startDate = startInput.value;
  startInput.value = formatDate(startInput.value);
  const endInput = document.getElementById('hostingEndDate');
  endDate = endInput.value;
  endInput.value = formatDate(endInput.value);

  const deleteButtons = document.querySelectorAll('.delete-button');
  deleteButtons.forEach(button => {
    button.addEventListener('click', function() {
      const wrapper = button.closest('.image-wrapper');
      if (wrapper) {
          wrapper.remove();
      }
      checkRequiredFields();
      checkPhotoCount();
    });
  });
});

function calc() {
  const price = Number(document.getElementById("price").value);
  const cleanFee = Number(document.getElementById("cleanFee").value);
  document.getElementById("income").value = Math.round((price + cleanFee) * 0.97);
}

window.onload = calc();

function submitForm() {
  formData.append('rentalHomeNo', document.getElementById('rentalHomeNo').value);

  formData.append('name', document.getElementById('name').value);
  formData.append('explanation', document.getElementById('explanation').value);
  formData.append('rentalHomeRule', document.getElementById('rentalHomeRule').value);

  let basicAddress = document.querySelector('#basicAddress').value;
  let detailAddress = document.querySelector('#detailAddress').value;
  formData.append('address', basicAddress + "." + detailAddress);
  formData.append('lat', document.querySelector('#lat').value);
  formData.append('lon', document.querySelector('#lon').value);

  formData.append('price', document.getElementById('price').value);
  formData.append('cleanFee', document.getElementById('cleanFee').value);
  formData.append('hostingStartDate', startDate);
  formData.append('hostingEndDate', endDate);

  formData.append('capacity', document.getElementById('capacity').value);

  formData.append('type', document.getElementById('type').value);

  // 모든 테마 요소들
  let checkedTheme = document.querySelectorAll('.theme-checkbox');

  // 각 체크박스 요소를 확인하여 선택된 값과 동일한 인덱스의 숨겨진 입력 필드들의 값을 form 데이터에 추가합니다.
  checkedTheme.forEach(function(e, index) {
      // 선택되었을 때
      if (e.checked) {
          // 값과 해당 인덱스의 숨겨진 입력 필드들의 값을 form 데이터에 추가합니다.
          formData.append('themeNos', e.value);
          formData.append('themeNames', document.querySelectorAll('.themeNames')[index].value);
      }
  });


  // 선택된 시설 요소들
  let checkedFacility = document.querySelectorAll('.facilityCount');
  let inputFacility = document.querySelectorAll('.facilityCount.input');

  checkedFacility.forEach(function(e, index) {
    // 값이 1 이상일 때(선택 또는 입력되었을 때, 기본 시설은 최소 1개 이상임)
    if (e.checked) {
      formData.append('facilityCount', e.value);
      formData.append('facilityNos', document.querySelectorAll('.facilityNos')[index].value);
      formData.append('facilityNames', document.querySelectorAll('.facilityNames')[index].value);
    }
  });

  inputFacility.forEach(function(e, index) {
    formData.append('facilityCount', e.value);
    formData.append('facilityNames', document.querySelectorAll('.facilityNames')[index].value);
    formData.append('facilityNos', document.querySelectorAll('.facilityNos')[index].value);

    console.log(document.querySelectorAll('.facilityNames')[index].value);
    console.log(document.querySelectorAll('.facilityNos')[index].value);
  });

  updateFormData(); // FormData 업데이트

  fetch('rentalHomeUpdate', {
    method: 'POST',
    body: formData
  })
  .then(response => {
    if (!response.ok) {
      throw new Error('Network response was not ok');
    }
    return response.text();
  })
  .then(data => {
    console.log('Response data:', data);
      window.location.href = data; // 자동으로 리디렉션
  })
  .catch(error => {
    console.error('Error:', error);
  });

}

// FormData 업데이트 함수
function updateFormData() {
  if (filesArray.length > 0) {
    filesArray.forEach(obj => {
      formData.append('photos', obj.file);
      formData.append('photoExplanations', obj.description.value);
    });
  }
  let uploadedImage = document.querySelectorAll('.uploaded-image');

  uploadedImage.forEach(image => {
    formData.append('existPhotoName', image.getAttribute('data-uuidPhotoName'));
  });
}
let uploadedImage = document.querySelectorAll('.uploaded-image');

uploadedImage.forEach(image => {
    console.log(image.getAttribute('data-uuidPhotoName'));
  });

function photoCountAndDisplay(files) {
  let imageContainer = document.getElementById('image-container');
  let defaultImage = document.querySelector('#default-image');

  defaultImage.style.display = 'none';
  imageContainer.style.justifyContent = 'flex-start';

  for (let i = 0; i < files.length; i++) {
    let file = files[i];
    let reader = new FileReader();

    reader.onload = function (e) {
      let wrapper = document.createElement('div');

      wrapper.classList.add('image-wrapper');

      let imgElement = document.createElement('img');
      imgElement.classList.add('image-item');
      imgElement.src = e.target.result;
      wrapper.appendChild(imgElement);

      let descriptionInput = document.createElement('input');
      descriptionInput.type = 'text';
      descriptionInput.placeholder = '이미지 설명';
      descriptionInput.classList.add('image-description');
      descriptionInput.name = 'photoExplanations'; // 이미지 설명의 이름 설정
      wrapper.appendChild(descriptionInput);

      let deleteButton = document.createElement('button');
      deleteButton.textContent = 'X';
      deleteButton.classList.add('delete-button');
      deleteButton.onclick = function () {
        checkPhotoCount();
      };
      console.log("dd");
      wrapper.appendChild(deleteButton);

      imageContainer.appendChild(wrapper);

      // 파일과 설명을 하나의 객체로 연결하여 배열에 추가
      filesArray.push({
        file: file,
        description: descriptionInput
      });
      descriptionInput.addEventListener('input', checkRequiredFields);
    checkRequiredFields();
    };

    reader.readAsDataURL(file);
  }
}

document.addEventListener('DOMContentLoaded', function() {


  const typeOptions = document.querySelectorAll('.type-option');
  const themeOptions = document.querySelectorAll('.theme-option');
  const facilityOptions = document.querySelectorAll('.facility-option');

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
          checkRequiredFields();
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
          checkRequiredFields();
      });
  });

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
          checkRequiredFields();
      });
  });

  const plusButtons = document.querySelectorAll('.fa-circle-plus');
  const minusButtons = document.querySelectorAll('.fa-circle-minus');

  plusButtons.forEach(button => {
      button.addEventListener('click', function() {
          const input = this.parentNode.querySelector('input[type="number"]');
          input.value = parseInt(input.value) + 1;
      });
  });

  minusButtons.forEach(button => {
      button.addEventListener('click', function() {
          const input = this.parentNode.querySelector('input[type="number"]');
          if (parseInt(input.value) > 0) {
              input.value = parseInt(input.value) - 1;
          }
      });
  });

  // 초기화 시 선택된 라디오 및 체크박스에 selected 클래스 추가
  document.querySelectorAll('input[type="radio"]:checked').forEach(radio => {
      radio.parentNode.classList.add('selected');
  });

  document.querySelectorAll('input[type="checkbox"]:checked').forEach(checkbox => {
      checkbox.parentNode.classList.add('selected');
  });

  // 등록 최종 확인 및 안내메시지
  document.querySelector("#saveButton").onclick = () => {
    Swal.fire({
      title: "저장하시겠습니까?",
      showCancelButton: true,
      width: 600,
      confirmButtonText: "저장",
      confirmButtonColor: "#35C5B3",
      cancelButtonText: "취소",
      cancelButtonColor: "#aaa"

    }).then((result) => {
      /* Read more about isConfirmed, isDenied below */
      if (result.isConfirmed) {
        Swal.fire({
          position: "center",
          icon: "success",
          title: "변경사항이 저장되었습니다.",
          showConfirmButton: false,
          timer: 2000
        }).then((result) => {
          submitForm();
        });
      }
    });
  }
});

function checkRequiredFields() {
  // 기본 정보 검사
  let requiredFields = [
    document.getElementById('name'),
    document.getElementById('explanation'),
    document.getElementById('rentalHomeRule'),
    document.getElementById('basicAddress'),
    document.getElementById('detailAddress'),
    document.getElementById('price'),
    document.getElementById('cleanFee'),
    document.getElementById('hostingStartDate'),
    document.getElementById('hostingEndDate')
  ];
  let roomFacility = document.querySelectorAll(".roomFacility");

  let isEmpty = requiredFields.some(field => field && !field.value.trim());
  let isRoomEmpty = Array.prototype.slice.call(roomFacility).some(facility => Number(facility.value) <= 0);
  let imageCount = document.querySelectorAll('.image-wrapper');
  let radioChecked = document.querySelector('input[type="radio"]:checked');
  let roomCheckboxes = document.querySelectorAll('.facilityCount.checkbox:checked');
  let themeCheckboxes = document.querySelectorAll('.theme-checkbox:checked');

  let saveButton = document.querySelector('#saveButton');

  if (isEmpty || imageCount.length < 5 || isRoomEmpty || !radioChecked || roomCheckboxes.length == 0 || themeCheckboxes.length == 0) {
    saveButton.disabled = true;
    saveButton.classList.add('disabled');
    saveButton.classList.remove('enabled');
  } else {
    saveButton.disabled = false;
    saveButton.classList.add('enabled');
    saveButton.classList.remove('disabled');
  }
}

// date range picker
$('#hostingEndDate').daterangepicker({
    singleDatePicker: true,
    autoUpdateInput: false,
    locale: {
      format: 'YYYY년 MM월 DD일',
      cancelLabel: 'Clear'
    }
  });

  $('#hostingEndDate').on('apply.daterangepicker', function(ev, picker) {
    endDate = picker.startDate.format('YYYY-MM-DD');
    $(this).val(picker.startDate.format('YYYY년 MM월 DD일'));
    $(this).css('background-color', '#35C5B3');
    $(this).css('color', 'white');
    checkRequiredFields();
  });

  $('#hostingEndDate').on('cancel.daterangepicker', function(ev, picker) {
    $(this).val('');
    checkRequiredFields();
  });

// 날짜 변환 함수
function formatDate(dateString) {
  const date = new Date(dateString);
  const year = date.getFullYear();
  const month = ('0' + (date.getMonth() + 1)).slice(-2); // 월은 0부터 시작하므로 1을 더해줌
  const day = ('0' + date.getDate()).slice(-2);
  return `${year}년 ${month}월 ${day}일`;
}




// 변경 확인
window.onload = function() {

  document.querySelectorAll('input').forEach(element => {
    element.addEventListener('change', checkRequiredFields);
  });

  document.querySelectorAll('input[type="checkbox"]').forEach(element => {
    element.addEventListener('change', checkRequiredFields);
  });

  document.querySelectorAll('input[type="radio"]').forEach(element => {
    element.addEventListener('change', checkRequiredFields);
  });

  checkRequiredFields();
};

let map;
let marker;
let geocoder;
let responseDiv;
let response;
let selectedLocation;
let address;

// google맵 생성
function initMap() {
  const position = { lat:Number(rentalHome.lat) , lng:Number(rentalHome.lon) };
  map = new google.maps.Map(document.getElementById("map"), {
    zoom: 10,
    center: position,
    mapTypeControl: false,
  });
  geocoder = new google.maps.Geocoder();

  // 맵 안쪽에 들어갈 html -- 검색 창 및 버튼
  const inputText = document.createElement("input");

  inputText.type = "text";
  inputText.placeholder = "주소 검색";
  const submitButton = document.createElement("input");

  submitButton.type = "button";
  submitButton.value = "검색";
  submitButton.classList.add("button", "button-primary");

  const clearButton = document.createElement("input");

  clearButton.type = "button";
  clearButton.value = "초기화";
  clearButton.classList.add("button", "button-secondary");

  const selectButton = document.createElement("input");

  selectButton.type = "button";
  selectButton.value = "선택";
  selectButton.classList.add("button", "button-primary");
  selectButton.id = "select-button";

  response = document.createElement("pre");
  response.id = "response";
  response.innerText = "";
  responseDiv = document.createElement("div");
  responseDiv.id = "response-container";
  responseDiv.appendChild(response);


  map.controls[google.maps.ControlPosition.TOP_LEFT].push(inputText); // 주소 입력 박스
  map.controls[google.maps.ControlPosition.TOP_LEFT].push(submitButton); // 주소 가져오기 버튼
  map.controls[google.maps.ControlPosition.TOP_LEFT].push(clearButton); // 초기화 버튼
  map.controls[google.maps.ControlPosition.LEFT_TOP].push(responseDiv); // 주소 변환 결과값 출력 공간
  map.controls[google.maps.ControlPosition.RIGHT_TOP].push(selectButton);
  marker = new google.maps.Marker({
    map,
  });

  // 기존 주소값 넣기
  let basicAddressDiv = document.getElementById('basicAddress');
  let detailAddressDiv = document.getElementById('detailAddress');
  let latDiv = document.getElementById('lat');
  let lonDiv = document.getElementById('lon');

  address = rentalHome.address
  addressArr = address.split(".");

  basicAddressDiv.value = addressArr[0];
  detailAddressDiv.value = addressArr[1];
  latDiv.value = rentalHome.lat;
  lonDiv.value = rentalHome.lon;


  // 기존 주소 마커 표시
  geocode({ address: basicAddressDiv.value })

  map.addListener("click", (e) => {
   geocode({ location: e.latLng });
  });

  submitButton.addEventListener("click", () =>
   geocode({ address: inputText.value })
  );

  clearButton.addEventListener("click", () => {
    clear();
  });

  selectButton.addEventListener("click", () => {
    let addressInput =  document.getElementById('basicAddress');
    let detailAddressInput = document.getElementById('detailAddress');
    let lat = document.getElementById("lat");
    let lon = document.getElementById('lon');

    addressInput.value = address;
    detailAddressInput.value = null;
    lat.value = JSON.stringify(selectedLocation.lat());
    lon.value = JSON.stringify(selectedLocation.lng());
  });

  clear();
}

// 초기화
function clear() {
  marker.setMap(null);
  responseDiv.style.display = "none";
}

// 주소 변환
function geocode(request) {
  clear();
  geocoder
    .geocode(request)
    .then((result) => {
      const { results } = result;

      map.setCenter(results[0].geometry.location);
      marker.setPosition(results[0].geometry.location);
      marker.setMap(map);
      responseDiv.style.display = "block";

      address = results[0].formatted_address;
      selectedLocation = results[0].geometry.location;

      response.innerText = JSON.stringify(results[0].formatted_address, null, 2);
      return results;
    })
    .catch((e) => {
      alert("주소 변환 실패: " + e);
    });
}
function checkPhotoCount() {
  let index = filesArray.findIndex(obj => obj.file === file);
  if (index > -1) {
    filesArray.splice(index, 1);
    wrapper.remove();
  }

  let imageContainer = document.querySelector('.image-container');
  let wrapperCount = document.querySelectorAll('.image-wrapper').length;
  let defaultImage = document.querySelector('#default-image');

  if (wrapperCount === 1) {
    defaultImage.style.display = 'block';
    imageContainer.style.justifyContent = 'center';
    console.log(":hhj");
  }
}
window.initMap = initMap;


