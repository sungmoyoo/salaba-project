package salaba.controller;

import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import salaba.service.HostReservationService;
import salaba.vo.HostReservation;

@RequiredArgsConstructor
@Controller
@RequestMapping("/hostReservation")
public class HostReservationController {
  private static final Log log = LogFactory.getLog(HostReservationController.class);
  private final HostReservationService hostReservationService;

  // 예약 내역 리스트
  @GetMapping("list")
  public void list(Model model, int hostNo) throws Exception {
    model.addAttribute("list", hostReservationService.list(hostNo));
  }

  @PostMapping("stateUpdate")
  public String stateUpdate(HostReservation hostReservation) throws Exception {
    hostReservationService.stateUpdate(hostReservation.getState(),
        hostReservation.getReservationNo());
    return "redirect:list?hostNo=" + hostReservation.getHostNo();
  }




}
