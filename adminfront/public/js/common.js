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

function formatDate(dateString) {
    // Date 객체 생성
    let date = new Date(dateString);

    // 년, 월, 일 정보 추출
    let year = date.getFullYear();
    let month = String(date.getMonth() + 1).padStart(2, '0'); // 월은 0부터 시작하므로 +1을 해주고, 두 자리로 만들기 위해 padStart 사용
    let day = String(date.getDate()).padStart(2, '0'); // 날짜를 두 자리로 만들기 위해 padStart 사용

    // YYYY-mm-DD 형식으로 조합
    return `${year}-${month}-${day}`;
}

