package salaba.controller;

import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import salaba.service.RentalHomeService;
import salaba.vo.Member;
import salaba.vo.rental_home.RentalHomeReview;

@RequiredArgsConstructor
@Controller
public class RentalHomeController {

  private final RentalHomeService rentalHomeService; // RentalHomeService

  @GetMapping("")
  public void rentalHomeMain( HttpSession httpSession, Model model){ // 메인화면 , 숙소 목록 출력
    Member loginUser = (Member) httpSession.getAttribute("loginUser");

    // LogIn User Check
    if( loginUser == null || loginUser.getThemes() == null ){
      model.addAttribute("rentalHomeList", rentalHomeService.getRentalHomeMain());
    }
    else{
      model.addAttribute("rentalHomeList",
          rentalHomeService.getRentalHomeMainForLoginUser(loginUser.getThemes()));
    }
  }

  @GetMapping("")
  public void rentalHomeView( int rentalHomeNo, Model model){ // 숙소 상세 조회
    model.addAttribute("rentalHome", rentalHomeService.getRentalHomeDetailView(rentalHomeNo));
  }


  @PostMapping("")
  public String rentalHomeReviewAdd( RentalHomeReview rentalHomeReview) {
    rentalHomeService.addRentalHomeReview(rentalHomeReview); // 숙소 리뷰 작성

    return ""; // 작성전 페이지로 돌아가기
  }
}

