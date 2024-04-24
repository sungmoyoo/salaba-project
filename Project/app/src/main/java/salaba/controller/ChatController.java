package salaba.controller;


import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import salaba.service.HostService;
import salaba.service.StorageService;
import salaba.vo.Member;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {

  private final HostService hostService;
  private final StorageService storageService;

  //채팅방 목록 조회
  @GetMapping("rooms")
  public void rooms(Model model, HttpSession session) {
    Member loginUser = (Member) session.getAttribute("loginUser");
    model.addAttribute("list", hostService.reservationList(1));
  }

  @PostMapping("/sendLoginUser")
  @ResponseBody
  public void sendLoginUser(HttpSession session) {
    Member loginUser = (Member) session.getAttribute("loginUser");
    // Node.js 서버로 데이터 전송
    String url = "http://localhost:3000/receiveData";

    System.out.println("잘받음여");
    // 전송할 데이터 설정
    Map<String, Object> data = new HashMap<>();
    data.put("loginUser", loginUser.getName());

    // RestTemplate을 사용하여 Node.js 서버로 데이터 전송
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.postForObject(url, data, Void.class);
  }
}