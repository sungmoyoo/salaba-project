package salaba.controller;


import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import salaba.service.ReservationService;
import salaba.vo.Member;
import salaba.vo.Reservation;

@RequiredArgsConstructor
@Controller
public class ReservationController {

  private static final Log log = LogFactory.getLog(ReservationController.class);
  private final ReservationService reservationService;

  @GetMapping("/member/reservationList")
  public void reservationList(
      Model model,
      HttpSession session) throws Exception {

    Member loginUser = (Member) session.getAttribute("loginUser");
    model.addAttribute("reservationList", reservationService.selectReservationList(loginUser.getNo()));
  }

  @GetMapping("/member/reservationView")
  public void reservationView(@RequestParam(value = "reservationNo" , required = false) Integer reservationNo, Model model) throws Exception {
    Reservation reservation = reservationService.selectReservation(reservationNo);
    model.addAttribute("reservation", reservation);
  }

}
