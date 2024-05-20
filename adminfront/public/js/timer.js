"use strict"

let timeInterval;
let initialDuration = 30 * 60; // 초기 duration 값 설정

function startTimer(duration, display) {
    let timer = duration // 타이머 초기값 설정
    let minutes;
    let seconds;
    timeInterval = setInterval(function () {
        sessionStorage.setItem("remainingTime", timer);

        minutes = Math.floor(timer / 60);
        seconds = timer % 60;

        minutes = minutes < 10 ? "0" + minutes : minutes;
        seconds = seconds < 10 ? "0" + seconds : seconds;

        display.text(minutes + ":" + seconds);

        if (--timer < 0) {
            clearInterval(timeInterval);
            sessionStorage.removeItem("remainingTime");
            Swal.fire({
                icon: "error",
                title: "토큰이 만료되어 로그아웃 합니다.",
            });
        }
    }, 1000);
}


function getRemainingTime() {
    const savedTime = sessionStorage.getItem("remainingTime");
    if (savedTime) {
        return parseInt(savedTime, 10);
    }
    return initialDuration;
}

function stopTimer() {
    clearInterval(timeInterval);
}