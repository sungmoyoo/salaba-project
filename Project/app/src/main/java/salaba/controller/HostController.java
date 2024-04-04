package salaba.controller;

import java.util.ArrayList;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.Mapping;
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
import salaba.vo.rentalHome.RentalHomeTheme;

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

  @GetMapping("rentalHomeForm")
  public void rentalHomeForm() {
  }

  @Transactional
  @PostMapping("rentalHomeAdd") // 숙소 인서트
  public String rentalHomeAdd(
      RentalHome rentalHome,
      MultipartFile[] photos,
      Model model,
      HttpSession session) throws Exception {

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

    model.addAttribute("themeList", hostService.themeList());
    session.setAttribute("rentalHome", rentalHome);

    return "host/rentalHomeThemeForm";
  }

  @GetMapping("rentalHomeThemeForm")
  public void rentalHomeThemeForm(Model model, HttpSession session) {
    RentalHome rentalHome = (RentalHome) session.getAttribute("rentalHome");
  }


  @PostMapping("rentalHomeThemeAdd")
  public String rentalHomeThemeAdd(RentalHomeTheme rentalHomeTheme) {
    return null;
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
