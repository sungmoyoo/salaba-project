package salaba.controller;

import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import salaba.service.Point_historyService;
import salaba.vo.Point_history;

@RequiredArgsConstructor
@Controller
@RequestMapping("/point_history")
public class PointHistoryController {

  private static final Log log = LogFactory.getLog(PointHistoryController.class);
  private final Point_historyService point_historyService;

  @GetMapping("pointList")
  public void pointList(
      Point_history pointHistory,
      Model model,
      HttpSession session) throws Exception {

    Member sessionInfo = (Member) session.getAttribute("loginUser");
    pointHistory.setNo(sessionInfo.getNo());

    model.addAttribute("pointList", point_historyService.pointList(pointHistory));

  }
}
