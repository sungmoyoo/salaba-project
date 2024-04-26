package salaba.controller;


import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import salaba.service.QuestionService;
import salaba.service.RentalHomeService;
import salaba.service.ReservationService;
import salaba.service.StorageService;
import salaba.vo.Member;
import salaba.vo.Question;
import salaba.vo.Reservation;
import salaba.vo.rental_home.RentalHome;

@RequiredArgsConstructor
@Controller
@RequestMapping("/reservation")
public class ReservationController {

  private static final Log log = LogFactory.getLog(ReservationController.class);
  private final ReservationService reservationService;
  private final RentalHomeService rentalHomeService;
  private final StorageService storageService;

  @GetMapping("reservationList")
  public void reservationList(
      Reservation reservation,
      Model model,
      HttpSession session) throws Exception {

    Member sessionInfo = (Member) session.getAttribute("loginUser");
    reservation.setNo(sessionInfo.getNo());

    model.addAttribute("reservationList", reservationService.reservationList(reservation));
  }

  @GetMapping("reservationView")
  public void reservationView(int reservationNo,Model model) throws Exception {

    Reservation reservation = reservationService.get(reservationNo);
    model.addAttribute("reservation", reservation);
  }

}
