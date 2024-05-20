"use strict"

const RESTAPI_HOST = "http://localhost:8890";



axiosInstance.get('/header.html', {
    responseType: 'text'
})
.then((response) => {
    $('header').html(response.data)
})
.then(() => {
    if (sessionStorage.getItem('accessToken')) {
        // loadUserInfo();
    }
});

axiosInstance.get('/sidebar.html', {
    responseType: 'text'
})
.then((response) => {
    $('sidebar').html(response.data)
})
