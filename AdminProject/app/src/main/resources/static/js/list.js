
document.addEventListener("DOMContentLoaded", function() {
    let buttons = document.getElementsByClassName("view");
    let modal = document.getElementById("detailModal");

    Array.from(buttons).forEach((button) => {
        button.addEventListener("click", function () {
            console.log("click")
            // AJAX를 사용하여 서버에 HTML 조각 요청
            let xhr = new XMLHttpRequest();
            xhr.onreadystatechange = function () {
                if (xhr.readyState == 4 && xhr.status == 200) {
                    document.getElementById("modalContent").innerHTML =
                        xhr.responseText;
                    modal.style.display = "block";
                }
            };
            let url = button.getAttribute("href");
            console.log(url);
            xhr.open("GET", url, true); // 서버에 요청 보내는 URL
            xhr.send();
        });
    });

    let closeButton = document.getElementById("closeBtn");
    closeButton.addEventListener("click", function () {
        modal.style.display = "none";
    });
});