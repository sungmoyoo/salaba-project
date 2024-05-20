(g=>{var h,a,k,p="The Google Maps JavaScript API",c="google",l="importLibrary",q="__ib__",m=document,b=window;b=b[c]||(b[c]={});var d=b.maps||(b.maps={}),r=new Set,e=new URLSearchParams,u=()=>h||(h=new Promise(async(f,n)=>{await (a=m.createElement("script"));e.set("libraries",[...r]+"");for(k in g)e.set(k.replace(/[A-Z]/g,t=>"_"+t[0].toLowerCase()),g[k]);e.set("callback",c+".maps."+q);a.src=`https://maps.${c}apis.com/maps/api/js?`+e;d[q]=f;a.onerror=()=>h=n(Error(p+" could not load."));a.nonce=m.querySelector("script[nonce]")?.nonce||"";m.head.append(a)}));d[l]?console.warn(p+" only loads once. Ignoring:",g):d[l]=(f,...n)=>r.add(f)&&u().then(()=>d[l](f,...n))})({
  key: "AIzaSyCn7sXQ-7jFww0vK_pndKEMLEsJfMxAsmk",
  // Add other bootstrap parameters as needed, using camel case.
  // Use the 'v' parameter to indicate the version to load (alpha, beta, weekly, etc.)
});

let filesArray = []; // 이미지 배열
let startDate;
let endDate;
let formData = new FormData(); // 폼데이터

function checkRequiredFields() {
// 필수 입력 필드 목록
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

// 필수 입력 필드가 비어 있는지 확인
let isEmpty = requiredFields.some(field => !field.value.trim());

let isDescriptionEmpty = filesArray.some(file => !file.description.value.trim());

// 다음 버튼 가져오기
const nextButton = document.querySelector('#nextButton');
nextButton.onclick = () => {
  submitForm();
}


// 필수 입력 필드가 비어 있으면 다음 버튼을 비활성화
if (isEmpty || filesArray.length < 5 || isDescriptionEmpty) {
  nextButton.disabled = true;
  nextButton.classList.add('disabled');
  nextButton.classList.remove('enabled');
} else {
  nextButton.disabled = false;
  nextButton.classList.add('enabled');
  nextButton.classList.remove('disabled');
}
}

// 페이지 로드 시, 입력 필드가 변경될 때마다 확인
window.onload = function() {
  // 입력 필드가 변경될 때마다 확인
  document.querySelectorAll('input, textarea').forEach(element => {
    element.addEventListener('input', checkRequiredFields);
  });


  // 초기에도 확인
  checkRequiredFields();
};

function submitForm() {
  formData.append('name', document.querySelector('#name').value);
  formData.append('explanation', document.querySelector('#explanation').value);
  formData.append('rentalHomeRule', document.querySelector('#rentalHomeRule').value);

  let basicAddress = document.querySelector('#basicAddress').value;
  let detailAddress = document.querySelector('#detailAddress').value;

  formData.append('address', basicAddress + "." + detailAddress);
  formData.append('lat', document.querySelector('#lat').value);
  formData.append('lon', document.querySelector('#lon').value);

  formData.append('price', document.querySelector('#price').value);
  formData.append('cleanFee', document.querySelector('#cleanFee').value);
  formData.append('hostingStartDate', startDate);
  formData.append('hostingEndDate', endDate);
  updateFormData(); // FormData 업데이트


  fetch('rentalHomeSave', {
    method: 'POST',
    body: formData

  }).then(response => {
    if (!response.ok) {
      throw new Error('Network response was not ok');
    }
    return response.text();

  }).then(data => {
    window.location.href = "themeForm"; // 자동으로 리디렉션

  }).catch(error => {
    console.error('Error:', error);
  });
}

// FormData 업데이트 함수
function updateFormData() {
  filesArray.forEach(obj => {
    formData.append('photos', obj.file);
    formData.append('photoExplanations', obj.description.value);
  });
}

function photoCountAndDisplay(files) {
  let imageContainer = document.querySelector('.image-container');
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
        let index = filesArray.findIndex(obj => obj.file === file);
        if (index > -1) {
          filesArray.splice(index, 1);
          checkRequiredFields();
          wrapper.remove();
        }

        let imageContainer = document.querySelector('.image-container');
        let wrapperCount = document.querySelectorAll('.image-wrapper').length;
        let defaultImage = document.querySelector('#default-image');

        if (wrapperCount === 1) {
          defaultImage.style.display = 'block';
          imageContainer.style.justifyContent = 'center';
        }
      };
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

$('.hostingDate').daterangepicker({
  autoUpdateInput: false,
  locale: {
    cancelLabel: 'Clear'
  }
});

$('.hostingDate').on('apply.daterangepicker', function (ev, picker) {
  startDate = picker.startDate.format('YYYY-MM-DD');
  endDate = picker.endDate.format('YYYY-MM-DD');
  $('#hostingStartDate').val(picker.startDate.format('YYYY년 MM월 DD일'));
  $('#hostingEndDate').val(picker.endDate.format('YYYY년 MM월 DD일'));
  checkRequiredFields();
});

$('.hostingDate').on('cancel.daterangepicker', function (ev, picker) {
  $('#hostingStartDate').val('');
  $('#hostingEndDate').val('');
  checkRequiredFields();
});

let map;
let marker;
let geocoder;
let responseDiv;
let response;
let selectedLocation;
let address;

// google맵 생성
function initMap() {
  map = new google.maps.Map(document.getElementById("map"), {
    zoom: 10,
    center: { lat: 37.5518911, lng: 126.9917937 },
    mapTypeControl: false,
  });
  geocoder = new google.maps.Geocoder();

  // 맵 안쪽에 들어갈 html -- 검색 창 및 버튼
  const inputText = document.createElement("input");

  inputText.type = "text";
  inputText.placeholder = "주소";
  inputText.id = "mapInput";

  inputText.addEventListener('keypress', function (e) {
    if (e.key === 'Enter') {
        geocode({ address: this.value });
      }
  });

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
    lat.value = selectedLocation.lat();
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
      responseDiv.style.display = "block";
      response.innerText = "검색결과가 없습니다.";
    });
}

window.initMap = initMap;