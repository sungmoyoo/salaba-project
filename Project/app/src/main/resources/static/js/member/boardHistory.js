function fn_goPage(gbn){
   if(gbn == '1'){
     frm.method = "get";
     frm.action = "/member/boardHistory";
     frm.submit();
  }else{
     frm.method = "get";
     frm.action = "/member/commentHistory";
     frm.submit();
  }
}

// 모든 행을 선택하여 클릭 이벤트를 추가
    document.querySelectorAll("table").forEach(table => {
      table.addEventListener("click", function(event) {
        let target = event.target;

        // 클릭된 요소에서 가장 가까운 tr 요소를 찾기
        while (target && target.nodeName !== "TR") {
          target = target.parentNode;
        }

        // tr 요소 내부에 있는 a 태그를 찾기
        if (target) {
          const link = target.querySelector("a");
          if (link && link.href) {
            event.preventDefault(); // 기본 이벤트 중지
            window.location.href = link.href; // 해당 링크로 페이지 이동
          }
        }
      });
    });

// 검색
    // 페이지가 로드될 때 저장된 검색어가 있는지 확인 -> 있으면 검색창에 남겨두기
  window.addEventListener('DOMContentLoaded', (event) => {
    initSlider();
    var savedKeyword = localStorage.getItem('searchKeyword');
    if (savedKeyword) {
      document.getElementById('bsearch-input').value = savedKeyword;
      // 검색어를 가져왔으면 -> 다시 로컬 스토리지에서 삭제
      localStorage.removeItem('searchKeyword');
    }
  });

  // 검색 버튼 클릭 시 검색어를 로컬 스토리지에 저장
  document.getElementById('bsearch-button').addEventListener('click', function(event) {
    event.preventDefault();
    var keyword = document.getElementById('bsearch-input').value;
    localStorage.setItem('searchKeyword', keyword);
    document.getElementById('bsearch-form').submit();
  });