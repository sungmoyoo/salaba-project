package salaba.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import salaba.service.HostService;
import salaba.service.StorageService;
import salaba.vo.host.HostReservation;
import salaba.vo.rental_home.RentalHome;
import salaba.vo.rental_home.RentalHomeFacility;
import salaba.vo.rental_home.RentalHomePhoto;
import salaba.vo.rental_home.Theme;


//호스트 페이지 컨트롤러
@RequiredArgsConstructor
@Controller
@RequestMapping("/host")
@SessionAttributes("rentalHome")

public class HostController {

  private static final Log log = LogFactory.getLog(HostController.class);
  private final HostService hostService;
  private final StorageService storageService;
  private String uploadDir = "rentalHome/";

  @Value("${ncp.bucketname}")
  private String bucketName;

  // 호스트 숙소등록 시작화면
  @GetMapping("hostStart")
  public void hostStart() {
  }

  // 호스트 숙소등록 기본정보 폼
  @GetMapping("rentalHomeForm")
  public void rentalHomeForm() {
  }

  // 호스트 숙소 기본정보 저장
  @PostMapping("rentalHomeSave")
  @ResponseBody
  public String rentalHomeSave(
      Model model, RentalHome rentalHome,
      MultipartFile[] photos,
      String[] photoExplanations) throws Exception {

    rentalHome.setRentalHomePhotos(photoTransform(photos, photoExplanations, 0));

    model.addAttribute("rentalHome", rentalHome);
    return "success";
  }

  // 호스트 숙소 테마 폼
  @GetMapping("themeForm")
  public void themeForm(Model model) {
    model.addAttribute("themeList", hostService.themeList());
  }

  // 호스트 숙소 테마 저장
  @PostMapping("themeSave")
  public String themeSave(HttpSession session,
      Model model,
      @RequestParam List<Integer> themeNos,
      @RequestParam List<String> themeNames,
      @RequestParam String type) {
    RentalHome rentalHome = (RentalHome) session.getAttribute("rentalHome");

    rentalHome.setThemes(themeTransform(themeNos, themeNames, type));

    model.addAttribute("rentalHome", rentalHome);

    return "redirect:rentalHomeFacilityForm";
  }

  // 호스트 숙소 시설 폼
  @GetMapping("rentalHomeFacilityForm")
  public void rentalHomeFacilityForm(Model model) {
    model.addAttribute("facilityList", hostService.facilityList());
  }

  // 호스트 숙소 시설 저장
  @PostMapping("rentalHomeFacilitySave")
  public String rentalHomeFacilitySave(HttpSession session,
      Model model,
      @RequestParam int capacity,
      @RequestParam List<Integer> facilityCount,
      @RequestParam List<Integer> facilityNos,
      @RequestParam List<String> facilityNames
  ) {
    RentalHome rentalHome = (RentalHome) session.getAttribute("rentalHome");

    rentalHome.setRentalHomeFacilities(
        facilityTransform(facilityCount, facilityNos, facilityNames));

    rentalHome.setCapacity(capacity);

    model.addAttribute("rentalHome", rentalHome);

    return "redirect:rentalHomeConfirm";
  }

  // 호스트 숙소 등록 최종 확인
  @GetMapping("rentalHomeConfirm")
  public void rentalHomeConfirm() {
  }

  // DB에 숙소 등록
  @Transactional
  @GetMapping("rentalHomeAdd")
  public String rentalHomeAdd(
      HttpSession session,
      SessionStatus sessionStatus) {

    RentalHome rentalHome = (RentalHome) session.getAttribute("rentalHome");

    hostService.rentalHomeAdd(rentalHome);

    // 숙소 등록 후 임시 정보 값 제거
    sessionStatus.setComplete();

    return "rentalHomeList?hostNo=1";
  }

  // 숙소 관리 리스트
  @GetMapping("rentalHomeList")
  public void rentalHomeList(Model model, int hostNo) {
    List<RentalHome> list = hostService.rentalHomeList(hostNo);
    for (RentalHome rentalHome : list) {
      System.out.println(rentalHome.getRentalHomeNo());
    }
    model.addAttribute("list", list);
  }

  // 숙소 상세(수정)
  @GetMapping("rentalHomeView")
  public void rentalHomeView(int rentalHomeNo, Model model, SessionStatus sessionStatus)
      throws Exception {
    RentalHome rentalHome = hostService.getRentalHome(rentalHomeNo);

    model.addAttribute("themeList", hostService.themeList());
    model.addAttribute("facilityList", hostService.facilityList());
    model.addAttribute("rentalHome", rentalHome);

    // 자동 바인딩 문제
    sessionStatus.setComplete();
  }

  @PostMapping("rentalHomeUpdate")
  @ResponseBody
  public String rentalHomeUpdate(
      RentalHome rentalHome,
      @RequestParam(required = false) MultipartFile[] photos,
      @RequestParam(required = false) String[] photoExplanations,
      @RequestParam List<Integer> themeNos,
      @RequestParam List<String> themeNames,
      @RequestParam String type,
      @RequestParam int capacity,
      @RequestParam List<Integer> facilityCount,
      @RequestParam List<Integer> facilityNos,
      @RequestParam List<String> facilityNames,
      SessionStatus sessionStatus
  ) throws Exception {

    if (rentalHome.getRentalHomePhotos() == null) {
      System.out.println("true");
    }

    // 모든 숙소 정보 vo 형태에 맞게 변환 후 set
    if (photos != null) { // 추가된 사진이 있을 때만
      RentalHome old = hostService.getRentalHome(rentalHome.getRentalHomeNo());
      List<RentalHomePhoto> newFiles = photoTransform(
          photos,
          photoExplanations,
          old.getRentalHomePhotos().getLast().getPhotoOrder());
      rentalHome.setRentalHomePhotos(newFiles);
    }

    rentalHome.setThemes(themeTransform(themeNos, themeNames, type));
    rentalHome.setRentalHomeFacilities(
        facilityTransform(facilityCount, facilityNos, facilityNames));
    rentalHome.setCapacity(capacity);

    // 숙소 업데이트
    hostService.rentalHomeUpdate(rentalHome);

    // 숙소 수정 후 임시 정보 값 제거
    sessionStatus.setComplete();
    return "rentalHomeView?rentalHomeNo=" + rentalHome.getRentalHomeNo();
  }

  @GetMapping("photoDelete")
  public String photoDelete(int photoNo, int rentalHomeNo) {
    hostService.deleteRentalHomePhoto(photoNo);
    return "redirect:rentalHomeView?rentalHomeNo=" + rentalHomeNo;
  }

  @GetMapping("incomeList")
  public void incomeList(Model model, int hostNo) {
    model.addAttribute("reservationList", hostService.reservationList(hostNo));
    model.addAttribute("rentalHomeList", hostService.rentalHomeList(hostNo));

  }

  // 예약 내역 리스트
  @GetMapping("reservationList")
  public void reservationList(Model model, int hostNo) {
    model.addAttribute("list", hostService.reservationList(hostNo));
  }

  // 예약 상태 업데이트
  @PostMapping("reservationCheck")
  public String reservationStateUpdate(HostReservation hostReservation) {
    hostService.reservationStateUpdate(hostReservation.getState(),
        hostReservation.getReservationNo());
    return "redirect:reservationList?hostNo=" + hostReservation.getHostNo();
  }


  // 숙소 정보 저장하기 위해 form으로부터 받은 정보들 변환하는 메서드들
  private ArrayList<RentalHomePhoto> photoTransform(
      MultipartFile[] photos,
      String[] photoExplanations,
      int startOrder) throws Exception {
    ArrayList<RentalHomePhoto> files = new ArrayList<>();
    int order = startOrder + 1;  // 사진 순서
    for (MultipartFile file : photos) {
      if (file.getSize() == 0) {
        continue;
      }
      String filename = storageService.upload(this.bucketName, this.uploadDir,
          file); // uuid_file_name
      files.add(
          RentalHomePhoto.builder()
              .uuidPhotoName(filename)
              .oriPhotoName(file.getOriginalFilename()) // ori_file_name
              .photoOrder(order)
              .build());
      order++;
    }
    for (int i = 0; i < photoExplanations.length; i++) {
      if (photoExplanations[i] != null) {
        files.get(i).setPhotoExplanation(photoExplanations[i]);
      } else {
        files.get(i).setPhotoExplanation("사진설명 기본값");
      }
    }
    return files;
  }

  private List<Theme> themeTransform(
      List<Integer> themeNos,
      List<String> themeNames,
      String type) {
    List<Theme> themes = new ArrayList<>();
    String[] typeArr = type.split(",");

    Theme typeTheme = new Theme();

    typeTheme.setThemeNo(Integer.parseInt(typeArr[0]));
    typeTheme.setThemeName(typeArr[1]);

    themes.add(typeTheme);

    for (int i = 0; i < themeNos.size(); i++) {
      Theme theme = new Theme();
      theme.setThemeNo(themeNos.get(i));
      theme.setThemeName(themeNames.get(i));
      themes.add(theme);
    }

    return themes;
  }

  private List<RentalHomeFacility> facilityTransform(
      List<Integer> facilityCount,
      List<Integer> facilityNos,
      List<String> facilityNames) {
    List<RentalHomeFacility> facilities = new ArrayList<>();

    for (int i = 0; i < facilityCount.size(); i++) {
      RentalHomeFacility rentalHomeFacility = new RentalHomeFacility();
      rentalHomeFacility.setFacilityNo(facilityNos.get(i));
      rentalHomeFacility.setFacilityName(facilityNames.get(i));
      rentalHomeFacility.setFacilityCount(facilityCount.get(i));

      facilities.add(rentalHomeFacility);
    }
    return facilities;
  }


}
