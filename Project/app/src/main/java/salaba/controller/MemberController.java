package salaba.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import salaba.service.BoardService;
import salaba.service.HostService;
import salaba.service.MemberService;
import salaba.service.StorageService;
import salaba.vo.Member;
import salaba.vo.Nation;
import salaba.vo.board.Board;

@RequiredArgsConstructor
@Controller
@RequestMapping("/member")
public class MemberController implements InitializingBean {

  private static final Log log = LogFactory.getLog(MemberController.class);

  private final MemberService memberService;
  private final StorageService storageService;
  private final BoardService boardService;

  private String uploadDir;

  @Value("${ncpbucketname}")
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

    String nickcheck = request.getParameter("nickcheck");
    model.addAttribute("nickcheck", nickcheck);
  }

  @PostMapping("add")
  public String add(Member member, Model model) throws Exception { // 회원가입

    //닉네임 중복체크
    Member check = memberService.checkNickname(member.getNickname());
    if (check != null) {//닉네임이 중복된 데이터가 발생한 경우
      String nickcheck = "Y";
      return "redirect:form?nickcheck=" + nickcheck;
    }

    memberService.add(member);
    return "redirect:/auth/form";
  }

  @GetMapping("myinfo")
  public void myinfo(
      Model model,
      HttpServletRequest request,
      HttpSession session) throws Exception { // 내정보 조회
    //로그인 사용자의 상세정보를 조회
    Member sessionInfo = (Member) session.getAttribute("loginUser");
    Member member = memberService.get(sessionInfo.getNo());

    //국가 리스트를 받아옴
    List<Nation> nationList = memberService.getNation();

    //조회한 결과 model 에 add
    model.addAttribute("member", member);
    model.addAttribute("nationList", nationList);

    //닉네임 중복체크
    String nickcheck = request.getParameter("nickcheck");
    String newNickName = request.getParameter("newNickName");
    if("N".equals(nickcheck)){
      member.setNickname(newNickName);
    }
    model.addAttribute("nickcheck", nickcheck);

  }

  @PostMapping("myinfoUpdate")
  public String myinfoUpdate(Member member, MultipartFile file, HttpSession session) throws Exception { // 내정보 수정

    Member old = memberService.get(member.getNo());

    if (file.getSize() > 0) {
      String filename = storageService.upload(this.bucketName, this.uploadDir, file);
      System.out.println(filename);
      member.setPhoto(filename);
      storageService.delete(this.bucketName, this.uploadDir, old.getPhoto());
    } else {
      member.setPhoto(old.getPhoto());
    }
    memberService.myinfoUpdate(member);

    Member memberSave = memberService.get(member.getNo());
    //회원정보
    session.setAttribute("loginUser", memberSave);

    return "redirect:myinfo";
  }

  @PostMapping("myinfoNickNameCheck")
  public String myinfoNickNameCheck(Member member) throws Exception { // 내정보 수정
      //닉네임 중복체크
      Member check = memberService.checkNickname(member.getNickname());
      if (check != null) {//닉네임이 중복된 데이터가 발생한 경우
        return "redirect:myinfo?nickcheck=Y";
      }else{
        return "redirect:myinfo?nickcheck=N&newNickName="+member.getNickname();
      }
  }

  @GetMapping("delete")
  public String delete(Member member) throws Exception { // 회원 탈퇴
    memberService.delete(member);
    return "redirect:/auth/logout";
  }

  @GetMapping("findEmail")
  public void findEmail(Member member) throws Exception { // 이메일 찾기
  }

  @GetMapping("findPassword")
  public void findPassword(Member member) throws Exception { // 비밀번호 찾기
  }

  @PostMapping("searchEmail")
  public String searchEmail(Member member, Model model) throws Exception { // 이메일 조회
    Member info = memberService.findEmail(member);
    if (info == null) {
      model.addAttribute("findYn", "N");
    } else {
      model.addAttribute("member", info);
      model.addAttribute("findYn", "Y");
    }
    return "/member/findEmailResult";
  }

  @PostMapping("searchPassword")
  public String searchPassword(Member member, Model model) throws Exception { // 비밀번호 조회
    Member info = memberService.findPassword(member);
    if (info == null) {
      model.addAttribute("findYn", "N");
    } else {
      model.addAttribute("member", info);
      model.addAttribute("findYn", "Y");
    }
      return "/member/findPasswordResult.js";
  }

  @PostMapping("changePasswordSave")
  public String changePasswordSave(Member member, Model model) throws Exception { // 비밀번호 변경
    memberService.changePasswordSave(member);
    return "redirect:/auth/form";
  }

  @PostMapping("myInfoChangePasswordSave")
  public String myInfoChangePasswordSave(Member member, Model model) throws Exception { // 비밀번호 변경
    memberService.changePasswordSave(member);
    return "redirect:/member/myinfo";
  }

  @GetMapping("myinfoCheckPassword")
  public void myinfoCheckPassword(Member member, Model model, HttpServletRequest request, HttpSession session) throws Exception {
    String pwcheck = request.getParameter("pwcheck");
    model.addAttribute("pwcheck", pwcheck);
    session.setAttribute("myInfoMenuId", member.getMyInfoMenuId());

  }

  @PostMapping("checkPassword")
  public String checkPassword(Member member, Model model, HttpSession session)
      throws Exception { // 비밀번호 확인
    Member sessionInfo = (Member) session.getAttribute("loginUser");
    member.setNo(sessionInfo.getNo());

    Member info = memberService.myinfoCheckPassword(member);
    if (info == null) {
      String pwcheck = "Y";
      return "redirect:myinfoCheckPassword?pwcheck=" + pwcheck;
    } else {
      return "redirect:myinfo";
    }

  }

  @PostMapping("themeSave")
  public String themeSave(Member member, Model model, HttpSession session)
      throws Exception {

    Member sessionInfo = (Member) session.getAttribute("loginUser");
    member.setNo(sessionInfo.getNo());
    memberService.deletePreference(member);

    for(int i=0;i<member.getMyThemes().size();i++) {
      member.setThemeNo(Integer.parseInt(member.getMyThemes().get(i)));
      memberService.insertPreference(member);

    }
    return "redirect:myinfo";
  }

  // 선호사항 폼
  @GetMapping("mytheme")
  public void mytheme(Member member, Model model, HttpSession session){
    Member sessionInfo = (Member) session.getAttribute("loginUser");

    model.addAttribute("themeList", memberService.themeList(sessionInfo));
  }

  @GetMapping("boardHistory")  // 작성글 내역
  public void BoardHistory(@RequestParam(defaultValue = "1") int pageNo,
      @RequestParam(defaultValue = "10") int pageSize,
      Member member,
      Model model,
      HttpSession session) throws Exception {

    Member loginUser = (Member) session.getAttribute("loginUser");

    if (pageSize < 10 || pageSize > 20) {  // 페이지 설정
      pageSize = 10;
    }

    if (pageNo < 1) {
      pageNo = 1;
    }

    int numOfRecord = boardService.countAllHistory(loginUser.getNo());
    int numOfPage = numOfRecord / pageSize + ((numOfRecord % pageSize) > 0 ? 1 : 0);

    if (pageNo > numOfPage) {
      pageNo = numOfPage;
    }

    List<Board> boardList = boardService.boardHistory(pageNo, pageSize, loginUser.getNo());
    model.addAttribute("list", boardList);

    model.addAttribute("pageNo", pageNo);
    model.addAttribute("pageSize", pageSize);
    model.addAttribute("numOfPage", numOfPage);

    if(member.getMyInfoMenuId() == null || "".equals(member.getMyInfoMenuId())){
      session.setAttribute("myInfoMenuId", "boardHistory");
    }else {
      session.setAttribute("myInfoMenuId", member.getMyInfoMenuId());
    }



  }


  @GetMapping("commentHistory")  // 작성댓글 내역
  public void commentHistory(@RequestParam(defaultValue = "1") int pageNo,
      @RequestParam(defaultValue = "10") int pageSize,
      Model model,
      HttpSession session) throws Exception {

    Member loginUser = (Member) session.getAttribute("loginUser");

    if (pageSize < 10 || pageSize > 20) {  // 페이지 설정
      pageSize = 10;
    }

    if (pageNo < 1) {
      pageNo = 1;
    }

    int numOfRecord = boardService.countAllCommentHistory(loginUser.getNo());
    int numOfPage = numOfRecord / pageSize + ((numOfRecord % pageSize) > 0 ? 1 : 0);

    if (pageNo > numOfPage) {
      pageNo = numOfPage;
    }

    List<Board> commentList = boardService.commentHistory(pageNo, pageSize, loginUser.getNo());


    //commentList = sort(commentList); // 정렬 함수 호출
    model.addAttribute("list", commentList);

    model.addAttribute("pageNo", pageNo);
    model.addAttribute("pageSize", pageSize);
    model.addAttribute("numOfPage", numOfPage);

  }

  @PostMapping("boardStateCheck")  // 작성댓글 내역
  public ResponseEntity<?> boardStateCheck(
      Board board,
      Model model) throws Exception {

    String state = memberService.boardStateCheck(board);
    return ResponseEntity.ok(state);

  }

}
