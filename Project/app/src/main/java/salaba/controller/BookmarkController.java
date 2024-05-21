package salaba.controller;


import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import salaba.service.BookmarkService;
import salaba.vo.Member;

@RequiredArgsConstructor
@Controller
public class BookmarkController {

  private static final Log log = LogFactory.getLog(BookmarkController.class);
  private final BookmarkService bookmarkService;

  @GetMapping("/member/bookmark")
  public void getUserBookMark(
      Model model,
      HttpSession session) throws Exception {
    Member loginUser = (Member) session.getAttribute("loginUser");
    // 즐겨찾기 select
    model.addAttribute("bookMarkList",
          bookmarkService.selectUserBookMark(loginUser.getNo()));
  }

  @PostMapping("/bookmark/delete")
  public ResponseEntity<String> deleteBookMark(
      @RequestParam("rentalHomeNo") int rentalHomeNo,
      HttpSession session){
    Member loginUser = (Member) session.getAttribute("loginUser");
    bookmarkService.deleteBookMark(loginUser.getNo(), rentalHomeNo);
    return ResponseEntity.ok("즐겨찾기 삭제완료");
  }

  @PostMapping("/bookmark/add")
  public ResponseEntity<String> addBookMark(
      @RequestParam(value = "memberNo",required = false) Integer memberNo,
      @RequestParam(value = "rentalHomeNo", required = false) Integer rentalHomeNo){

    log.debug(String.format("로그 로그 memberNo : %s", memberNo));
    log.debug(String.format("로그 로그 rentalHomeNo : %s", rentalHomeNo));
    bookmarkService.addBookMark(memberNo,rentalHomeNo);
    return ResponseEntity.ok("즐겨찾기를 추가했습니다");
  }
}