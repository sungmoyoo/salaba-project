package salaba.controller;


import java.util.List;
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
import salaba.service.RentalHomeService;
import salaba.service.ReservationService;
import salaba.vo.ConstVO;
import salaba.vo.Member;
import salaba.vo.Reservation;
import salaba.vo.rental_home.RentalHomeReview;

@RequiredArgsConstructor
@Controller
public class ReservationController {

  private static final Log log = LogFactory.getLog(ReservationController.class);
  private final ReservationService reservationService;
  private final RentalHomeService rentalHomeService;

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
    if(reservation.getState().equals(ConstVO.reservation_state_end)){
      List<RentalHomeReview> reviews = rentalHomeService.getRentalHomeReviewList(reservation.getRentalHomeNo());
      model.addAttribute("reviews", reviews);
    }
    model.addAttribute("reservation", reservation);
  }

  @PostMapping("/member/reservation/cancel")
  public ResponseEntity<Integer> updateReservationForCancel(@RequestParam(value = "reservationNo", required = false) Integer reservationNo){
    int result = reservationService.updateReservationForCancel(reservationNo);

    return ResponseEntity.ok(result);
  }
}
