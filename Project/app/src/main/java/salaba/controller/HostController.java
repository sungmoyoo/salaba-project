package salaba.controller;

import java.util.ArrayList;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import salaba.vo.rentalHome.RentalHomePhoto;

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
  public void rentalHomeForm(Model model, int hostNo) {
    model.addAttribute("hostNo", hostNo);
  }

  @PostMapping("rentalHomeAdd") // 숙소 인서트
  public String rentalHomeAdd(
      RentalHome rentalHome,
      MultipartFile[] rentalHomePhotos,
      HttpSession session) throws Exception {

    ArrayList<RentalHomePhoto> files = new ArrayList<>();
    int order = 1;
      for (MultipartFile file : rentalHomePhotos) {
        if (file.getSize() == 0) {
          continue;
        }
        String filename = storageService.upload(this.bucketName, this.uploadDir, file);
        files.add(
            RentalHomePhoto.builder()
            .uuidPhotoName(filename)
                .oriPhotoName(file.getOriginalFilename())
                .photoOrder(order)
                .build());
        order++;
      }

    if (files.size() > 0) {
      rentalHome.setRentalHomePhotos(files);
    }

    hostService.rentalHomeAdd(rentalHome);

    return "redirect:reservationList?hostNo=" + rentalHome.getMemberNo();
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
