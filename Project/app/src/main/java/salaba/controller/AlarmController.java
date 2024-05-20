package salaba.controller;

import java.util.List;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import salaba.service.AlarmService;
import salaba.vo.Alarm;
import salaba.vo.ConstVO;
import salaba.vo.Member;

@RequiredArgsConstructor
@RestController
public class AlarmController {
  private static final Log log = LogFactory.getLog(AlarmController.class);
  private final AlarmService alarmService;

  @PostMapping("/alarm/get")
  public ResponseEntity<Object> getAlarm(HttpSession httpSession){
    Member loginUser = (Member) httpSession.getAttribute("loginUser");
    List<Alarm> alarmList = alarmService.selectNotifyHistory(loginUser.getNo());

    return new ResponseEntity<>(alarmList, HttpStatus.OK);
  }

  @PostMapping("/alarm/read")
  public void readAlarm( @RequestParam(value = "notifyNo", required = false) Integer notifyNo){
    alarmService.updateNotifyHistory(ConstVO.state_ok, notifyNo);
  }
}
