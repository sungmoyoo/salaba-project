package salaba.controller;

import salaba.service.MemberService;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import salaba.vo.Member;

@RequiredArgsConstructor
@Controller
@RequestMapping("/auth")
public class AuthController {

  private static final Log log = LogFactory.getLog(AuthController.class);
  private final MemberService memberService;

  @GetMapping("form")
  public void form(@CookieValue(required = false) String email, Model model) {
    model.addAttribute("email", email);
  }

  @PostMapping("login")
  public String login(
      String email,
      String password,
      String saveEmail,
      Model model,
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
    //이메일주소 또는 암호가 맞을 경우
    if (member != null) {
      if('1' == member.getState()){//회원탈퇴
        model.addAttribute("memberState",member.getState());
      }else if('3' == member.getState()) {//제재회원
        model.addAttribute("memberState",member.getState());
      }else if('4' == member.getState()) {//휴먼회원
        model.addAttribute("memberName",member.getName());
        model.addAttribute("memberEmail",member.getEmail());
        model.addAttribute("memberState",member.getState());
      }else {//로그인 성공
        session.setAttribute("loginUser", member);
      }
    }else{//이메일주소 또는 암호가 맞지 않을 경우
      model.addAttribute("memberState","99");
    }

    return "auth/login";
  }

  @GetMapping("logout")
  public String logout(HttpSession session) throws Exception {
    session.invalidate();
    return "auth/logout";
  }
}
