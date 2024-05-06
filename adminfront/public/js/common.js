"use strict";

const pageContext = {
    params: new URL(document.location).searchParams
};

const axiosInstance = axios.create();

// 전역 에러 핸들러 추가
axiosInstance.interceptors.response.use(
    (response) => {
        return response;
    },
    (error) => {
        if (error.response.status === 403) {
            alert('권한이 없습니다.');
            // 특정 작업 수행
        } else if (error.response.status === 401) {
            location.href = '/';
        }
        return Promise.reject(error);
    }
);

