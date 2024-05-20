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

            sessionStorage.setItem('accessToken', response.accessToken);
            sessionStorage.setItem('refreshToken', response.refreshToken);
            sessionStorage.setItem('memberNo', response.memberNo);
            sessionStorage.setItem('name', response.name);
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