package salaba.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import salaba.service.BoardService;
import salaba.service.MemberService;
import salaba.service.StorageService;
import salaba.vo.ConstVO;
import salaba.vo.Member;
import salaba.vo.Nation;
import salaba.vo.board.Board;

@RequiredArgsConstructor
@Controller
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

  @GetMapping("/member/myPage")
  public void myinfo(
      Model model,
      HttpServletRequest request,
      HttpSession session) throws Exception { // 내정보 조회

  }

  @PostMapping("myinfoUpdate")
  public String myinfoUpdate(Member member, MultipartFile file, HttpSession session) throws Exception { // 내정보 수정

    Member old = memberService.selectUserInfoForLogin(member.getNo());

    if (file.getSize() > 0) {
      String filename = storageService.upload(this.bucketName, this.uploadDir, file);
      System.out.println(filename);
      member.setPhoto(filename);
      storageService.delete(this.bucketName, this.uploadDir, old.getPhoto());
    } else {
      member.setPhoto(old.getPhoto());
    }
    memberService.myinfoUpdate(member);

    Member memberSave = memberService.selectUserInfoForLogin(member.getNo());
    //회원정보
    session.setAttribute("loginUser", memberSave);

    return "redirect:myinfo";
  }

  @GetMapping("delete")
  public String delete(Member member) throws Exception { // 회원 탈퇴
    memberService.delete(member);
    return "redirect:/auth/logout";
  }

  @PostMapping("myInfoChangePasswordSave")
  public String myInfoChangePasswordSave(Member member) throws Exception { // 비밀번호 변경
    memberService.changePasswordSave(member);
    return "redirect:/member/myinfo";
  }

  // 선호사항 폼
  @GetMapping("mytheme")
  public void mytheme(Member member, Model model, HttpSession session){
    Member sessionInfo = (Member) session.getAttribute("loginUser");

    model.addAttribute("themeList", memberService.themeList(sessionInfo));
  }

  @GetMapping("boardHistory")  // 작성글 내역
  public void BoardHistory(@RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
      @RequestParam( value = "pageSize" ,defaultValue = "10") int pageSize,
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
//
//    if(member.getMyInfoMenuId() == null || "".equals(member.getMyInfoMenuId())){
//      session.setAttribute("myInfoMenuId", "boardHistory");
//    }else {
//      session.setAttribute("myInfoMenuId", member.getMyInfoMenuId());
//    }
  }

  @GetMapping("commentHistory")  // 작성댓글 내역
  public void commentHistory(@RequestParam( value = "pageNo",defaultValue = "1") int pageNo,
      @RequestParam( value = "pageSize",defaultValue = "10") int pageSize,
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

  // 검색
  @GetMapping("/member/search")
  public String searchBoardHistory(
      @RequestParam("type") String type,
      @RequestParam("keyword") String keyword,
      @RequestParam(defaultValue = "1") int pageNo,
      @RequestParam(defaultValue = "8") int pageSize,
      Model model) {

    List<Board> filteredBoardList;

    // 검색 유형에 따라 적절한 서비스 메서드를 호출하여 필터링된 게시글 목록을 가져옴
    if ("title".equals(type)) {
      filteredBoardList = memberService.searchByTitle(keyword);
    } else if ("content".equals(type)) {
      filteredBoardList = memberService.searchByContent(keyword);
    } else {
      // 유효하지 않은 검색 유형을 처리하는 경우
      filteredBoardList = Collections.emptyList(); // 빈 리스트 반환
      model.addAttribute("message", "검색 결과가 없습니다");
    }

    // 페이징 처리를 위해 검색 결과의 총 개수를 계산
    int numOfRecord = memberService.countFiltered(type, keyword);
    int numOfPage = (numOfRecord / pageSize) + (numOfRecord % pageSize > 0 ? 1 : 0);
    pageNo = Math.max(1, Math.min(pageNo, numOfPage));

    // 필터링된 게시글 목록을 화면에 전달
    model.addAttribute("list", filteredBoardList);
    model.addAttribute("type", type); // 검색 유형을 유지하기 위해 전달
    model.addAttribute("keyword", keyword); // 검색 키워드를 유지하기 위해 전달
    model.addAttribute("pageNo", pageNo);
    model.addAttribute("pageSize", pageSize);
    model.addAttribute("numOfPage", numOfPage);

    return "member/boardHistory"; // 필터링된 게시글 목록을 보여줄 뷰 페이지
  }
}

