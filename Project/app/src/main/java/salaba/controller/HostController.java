package salaba.controller;

import java.util.ArrayList;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;
import salaba.vo.rentalHome.RentalHomeFacility;
import salaba.vo.rentalHome.RentalHomePhoto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import salaba.service.HostService;
import salaba.service.StorageService;
import salaba.vo.host.HostReservation;
import salaba.vo.rentalHome.RentalHome;
import salaba.vo.rentalHome.Theme;

//호스트 페이지 컨트롤러
@RequiredArgsConstructor
@Controller
@RequestMapping("/host")
public class HostController {
  private static final Log log = LogFactory.getLog(HostController.class);
  private final HostService hostService;
  private final StorageService storageService;
  private String uploadDir = "rentalHome/";

  @Value("${ncp.bucketname}")
  private String bucketName;

  @GetMapping("hostStart") // 호스트 숙소등록 시작화면
  public void hostStart() {
  }

  @GetMapping("rentalHomeForm") // 호스트 숙소등록 기본정보 폼
  public void rentalHomeForm() {
  }

  @PostMapping("rentalHomeSave") // 호스트 숙소 기본정보 저장
  public String rentalHomeSave(HttpSession session, RentalHome rentalHome) {
    session.setAttribute("rentalHome",rentalHome);
    return "redirect:themeForm";
  }

  @GetMapping("themeForm") // 호스트 숙소 테마 폼
  public void themeForm(Model model){
    model.addAttribute("themeList", hostService.themeList());
  }

  @PostMapping("themeSave") // 호스트 숙소 테마 저장
  public String themeSave(HttpSession session, Theme theme) {
    session.setAttribute("theme",theme);
    return "redirect:rentalHomeFacilityForm";
  }

  @GetMapping("rentalHomeFacilityForm") // 호스트 숙소 시설 폼
  public void rentalHomeFacilityForm(Model model){
    model.addAttribute("facilityList", hostService.facilityList());
  }

  @PostMapping("rentalHomeFacilitySave") // 호스트 숙소 시설 저장
  public String rentalHomeFacilitySave(HttpSession session, RentalHomeFacility rentalHomeFacility) {
    session.setAttribute("rentalHomeFacility", rentalHomeFacility);
    return "redirect:rentalHomeConfirm";
  }

  @GetMapping("rentalHomeConfirm") // 호스트 숙소 등록 최종 확인
  public void rentalHomeConfirm(HttpSession session) {
  }

  @Transactional
  @GetMapping("rentalHomeAdd") // DB에 숙소 등록
  public void rentalHomeAdd(
      MultipartFile[] photos,
      Model model,
      HttpSession session) throws Exception {

    RentalHome rentalHome = (RentalHome) session.getAttribute("rentalHome");

    // 숙소 사진 추가하는 메서드
    ArrayList<RentalHomePhoto> files = new ArrayList<>();
    int order = 1;  // 사진 순서
      for (MultipartFile file : photos) {
        if (file.getSize() == 0) {
          continue;
        }
        String filename = storageService.upload(this.bucketName, this.uploadDir, file); // uuid_file_name
        files.add(
            RentalHomePhoto.builder()
            .uuidPhotoName(filename)
                .oriPhotoName(file.getOriginalFilename()) // ori_file_name
                .photoOrder(order)
                .build());
        // 사진 설명 추가하는 부분은 고민중..
        order++;
      }

    if (files.size() > 0) {
      rentalHome.setRentalHomePhotos(files);
    }


    hostService.rentalHomeAdd(rentalHome);


    session.setAttribute("rentalHome", rentalHome);
  }

  // 예약 내역 리스트
  @GetMapping("reservationList")
  public void reservationList(Model model, int hostNo) throws Exception {
    model.addAttribute("list", hostService.list(hostNo));
  }

  // 예약 상태 업데이트
  @PostMapping("reservationCheck")
  public String stateUpdate(HostReservation hostReservation) throws Exception {
    hostService.stateUpdate(hostReservation.getState(),
        hostReservation.getReservationNo());
    return "redirect:reservationList?hostNo=" + hostReservation.getHostNo();
  }
}
