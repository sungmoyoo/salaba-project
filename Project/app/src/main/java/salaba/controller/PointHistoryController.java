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
import salaba.service.MemberService;
import salaba.service.PointHistoryService;
import salaba.vo.Member;
import salaba.vo.PointHistory;

@RequiredArgsConstructor
@Controller
@RequestMapping("/pointHistory")
public class PointHistoryController {

  private static final Log log = LogFactory.getLog(PointHistoryController.class);
  private final PointHistoryService pointHistoryService;
  private final MemberService memberService;

  @GetMapping("pointList")
  public void pointList(
      PointHistory pointHistory,
      Model model,
      HttpSession session) throws Exception {

    Member sessionInfo = (Member) session.getAttribute("loginUser");
    pointHistory.setNo(sessionInfo.getNo());

    model.addAttribute("pointList", pointHistoryService.pointList(pointHistory));

  }
}
