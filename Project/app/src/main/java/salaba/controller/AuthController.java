package salaba.controller;

import java.util.HashMap;
import java.util.Map;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import salaba.service.MemberService;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import salaba.vo.ConstVO;
import salaba.vo.Member;

@RequiredArgsConstructor
@RestController
public class AuthController {

  @Data
  private static class loginInfo{ // login 때 사용할 내부 class
    private char state;
    // 휴면 이용자의 경우 name과 email 리턴
    private String memberName;
    private String memberEmail;

  }

  private static final Log log = LogFactory.getLog(AuthController.class);
  private final MemberService memberService;

  @GetMapping("form")
  public void form(@CookieValue(required = false) String email, Model model) {
    model.addAttribute("email", email);
  }

  @PostMapping("/auth/login")
  public loginInfo login(
      @RequestParam String email,
      @RequestParam String password,
      @RequestParam(required = false) String saveEmail,
      HttpServletResponse response,
      HttpSession session) throws Exception {

    if (saveEmail != null) {
      Cookie cookie = new Cookie("email", email);
      cookie.setMaxAge(60 * 60 * 24 * 7);
      response.addCookie(cookie);
    } else {
      Cookie cookie = new Cookie("email", "");
      cookie.setMaxAge(0);
      response.addCookie(cookie);
    }

    Member member = memberService.selectUserInfoForLogin(email, password);
    loginInfo loginInfo = new loginInfo();
    //이메일주소 또는 암호가 맞을 경우
    if (member != null) {
      if(ConstVO.member_state_resign == member.getState()){//회원탈퇴
        loginInfo.setState(ConstVO.member_state_resign); // 회원탈퇴 상태 - 1
      }else if(ConstVO.member_state_blocked == member.getState()) {//제재회원
        loginInfo.setState(ConstVO.member_state_blocked); // 회원제재 상태 - 3
      }else if(ConstVO.member_state_sleep == member.getState()) {//휴먼회원
        loginInfo.setMemberName(member.getName());
        loginInfo.setMemberEmail(member.getEmail());
        loginInfo.setState(ConstVO.member_state_sleep); // 휴면회원 상태 - 4
      }else {//로그인 성공
        /* 세션 설정*/
        session.setAttribute("loginUser", member);
        loginInfo.setState(ConstVO.login_ok); // 로그인 성공 - 0
      }
    }else{//이메일주소 또는 암호가 맞지 않을 경우
      loginInfo.setState(ConstVO.login_fail); // 로그인 실패 상태 - 9
    }
    return loginInfo;
  }

  @PostMapping("/auth/logout")
  public void logout(HttpSession session) throws Exception {
    session.invalidate();
  }

  // 회원가입관련 메서드
  @PostMapping("/member/addMember")
  public ResponseEntity<Object> add(@RequestBody Member member) throws Exception { // 회원가입

    int result = memberService.addMember(member); // 리턴값 = 성공 - 1 , 실패 - 0

    return new ResponseEntity<>(result, HttpStatus.OK);
  }
  @PostMapping("/member/checkNickName")
  public ResponseEntity<Object> checkNickName( @RequestParam String nickName ) throws Exception{
    int result = memberService.checkNickname(nickName);
    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  @PostMapping("/member/checkEmail")
  public ResponseEntity<Object> checkEmail(@RequestParam String email) throws Exception{
    int result = memberService.checkEmail(email);
    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  @PostMapping("/member/searchEmail")
  public ResponseEntity<Object> searchEmail(@RequestBody Member member) throws Exception { // 이메일 조회
    Member info = memberService.findEmail(member);
    Map<String, Object> data = new HashMap<>();
    if (info == null) {
      data.put("state", ConstVO.state_no); // 조회 결과 없는 경우 state - 0
    } else {
      data.put("state", ConstVO.state_ok); // 조회 결과가 있는 경우 state - 1
      data.put("email", info.getEmail());
      data.put("name", info.getName());
    }
    return new ResponseEntity<>(data, HttpStatus.OK);
  }

  @PostMapping("/member/searchPassword")
  public ResponseEntity<Object> searchPassword(@RequestBody Member member) throws Exception { // 비밀번호 조회
    Member info = memberService.findPassword(member);
    Map<String, Object> data = new HashMap<>();
    if (info == null) {
      data.put("state", ConstVO.state_no); // 조회 결과 없는 경우 state - 0
    } else {
      data.put("state", ConstVO.state_ok); // 조회 결과가 있는 경우 state - 1
      data.put("memberNo" ,info.getNo());
    }
    return new ResponseEntity<>(data, HttpStatus.OK);
  }

  @PostMapping("/member/changePassword")
  public ResponseEntity<String> changePasswordSave(@RequestBody Member member) throws Exception { // 비밀번호 변경
    memberService.changePasswordSave(member);
    return ResponseEntity.ok("비밀번호 변경 완료");
  }

  @PostMapping("/member/checkPassword")
  public ResponseEntity<Object> checkPassword(@RequestBody Member member){
    int result = memberService.checkPassword(member.getNo(), member.getPassword());
    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  // 회원탈퇴
  @PostMapping("/member/withdrawal")
  public ResponseEntity<Object> updateMemberWithdrawal(@RequestParam int memberNo,
      HttpSession session) throws Exception {
    int result = memberService.updateMemberWithdrawal(memberNo);
    if(result == 1){
      session.invalidate();
    }
    return new ResponseEntity<>(result, HttpStatus.OK);
  }

}
