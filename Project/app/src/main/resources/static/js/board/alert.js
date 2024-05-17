$('#boardDelBtn').click(function(e) {
    e.preventDefault();
    let url = '/board/' + $(this).attr('href');
    let categoryNo = getQueryParam(url, 'categoryNo');
    Swal.fire({
        title: "정말로 삭제하시겠습니까?",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "삭제",
        cancelButtonText: "취소"
      }).then((result) => {
        
        if (result.isConfirmed) {
            $.ajax({
                url: url,
                type: 'get',
                success: function() {
                    Swal.fire({
                        title: "Deleted!",
                        text: "삭제가 완료되었습니다.",
                        icon: "success",
                        showConfirmButton: false,
                        timer: 1000
                      });
                      setTimeout(function() {
                        window.location.href= '/board/list?categoryNo=' + categoryNo;
                      }, 1100)
                    
                }
            })
        }
      });
    
})

$('#write-button').click(function(e) {
  e.preventDefault();
  let url = '/board/' + $(this).attr('href');
  $.ajax({
    url: '/board/getLoginUser',
    type: 'get',
    success: function() {
      window.location.href = url;
    },
    error: function() {
      Swal.fire({
        icon: "error",
        title: "로그인이 필요합니다.",
        showConfirmButton: false,
        timer: 1000
      });
    }
  })
  
})



function getQueryParam(url, param) {
    const params = new URLSearchParams(url.split('?')[1]);
    return params.get(param);
}