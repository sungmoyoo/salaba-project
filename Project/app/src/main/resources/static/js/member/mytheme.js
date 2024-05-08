$(document).ready(function() {
        $("input[type='checkbox']").on("click", function(){
            var className = $(this).attr("class");
            var roomTypeCnt = 0;
            var roomThemeCnt = 0;
            if(className == "roomType"){
              $(".roomType").each(function() {
                if($(this).is(':checked')){
                  roomTypeCnt++;
                }
              });

              if(roomTypeCnt > 3){
                alert("숙소 타입은 3개까지만 선택할 수 있습니다.");
                return false;
              }else{
                return true;
              }
            }else{
              $(".roomTheme").each(function() {
                if($(this).is(':checked')){
                  roomThemeCnt++;
                }
              });

              if(roomThemeCnt > 5){
                alert("숙소 테마는 5개까지만 선택할 수 있습니다.");
                return false;
              }else{
                return true;
              }
            }
        });
    });