package salaba.controller;

import java.sql.Date;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import salaba.service.RentalHomeService;
import salaba.vo.Member;
import salaba.vo.rental_home.RentalHomeReport;
import salaba.vo.rental_home.RentalHomeReview;

@RequiredArgsConstructor
@Controller
@RequestMapping("/rentalHome")
public class RentalHomeController {

  private final static Log log = LogFactory.getLog(RentalHomeController.class);
  private final RentalHomeService rentalHomeService; // RentalHomeService

  @GetMapping("/search")
  public String rentalHomeMain( HttpSession httpSession, Model model,
      @RequestParam( name = "region_name", required = false) String regionName,
      @RequestParam( name = "check_in", required = false) Date checkInDate,
      @RequestParam( name = "check_out", required = false ) Date checkOutDate,
      @RequestParam( name = "capacity", required = false ) Integer capacity){ // 메인화면
    Member loginUser = (Member) httpSession.getAttribute("loginUser");

    // LogIn User Check
    if( ( loginUser == null || loginUser.getThemes() == null ) &&
        ( (regionName == null || regionName.isEmpty()) &&
        checkInDate == null && checkOutDate == null && capacity == null ) ){

      // 로그인하지 않은 경우 기본 숙소 목록 출력 검색 하지 않은 경우
      model.addAttribute("rentalHomeList", rentalHomeService.getRentalHomeMain());
    }
    else if(
         loginUser != null && loginUser.getThemes() != null &&
        regionName == null && regionName.isEmpty() &&
        checkInDate == null && checkOutDate == null && capacity == 0 ){
      // 로그인한 유저 중 선호 사항을 고른 유저의 경우
      // 선호사항으로 숙소 목록 출력
      model.addAttribute("rentalHomeList",
          rentalHomeService.getRentalHomeMainForLoginUser(loginUser.getThemes()));
    }
    else{
      // 숙소 검색
      model.addAttribute("rentalHomeList",
          rentalHomeService.getRentalHomeConditionSearch(regionName,checkInDate,checkOutDate,capacity));
    }
    return "main";
  }

//  @GetMapping("conditionSearch")
//  public void rentalHomeSearch( Model model,
//      @RequestParam( name = "region_name", required = false) String regionName,
//      @RequestParam( name = "check_in", required = false) Date checkInDate,
//      @RequestParam( name = "check_out", required = false ) Date checkOutDate,
//      @RequestParam( name = "capacity", required = false ) int capacity){ // 숙소 검색
//    model.addAttribute("rentalHomeList",
//        rentalHomeService.getRentalHomeConditionSearch(regionName,checkInDate,checkOutDate,capacity));
//  }

//  @GetMapping
  public void rentalHomeThemeSearch( Model model, String themeName ){
    model.addAttribute("rentalHomeList",
        rentalHomeService.getRentalHomeThemeSearch(themeName));
  }

//  @GetMapping("")
  public void rentalHomeView( int rentalHomeNo, Model model){ // 숙소 상세 조회
    model.addAttribute("rentalHome", rentalHomeService.getRentalHomeDetailView(rentalHomeNo));
  }


//  @PostMapping("")
  public String rentalHomeReviewAdd( RentalHomeReview rentalHomeReview) {
    rentalHomeService.addRentalHomeReview(rentalHomeReview); // 숙소 리뷰 작성
    return ""; // 작성전 페이지로 돌아가기
  }

//  @GetMapping("")
  public void rentalHomeReviewList( int rentalHomeNo, Model model ){
    // 숙소 리뷰 조회
    model .addAttribute("rentalHomeReviewList",
        rentalHomeService.getRentalHomeReviewList(rentalHomeNo));
  }

//  @PostMapping("")
  public String rentalHomeReportAdd( RentalHomeReport rentalHomeReport){
    rentalHomeService.addRentalHomeReport(rentalHomeReport); // 숙소 신고

    return ""; // 작성전 페이지로 돌아가기
  }

}

