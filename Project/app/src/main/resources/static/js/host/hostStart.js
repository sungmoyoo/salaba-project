let validator = document.getElementById('validator');
validator.onclick = () => {
  console.log(loginUser.name);
  if (loginUser.telNo != null) {
    window.location.href= "rentalHomeForm";
  } else {
    window.alert("숙소 등록은 본인 인증 후 가능합니다.");
    window.location.href= "/member/chkPw";
  }
};

const content = "간단하게 \n 사라바 \n 호스팅을 시작할 수 \n 있습니다.";

const text = document.querySelector("#text");
let i = 0;

function typing(){
  let txt = content.charAt(i++);
  text.innerHTML += txt=== "\n" ? "<br/>": txt;
}

setInterval(typing, 200)