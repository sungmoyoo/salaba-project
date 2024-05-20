package salaba.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import salaba.service.MemberService;
import salaba.vo.Member;
import salaba.vo.google.GoogleInfResponse;
import salaba.vo.google.GoogleRequest;
import salaba.vo.google.GoogleResponse;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin("*")
public class SocialLoginController {
  @Value("${google.client.id}")
  private String googleClientId;
  @Value("${google.client.pw}")
  private String googleClientPw;

  @Autowired
  private MemberService memberService;

  @PostMapping("/auth/login/google")
  public void loginUrlGoogle(HttpServletResponse response) throws IOException {
    String reqUrl = "https://accounts.google.com/o/oauth2/v2/auth?client_id=" + googleClientId
        + "&redirect_uri=http://localhost:8888/api/v1/oauth2/google&response_type=code&scope=email%20profile%20openid&access_type=offline";
    response.sendRedirect(reqUrl);
  }

  @GetMapping("/api/v1/oauth2/google")
  public void googleLogin(@RequestParam("code") String authCode, HttpServletResponse response) throws IOException {
    String jwtToken = getGoogleJwtToken(authCode);
    GoogleInfResponse userInfo = getUserInfoFromToken(jwtToken);
    Member member = createMemberFromUserInfo(userInfo);
    Member existingMember = memberService.selectEmailForGoogle(member.getEmail());
    ObjectMapper objectMapper = new ObjectMapper(); // JSON 객체 매퍼
    String jsonData;
    if (existingMember == null) {
      jsonData = objectMapper.writeValueAsString(member);
      //창을 닫고 회원가입 페이지로 이동(추가적인 정보필요)
    } else {
      jsonData = objectMapper.writeValueAsString(existingMember);
      //창을 닫고 홈으로 이동
    }

    jsonData = jsonData.replace("\n", "\\n").replace("\r", "\\r").replace("'", "\\'");
    String script = "<script>" +
        "window.opener.postMessage(" + jsonData + ", 'http://localhost:8888');" +
        "window.close();" +
        "</script>";
    response.setCharacterEncoding("UTF-8");
    response.setContentType("text/html");
    response.getWriter().write(script);
  }

  // 구글 OAuth를 통해 토큰 가져오기
  private String getGoogleJwtToken(String authCode) {
    RestTemplate restTemplate = new RestTemplate();
    GoogleRequest googleOAuthRequestParam = GoogleRequest.builder()
        .clientId(googleClientId)
        .clientSecret(googleClientPw)
        .code(authCode)
        .redirectUri("http://localhost:8888/api/v1/oauth2/google")
        .grantType("authorization_code").build();
    ResponseEntity<GoogleResponse> resultEntity = restTemplate.postForEntity("https://oauth2.googleapis.com/token",
        googleOAuthRequestParam, GoogleResponse.class);
    return resultEntity.getBody().getId_token();
  }

  // 토큰을 통해 사용자 정보 가져오기
  private GoogleInfResponse getUserInfoFromToken(String jwtToken) {
    RestTemplate restTemplate = new RestTemplate();
    Map<String, String> map = new HashMap<>();
    map.put("id_token", jwtToken);
    ResponseEntity<GoogleInfResponse> resultEntity = restTemplate.postForEntity("https://oauth2.googleapis.com/tokeninfo",
        map, GoogleInfResponse.class);
    return resultEntity.getBody();
  }

  // 사용자 정보를 바탕으로 회원 객체 생성
  private Member createMemberFromUserInfo(GoogleInfResponse userInfo) {
    Member member = new Member();
    member.setEmail(userInfo.getEmail());
    member.setName(userInfo.getName());
    // 필요한 경우 추가 정보 설정
    return member;
  }
}