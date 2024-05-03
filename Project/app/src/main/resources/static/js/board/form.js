 // summernote 불러오기
  $(document).ready(function() {
    $('#summernote').summernote({
        height: 800,
        width: 1400,
        callbacks: {
            onInit: function() {
                console.log('Summernote 생성됨!');
            },
            onImageUpload: function(files) {
                let data = new FormData();
                for (const file of files) {
                    data.append("files", file);
                }
                let categoryNo = $("#categoryNo").val();

                $.ajax({
                    url: 'file/upload?categoryNo=' + categoryNo,
                    type: 'POST',
                    dataType: 'json',
                    contentType: false,
                    processData: false,
                    data: data
                })
                .done(function(result) {
                    if (result.length == 0) {
                        alert('로그인 하세요!');
                        return;
                    }

                    // 카테고리 번호에 따라 다른 경로를 설정
                    let basePath = 'https://kr.object.ncloudstorage.com/tp3-salaba/board/';
                    if (categoryNo == '0') {
                        basePath += 'review/';
                    } else {
                        basePath += 'community/';
                    }

                    for (const boardFile of result) {
                        $('#summernote').summernote('insertImage',
                            basePath + boardFile.uuidFileName);
                    }
                })
                .fail(function(xhr, status, error) {
                    console.error('Upload failed: ' + error);
                    alert('이미지 업로드 실패: ' + error);
                });
            }
        }
    });
});


// 게시글 공개범위 설정
  function setScopeNo(input) {
    // 선택된 라디오 버튼의 값을 scopeNo에 설정
    document.getElementById("scopeNo").value = input.dataset.thValue;
  }
