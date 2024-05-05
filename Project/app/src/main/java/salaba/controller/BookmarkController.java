package salaba.controller;


import java.util.List;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import salaba.service.BookmarkService;
import salaba.service.MemberService;
import salaba.service.ReservationService;
import salaba.service.StorageService;
import salaba.vo.Bookmark;
import salaba.vo.Member;
import salaba.vo.Reservation;

@RequiredArgsConstructor
@Controller
@RequestMapping("/bookmark")
public class BookmarkController {

  private static final Log log = LogFactory.getLog(BookmarkController.class);
  private final BookmarkService bookmarkService;
  private final MemberService memberService;
  private final StorageService storageService;

  @GetMapping("bookmark")
  public void bookmark(
      Bookmark bookmark,
      Model model,
      HttpSession session) throws Exception {

    Member loginUser = (Member) session.getAttribute("loginUser");
    Member member = memberService.get(loginUser.getNo());
    bookmark.setNo(loginUser.getNo());

    model.addAttribute("bookmark", bookmarkService.bookmark(bookmark));
    model.addAttribute("member", member);

    session.setAttribute("myInfoMenuId", bookmark.getMyInfoMenuId());
  }
}