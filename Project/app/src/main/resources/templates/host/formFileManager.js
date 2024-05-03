var filesArray = []; // 이미지 배열
var formData = new FormData(); // 폼데이터

function submitForm() {
  formData.append('name', document.querySelector('#name').value);
  formData.append('explanation', document.querySelector('#explanation').value);
  formData.append('rentalHomeRule', document.querySelector('#rentalHomeRule').value);

  formData.append('region_no', document.querySelector('#region_no').value);
  formData.append('address', document.querySelector('#address').value);
  formData.append('lat', document.querySelector('#lat').value);
  formData.append('lon', document.querySelector('#lon').value);

  formData.append('price', document.querySelector('#price').value);
  formData.append('cleanFee', document.querySelector('#cleanFee').value);
  formData.append('hostingStartDate', document.querySelector('#hostingStartDate').value);
  formData.append('hostingEndDate', document.querySelector('#hostingEndDate').value);

  updateFormData(); // FormData 업데이트

  console.log(formData.getAll('photoExplanations'));
  console.log(formData.getAll('photos'));

  fetch('rentalHomeSave', {
    method: 'POST',
    body: formData

  }).then(response => {
    if (!response.ok) {
      throw new Error('Network response was not ok');
    }
    return response.text();

  }).then(data => {
    console.log('Response data:', data);
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
  var imageContainer = document.getElementById('imageContainer');

  for (var i = 0; i < files.length; i++) {
    let file = files[i];
    var reader = new FileReader();

    reader.onload = function (e) {
      var container = document.createElement('div');
      container.classList.add('image-container');

      var imgElement = document.createElement('img');
      imgElement.classList.add('image-item');
      imgElement.src = e.target.result;
      container.appendChild(imgElement);

      var descriptionInput = document.createElement('input');
      descriptionInput.type = 'text';
      descriptionInput.placeholder = '이미지 설명';
      descriptionInput.classList.add('image-description');
      descriptionInput.name = 'photoExplanations'; // 이미지 설명의 이름 설정
      container.appendChild(descriptionInput);

      var deleteButton = document.createElement('button');
      deleteButton.textContent = 'X';
      deleteButton.classList.add('delete-button');
      deleteButton.onclick = function () {
        var index = filesArray.findIndex(obj => obj.file === file);
        if (index > -1) {
          filesArray.splice(index, 1);
          container.remove();
        }
      };
      container.appendChild(deleteButton);

      imageContainer.appendChild(container);

      // 파일과 설명을 하나의 객체로 연결하여 배열에 추가
      filesArray.push({
        file: file,
        description: descriptionInput
      });
    };

    reader.readAsDataURL(file);
  }
}