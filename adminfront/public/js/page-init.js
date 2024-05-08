"use strict"

const RESTAPI_HOST = "http://localhost:8890";

if (sessionStorage.getItem("accessToken")) {
    axiosInstance.defaults.headers.common['Authorization'] = `Bearer ${sessionStorage.getItem("accessToken")}`;
    // console.log(axios.defaults.headers);
} 


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
