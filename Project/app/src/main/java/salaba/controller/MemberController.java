package salaba.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import salaba.service.MemberService;
import salaba.service.StorageService;
import salaba.vo.Member;
import salaba.vo.Nation;

@RequiredArgsConstructor
@Controller
@RequestMapping("/member")
public class MemberController implements InitializingBean {

  private static final Log log = LogFactory.getLog(MemberController.class);

  private final MemberService memberService;
  private final StorageService storageService;
  private String uploadDir;

  @Value("${ncp.bucketname}")
  private String bucketName;

  @Override
  public void afterPropertiesSet() throws Exception {
    this.uploadDir = "member/";

    log.debug(String.format("uploadDir: %s", this.uploadDir));
    log.debug(String.format("bucketname: %s", this.bucketName));
  }

  @GetMapping("form")
  public void form(
      Model model,
      HttpServletRequest request) throws Exception {

    String nickNameChkYn = request.getParameter("nickNameChkYn");
    model.addAttribute("nickNameChkYn", nickNameChkYn);
  }

  @PostMapping("add")
  public String add(Member member, Model model) throws Exception { // 회원가입

    //닉네임 중복체크
    Member check = memberService.checkNickname(member.getNickname());
    if(check != null){//닉네임이 중복된 데이터가 발생한 경우
      String nickNameChkYn = "Y";
      return "redirect:form?nickNameChkYn="+nickNameChkYn;
    }

    memberService.add(member);
    return "redirect:/auth/form";
  }

  @GetMapping("myinfo")
  public void myinfo(
      Model model,
      HttpServletRequest request,
      HttpSession session) throws Exception { // 내정보 조회
    //db에서 로그인 사용자의 상세정보를 조회
    Member sessionInfo = (Member)session.getAttribute("loginUser");
    Member member = memberService.get(sessionInfo.getNo());
    List<Nation> nationList = memberService.getNation();

    String nickNameChkYn = request.getParameter("nickNameChkYn");

    //조회한 결과 model 에 add
    model.addAttribute("member", member);
    model.addAttribute("nationList", nationList);
    model.addAttribute("nickNameChkYn", nickNameChkYn);
  }

  @PostMapping("myinfoUpdate")
  public String myinfoUpdate(Member member, MultipartFile file) throws Exception { // 내정보 수정

    String oldNickname = member.getOldNickname();//수정전 닉네임
    String nickname = member.getNickname();//수정후 닉네임
    //닉네임이 수정되었을 경우에만 중복체크를 한다.
    if(!oldNickname.equals(nickname)){
      //닉네임 중복체크
      Member check = memberService.checkNickname(member.getNickname());
      if(check != null){//닉네임이 중복된 데이터가 발생한 경우
        String nickNameChkYn = "Y";
        return "redirect:myinfo?nickNameChkYn="+nickNameChkYn;
      }
    }

    Member old = memberService.get(member.getNo());

    if (file.getSize() > 0) {
      String filename = storageService.upload(this.bucketName, this.uploadDir, file);
      member.setPhoto(filename);
      storageService.delete(this.bucketName, this.uploadDir, old.getPhoto());
    } else {
      member.setPhoto(old.getPhoto());
    }
    memberService.myinfoUpdate(member);
    return "redirect:myinfo";
  }

  @GetMapping("delete")
  public String delete(Member member) throws Exception {
    memberService.delete(member);
    return "redirect:/auth/logout";
  }

  @GetMapping("findEmail")
  public void findEmail(Member member) throws Exception { // 이메일 찾기
  }

  @GetMapping("findPw")
  public void findPw(Member member) throws Exception { // 비밀번호 찾기
  }

  @PostMapping("schEmail")
  public String schEmail(Member member, Model model) throws Exception { // 이메일 조회
    Member info = memberService.findEmail(member);
    if(info == null){
      return "/member/findEmailFail";
    }else{
      model.addAttribute("member", info);
      return "/member/findEmailSuc";
    }
  }

  @PostMapping("schPw")
  public String schPw(Member member, Model model) throws Exception { // 비밀번호 조회
    Member info = memberService.findPw(member);
    if(info == null){
      return "/member/findPwFail";
    }else{
      model.addAttribute("member", info);
      return "/member/findPwSuc";
    }
  }

  @PostMapping("chgPwSave")
  public String chgPwSave(Member member, Model model) throws Exception { // 비밀번호 변경
    memberService.chgPwSave(member);
    return "redirect:/auth/form";
  }

}
