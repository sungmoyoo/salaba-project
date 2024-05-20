let roomList = document.querySelectorAll('.room-container');

roomList.forEach(room => {
  let chatLink = room.querySelector('.room-info').getAttribute('data-href');

  room.onclick = () => {
    window.location.href=chatLink;
  };
});

function profileShow() {
  let profileImg = document.querySelectorAll(".profileImg");

  let count = 0;

  profileImg.forEach((element) => {
    element.src="https://5ns6sjke2756.edge.naverncp.com/nBMc0TCJiv/member/"+photoNames[count]+"?type=f&w=80&h=80&ttype=jpg"
    count++;
  });
}
profileShow();