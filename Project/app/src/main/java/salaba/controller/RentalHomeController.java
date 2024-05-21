package salaba.controller;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import salaba.service.BookmarkService;
import salaba.service.PaymentService;
import salaba.service.RentalHomeService;
import salaba.vo.Member;
import salaba.vo.Reservation;
import salaba.vo.rental_home.RentalHome;
import salaba.vo.rental_home.RentalHomePhoto;
import salaba.vo.rental_home.RentalHomeReport;
import salaba.vo.rental_home.RentalHomeReview;

@RequiredArgsConstructor
@Controller
public class RentalHomeController {

  private final static Log log = LogFactory.getLog(RentalHomeController.class);
  private final RentalHomeService rentalHomeService; // RentalHomeService
  private final BookmarkService bookmarkService;

  @GetMapping("/main")
  public String rentalHomeMain( HttpSession httpSession, Model model,
      @RequestParam( value = "regionName", defaultValue = "all") String regionName,
      @RequestParam( value = "checkInDate", required = false)
      @DateTimeFormat( pattern = "yyyy-MM-dd") Date checkInDate,
      @RequestParam( value = "checkOutDate", required = false )
      @DateTimeFormat( pattern = "yyyy-MM-dd") Date checkOutDate,
      @RequestParam( value = "capacity", defaultValue = "1") int capacity) throws Exception{ // 메인화면
    Member loginUser = (Member) httpSession.getAttribute("loginUser");
    // 세션에 테마가 없는 경우 세션에 테마 저장
    if(httpSession.getAttribute("themeList") == null ){
      httpSession.setAttribute("themeList", rentalHomeService.getAllThemes());
    }
    
    // 세션에 편의시설이 없는 경우 세션에 편의시설 저장
    if(httpSession.getAttribute("facilityList") == null){
      httpSession.setAttribute("facilityList", rentalHomeService.getAllFacilities());
    }

    // LogIn User Check
    if(  loginUser == null   && ( regionName.equalsIgnoreCase("all") &&
        checkInDate == null && checkOutDate == null && capacity == 1 ) ){

      // 로그인하지 않은 경우 기본 숙소 목록 출력 검색 하지 않은 경우
      model.addAttribute("rentalHomeList", rentalHomeService.getRentalHomeMain());
    }
    else if(
         loginUser != null && loginUser.getThemes() != null && !loginUser.getThemes().isEmpty()   &&
         regionName.equalsIgnoreCase("all") &&
        checkInDate == null && checkOutDate == null && capacity == 1 ){
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

  @GetMapping("/theme")
  public String rentalHomeTheme( @RequestParam( value = "themeName") String themeName, Model model ){
    log.debug(String.format("테마명 : %s", themeName));
    List<RentalHome> rentalHome = rentalHomeService.getRentalHomeThemeSearch(themeName);
    model.addAttribute("rentalHomeList", rentalHome);

    for( RentalHome rh : rentalHome ){
      log.debug(String.format("rentalHome : %s", rh.getRentalHomeNo()));
      log.debug(String.format("rentalHome : %s", rh.getName()));
      for( RentalHomePhoto rp : rh.getRentalHomePhotos() ){
        log.debug(String.format("rentalHomePhoto : %s", rp.getPhotoNo()));
        log.debug(String.format("rentalHomePhoto : %s", rp.getUuidPhotoName()));
      }
    }

    return "main";
  }

  @GetMapping("/rentalHome/view")
  public void rentalHomeView( @RequestParam(value = "rentalHomeNo") int rentalHomeNo, Model model, HttpSession session){ // 숙소 상세 조회
    RentalHome rentalHome = rentalHomeService.getRentalHomeDetailView(rentalHomeNo);
    model.addAttribute("rentalHome", rentalHome);
    model.addAttribute("rentalHomeReview", rentalHomeService.getRentalHomeReviewList(rentalHomeNo));
    model.addAttribute("rentalHomeReviewAverage",Math.round(rentalHomeService.rentalHomeReviewAverage(rentalHomeNo) * 100) / 100.0);
    model.addAttribute("rentalHomePhoto", rentalHomeService.getRentalHomePhotos(rentalHomeNo));
    model.addAttribute("rentalHomeFacility", rentalHomeService.getRentalHomeFacilities(rentalHomeNo));

    Member loginUser = (Member) session.getAttribute("loginUser");
    if(loginUser != null){
      int result = bookmarkService.selectOneBookMark(loginUser.getNo(), rentalHomeNo);
      if( result > 0 ){
        model.addAttribute("bookMark", result);
      }
    }
  }


@PostMapping("/rentalHome/addReview")
  public ResponseEntity<String> rentalHomeReviewAdd( RentalHomeReview rentalHomeReview,
    @RequestParam(value = "rentalHomeNo", required = false) Integer rentalHomeNo) {

    log.debug(String.format("reservationNo : %s", rentalHomeReview.getReservationNo()));
    rentalHomeService.addRentalHomeReview(rentalHomeReview); // 숙소 리뷰 작성
    return ResponseEntity.ok("리뷰 작성 완료");
  }

  @GetMapping("/rentalHome/review")
  public void rentalHomeReviewList( int rentalHomeNo, Model model ){
    // 숙소 리뷰 조회
    model.addAttribute("rentalHomeReviewList",
        rentalHomeService.getRentalHomeReviewList(rentalHomeNo));
  }

  @PostMapping("/rentalHome/report")
  public ResponseEntity<?> rentalHomeReportAdd( @RequestBody RentalHomeReport rentalHomeReport){
    try {
      rentalHomeService.addRentalHomeReport(rentalHomeReport); // 숙소 신고
      return ResponseEntity.ok().build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }



  }

  @GetMapping("/rentalHome/reservation")
  public void getReservationInfo(
      @RequestParam("rentalHomeNo") int rentalHomeNo,
      @RequestParam("checkInDate") String checkInDate,
      @RequestParam("checkOutDate") String checkOutDate,
      @RequestParam("guests") int guests,
      Model model ){

    model.addAttribute("reservationInfo", rentalHomeService.getReservationInfo(rentalHomeNo));
    model.addAttribute("checkInDate", checkInDate);
    model.addAttribute("checkOutDate", checkOutDate);
    model.addAttribute("days", dateDifferenceCalculator(checkInDate, checkOutDate));
    model.addAttribute("guests", guests);
    model.addAttribute("reviewAverage",Math.round(rentalHomeService.rentalHomeReviewAverage(rentalHomeNo) * 100) / 100.0);
  }

  private long dateDifferenceCalculator(String startDateStr, String endDateStr) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");

    // 문자열을 LocalDate 객체로 변환
    LocalDate startDate = LocalDate.parse(startDateStr, formatter);
    LocalDate endDate = LocalDate.parse(endDateStr, formatter);

    // 날짜 간의 차이 계산
    return ChronoUnit.DAYS.between(startDate, endDate);
  }

}

