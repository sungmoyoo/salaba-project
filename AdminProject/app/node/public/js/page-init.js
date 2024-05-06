"use strict"

const RESTAPI_HOST = "http://localhost:8889";

if (sessionStorage.getItem("accessToken")) {
    axios.defaults.headers.common['Authorization'] = `Bearer ${sessionStorage.getItem("accessToken")}`;
    // console.log(axios.defaults.headers);
}

axios.get('/header.html', {
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

axios.get('/sidebar.html', {
    responseType: 'text'
})
.then((response) => {
    $('sidebar').html(response.data)
})

// function loadUserInfo() {
//     axios.get(`${RESTAPI_HOST}/auth/userInfo`)
//     .then((response) => {
//         let result = response.data;
//         if (result.status == "success") {

//         }
//     })  
// }

// function logout(e) {
//     e.preventDefault();

//     axios.get(`${RESTAPI_HOST}/auth/logout`)
//     .then((response) => {
//         let result = response.data;
//         console.log(result);
//         $.removeCookie("TOKEN", {path: '/'});
//         location.href = "/";
//     });
// }