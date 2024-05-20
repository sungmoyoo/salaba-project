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
                        Swal.fire({
                            icon: "error",
                            title: "로그인이 필요합니다.",
                            showConfirmButton: false,
                            timer: 1000
                          });
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
                    Swal.fire({
                        icon: "error",
                        title: "이미지 업로드에 실패하였습니다.",
                        text: error,
                        showConfirmButton: false,
                        timer: 1000
                      });
                });
            }
        }
    });
});


$('#write-button').click(function(e) {
    e.preventDefault();
    let content = $('.note-editable').html();
    console.log(content);
    let imageCount = countImages(content);
    let categoryNo = $('#categoryNo').val();
    if (imageCount < 3 && categoryNo == 0) {
        Swal.fire({
            icon: "error",
            title: "이미지 파일을 3개이상 올려주세요",
            showConfirmButton: false,
            timer: 1000
          });
          return;
    }

    if ($("#titleInput").val().trim().length <= 2 || $("#summernote").val().trim().length <= 2) {
        Swal.fire({
            icon: "error",
            title: "제목과 내용은 2자 이상이여야 합니다.",
            showConfirmButton: false,
            timer: 1000
          });
          return;
      }

     $('form').submit();

  })
  function countImages(htmlString) {
    // 1. HTML 문자열을 DOM으로 파싱
    let parser = new DOMParser();
    let doc = parser.parseFromString(htmlString, 'text/html');

    // 2. 모든 img 태그를 찾기
    let images = doc.querySelectorAll('img');
    console.log(images)

    // 4. styledImages의 개수를 반환
    return images.length;
}


