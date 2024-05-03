package salaba.controller;


import java.util.List;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import salaba.service.MemberService;
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
  private final MemberService memberService;

  @GetMapping("reservationList")
  public void reservationList(
      Reservation reservation,
      Model model,
      HttpSession session) throws Exception {

    Member sessionInfo = (Member) session.getAttribute("loginUser");
    Member member = memberService.get(sessionInfo.getNo());
    reservation.setNo(sessionInfo.getNo());

    model.addAttribute("reservationList", reservationService.reservationList(reservation));
    model.addAttribute("member", member);

    //포인트
    String memberPoint = memberService.getMemberPoint(member);
    session.setAttribute("memberPoint", memberPoint);
    //등급
    Member memberGrade = memberService.getGrade(member);
    session.setAttribute("memberGradeNo", memberGrade.getGrade().getGradeNo());
    session.setAttribute("memberGradeName", memberGrade.getGrade().getGradeName());
  }

  @GetMapping("reservationView")
  public void reservationView(int reservationNo,Model model,HttpSession session) throws Exception {

    Member sessionInfo = (Member) session.getAttribute("loginUser");
    Member member = memberService.get(sessionInfo.getNo());

    Reservation reservation = reservationService.get(reservationNo);
    model.addAttribute("reservation", reservation);
    model.addAttribute("member", member);

    //포인트
    String memberPoint = memberService.getMemberPoint(member);
    session.setAttribute("memberPoint", memberPoint);
    //등급
    Member memberGrade = memberService.getGrade(member);
    session.setAttribute("memberGradeNo", memberGrade.getGrade().getGradeNo());
    session.setAttribute("memberGradeName", memberGrade.getGrade().getGradeName());
  }

}
