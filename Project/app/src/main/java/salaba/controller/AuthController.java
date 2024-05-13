package salaba.controller;

import java.util.List;
import lombok.Data;
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
@RequestMapping("/auth")
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

  @PostMapping(value = "login")
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

    Member member = memberService.get(email, password);
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
        //선호사항
        List<Member> preferenceList = memberService.mythemeList(member);
        session.setAttribute("preference", preferenceList);
        //포인트
        String memberPoint = memberService.getMemberPoint(member);
        session.setAttribute("memberPoint", memberPoint);
        log.debug(String.format("로그 member : %s", member));
        //등급
        Member memberGrade = memberService.getGrade(member);
        session.setAttribute("memberGradeNo", memberGrade.getGrade().getGradeNo());
        session.setAttribute("memberGradeName", memberGrade.getGrade().getGradeName());
        //회원정보
        session.setAttribute("loginUser", member);
        loginInfo.setState(ConstVO.login_ok); // 로그인 성공 - 0
      }
    }else{//이메일주소 또는 암호가 맞지 않을 경우
      loginInfo.setState(ConstVO.login_fail); // 로그인 실패 상태 - 9
    }
    return loginInfo;
  }

  @GetMapping("logout")
  public String logout(HttpSession session) throws Exception {
    session.invalidate();
    return "auth/logout";
  }
}
