$('#signIn').click((e) => {
    let email = $('#email').children().first().val();
    let pw = $('#pw').children().first().val();
    let jsonData = {
            email: email,
            password: pw
        }
    $.ajax({
        url: `${RESTAPI_HOST}/auth/login`,
        type: 'POST',
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify(jsonData),

        success: (response) => {
            console.log(response);
            //accessToken은 sessionStorage에
            sessionStorage.setItem('accessToken', response.accessToken);
            sessionStorage.setItem('memberNo', response.memberNo);
            sessionStorage.setItem('name', response.name);
            //refreshToken은 HttpOnly 쿠키에(요청시 자동으로 전송되지만 JavaScript로 절대 읽을 수 없다.)
            document.cookie = `refreshToken=${response.refreshToken}; Secure; HttpOnly; SameSite=Strict`;
            window.location.href = '/home';
        },
        error: (error) => {
            Swal.fire({
                icon: "error",
                title: "유효하지 않거나 권한이 없습니다.",
                showConfirmButton: false,
                timer: 1000
              })
        }
    });
});