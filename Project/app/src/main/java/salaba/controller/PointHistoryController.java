package salaba.controller;

import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import salaba.service.PointHistoryService;
import salaba.vo.Member;


@RequiredArgsConstructor
@Controller
public class PointHistoryController {

  private static final Log log = LogFactory.getLog(PointHistoryController.class);
  private final PointHistoryService pointHistoryService;

  @GetMapping("/member/pointHistory")
  public void getUserPointHistory(
      Model model,
      HttpSession session) throws Exception {

    Member loginUser = (Member) session.getAttribute("loginUser");
    model.addAttribute("pointList", pointHistoryService.selectUserPointHistory(loginUser.getNo()));

  }
}
