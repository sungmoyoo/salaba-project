package salaba.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import salaba.service.BoardService;
import salaba.service.CommentService;
import salaba.service.MemberService;
import salaba.service.ReplyService;
import salaba.service.StorageService;
import salaba.util.Translator;
import salaba.vo.board.BoardFile;
import salaba.vo.board.Board;
import salaba.vo.board.Comment;
import salaba.vo.Member;
import salaba.vo.Region;
import salaba.vo.board.Reply;

@RequiredArgsConstructor
@Controller
@SessionAttributes("boardFiles") // boardFiles 로 저장되는 객체는 세션에 보관한다.
public class BoardController {  // 게시판, 댓글, 답글 컨트롤러

  private static final Log log = LogFactory.getLog(BoardController.class);
  private final BoardService boardService; // 게시판 서비스
  private final StorageService storageService; // 스토리지 서비스
  private final CommentService commentService; // 댓글 서비스
  private final ReplyService replyService; // 답글 서비스
  private final MemberService memberService;


  @Value("${ncpbucketname}")
  private String bucketName;

  @GetMapping("board/main")
  public void mainBoard(Model model) throws Exception {
    // 각 카테고리별로 최신 공지 2개와 일반 게시글 3개를 로드
    List<Board> reviewBoardList = mainBoardContents(0, 1, 4);  // 후기 게시판에는 공지사항 없음
    List<Board> infoBoardList = mainBoardContents(1, 1, 3);    // 정보공유 게시판
    List<Board> communityBoardList = mainBoardContents(2, 1, 3); // 자유 게시판

    model.addAttribute("review", reviewBoardList);
    model.addAttribute("information", infoBoardList);
    model.addAttribute("community", communityBoardList);
  }

  // 메인화면에서 공지와 일반 게시글 분리 후 합체
  private List<Board> mainBoardContents(int categoryNo, int pageNo, int normalPostCount) {
    List<Board> combinedList = new ArrayList<>();

    // 공지사항 로드, 후기 게시판 제외
    if (categoryNo != 0) {
      List<Board> announcements = boardService.findAnnouncements(categoryNo,  2);
      System.out.println("값 넣음");
      combinedList.addAll(announcements);
      System.out.println("넣었음");

    }

    // 일반 게시글 로드
    List<Board> normalPosts = boardService.listBoard(categoryNo, pageNo, normalPostCount,0);
    combinedList.addAll(normalPosts);

    return combinedList;
  }

  @GetMapping("board/form") // 게시글 폼
  public void form(int categoryNo, Model model) throws Exception {
    model.addAttribute("boardName", categoryNo == 0 ? "후기게시판"
        : (categoryNo == 1 ? "정보공유게시판" : "자유게시판")); // 카테고리 별 분류 - 0 : 후기 / 1 : 정보공유 / 2 : 자유
    model.addAttribute("categoryNo", categoryNo);
  }

  @PostMapping("board/add")
  public String addBoard(  // 게시글 작성
      Board board,
      @RequestParam("categoryNo") int categoryNo,
      @RequestParam("scopeNo") int scopeNo, // 공개범위
      @RequestParam(value = "regionNo", defaultValue = "0") int regionNo,// 지역
      @RequestParam(value = "headNo", defaultValue = "0") int headNo, // 말머리
      HttpSession session, SessionStatus sessionStatus) throws Exception {

    log.debug(String.format("addBoard 진입11"));
    Member loginUser = (Member) session.getAttribute("loginUser"); // 로그인
    if (loginUser == null) { // 로그인 정보 요청
      throw new Exception("로그인하시기 바랍니다!");
    }
    log.debug(String.format("addBoard 진입22"));
    // 공통으로 넣어줘야하는 값
    board.setWriter(loginUser); // 작성자 로그인 설정
    board.setScopeNo(scopeNo); // 공개범위 설정
    log.debug(String.format("addBoard 진입33"));
    if (categoryNo == 0) {
      board.setRegion(Region.builder().regionNo(regionNo).build()); // 지역 설정
      board.setHeadNo(0);
    } else {
      board.setHeadNo(headNo); // 말머리 설정
    }
  log.debug(String.format("addBoard 진입44"));
    // 게시글 등록할 때 삽입한 이미지 목록을 세션에서 가져온다.
    List<BoardFile> boardFiles = (List<BoardFile>) session.getAttribute("boardFiles");
    log.debug("1234" + boardFiles);

    if (boardFiles != null) {
      for (int i = boardFiles.size() - 1; i >= 0; i--) {
        BoardFile boardFile = boardFiles.get(i);
        if (board.getContent().indexOf(boardFile.getUuidFileName()) == -1) {
          // Object Storage에 업로드 한 파일 중에서 게시글 콘텐트에 포함되지 않은 것은 삭제한다.
          storageService.delete(this.bucketName, Translator.getNcpFolderPath(categoryNo),
              boardFile.getUuidFileName());
          log.debug(String.format("%s 파일 삭제!", boardFile.getUuidFileName()));
          boardFiles.remove(i);
        }
      }
      if (boardFiles.size() > 0) {
        board.setFileList(boardFiles);
      }
    }

    boardService.addBoard(board);

    // 게시글을 등록하는 과정에서 세션에 임시 보관한 첨부파일 목록 정보를 제거한다.
    sessionStatus.setComplete();

    return "redirect:list?categoryNo=" + board.getCategoryNo();
  }


  @GetMapping("board/list")  // 게시글 목록
  public void listBoard(
      @RequestParam int categoryNo,
      @RequestParam(defaultValue = "1") int pageNo,
      @RequestParam(defaultValue = "8") int pageSize,
      @RequestParam(defaultValue = "0") int headNo,
      Model model) throws Exception {

    int numOfPage = 1;
    // 페이징 처리
    if (categoryNo != 0) {  // 페이징 처리를 후기게시판에서 제외
      pageSize = Math.max(3, Math.min(pageSize, 20));
      pageNo = Math.max(1, pageNo);

      int numOfRecord = boardService.countAll(categoryNo);
      numOfPage = (numOfRecord / pageSize ) + (numOfRecord % pageSize > 0 ? 1 : 0);
      pageNo = Math.min(pageNo, numOfPage);

      log.debug(String.format("로그"));
      log.debug(String.format("pageSize : %s", pageSize));
      log.debug(String.format("pageNo : %s", (pageNo-1) * pageSize));
      log.debug(String.format("headNo : %s", headNo));
      log.debug(String.format("numOfRecord : %s", numOfRecord));
      log.debug(String.format("numOfPage : %s", numOfPage));
    }

    List<Board> combinedList = new ArrayList<>();
    if (categoryNo != 0) {
      // 후기게시판이 아닐 때만 공지사항을 가져오기
      List<Board> announcements = boardService.findAnnouncements(categoryNo, 10);
      combinedList.addAll(announcements);
    }

    // 일반 게시글 로드
    List<Board> boardList = boardService.listBoard(categoryNo, ((pageNo-1) * pageSize), pageSize, headNo);  // 공지사항 제외
    log.debug("xxxxxxxx"+boardList);
    combinedList.addAll(boardList);

    model.addAttribute("boardName", categoryNo == 0 ? "후기게시판" : (categoryNo == 1 ? "정보공유게시판" : "자유게시판"));
    model.addAttribute("categoryNo", categoryNo);
    model.addAttribute("headNo", headNo);
    model.addAttribute("list", combinedList);
    model.addAttribute("pageNo", pageNo);
    model.addAttribute("pageSize", pageSize);
    model.addAttribute("numOfPage", numOfPage);
  }

  @GetMapping("board/view")  // 게시글 조회
  public String viewBoard(
      @RequestParam("categoryNo") int categoryNo, // 카테고리 번호
      @RequestParam("boardNo") int boardNo, // 게시글 번호
      @RequestParam(value = "commentNo", required = false) Integer commentNo,
      // 댓글번호 - 필수 x, 답글 찾을 때 필요
      Model model,
      HttpSession session) throws Exception {
    Board board = boardService.getBoard(boardNo, categoryNo);
    //log.debug("abcdefg"+ board);
    if (board == null) {
      throw new Exception("번호가 유효하지 않습니다.");
    }

    //댓글을 가져온다.
    List<Comment> commentList = commentService.list(boardNo);
    if (commentList != null) {
      //댓글이 있다면 댓글을 게시글에 저장한다.
      board.setCommentList(commentList);
      if (commentList.size() > 0) {
        for (Comment comment : board.getCommentList()) {
          //답글을 가져온다.
          List<Reply> replyList = replyService.list(comment.getCommentNo());
          //답글이 있다면 답글을 댓글에 저장한다.
          if (replyList != null) {
            comment.setReplyList(replyList);
          }
        }
      }

    }

    // 조회수 증가 (게시글 존재 및 접근 가능 확인 후)
    boardService.increaseViewCount(boardNo);

    Member loginUser = (Member) session.getAttribute("loginUser"); // 로그인

    // 공개 범위에 따라 접근 제어
    switch (board.getScopeNo()) {
      case 2: // 작성자만
          if (loginUser != null && board.getWriter().getNo() != loginUser.getNo()) {
            model.addAttribute("title", "비공개 게시글입니다.");
          return "block";
        }
      case 1: // 로그인한 회원만
        if (loginUser == null) {
          model.addAttribute("title", "회원만 열람이 가능한 게시물입니다.");
          return "block";
        }
      case 0: // 전체 공개
        break;
    }
    model.addAttribute("categoryNo", categoryNo); // 카테고리 별 분류
    model.addAttribute("board", board); // 게시판
    model.addAttribute("boardName", categoryNo == 0 ? "후기게시판"
        : (categoryNo == 1 ? "정보공유게시판" : "자유게시판")); // 0: 후기 게시판 - 1 : 정보공유게시판
    model.addAttribute("loginUser", session.getAttribute("loginUser"));

    return "board/view";
  }


  @PostMapping("board/update")
  public String updateBoard( // 게시글 수정
      Board board, HttpSession session, SessionStatus sessionStatus) throws Exception {

    Member loginUser = (Member) session.getAttribute("loginUser");
    if (loginUser == null) {
      throw new Exception("로그인하시기 바랍니다!");
    }

    Board old = boardService.getBoard(board.getBoardNo(), board.getCategoryNo());
    if (old == null) {
      throw new Exception("번호가 유효하지 않습니다.");
    } else if (old.getWriter().getNo() != loginUser.getNo()) {
      throw new Exception("권한이 없습니다.");
    }

    // 게시글 변경할 때 삽입한 이미지 목록을 세션에서 가져온다.
    List<BoardFile> boardFiles = (List<BoardFile>) session.getAttribute("boardFiles");
    if (boardFiles == null) {
      boardFiles = new ArrayList<>();
    }

    if (old.getFileList().size() > 0 && old.getFileList().get(0).getFileNo() != 0) {
      // 기존 게시글에 등록된 이미지 목록과 합친다.
      boardFiles.addAll(old.getFileList());
    }

    if (boardFiles != null) {
      for (int i = boardFiles.size() - 1; i >= 0; i--) {
        BoardFile boardFile = boardFiles.get(i);
        if (board.getContent().indexOf(boardFile.getUuidFileName()) == -1) {
          // Object Storage에 업로드 한 파일 중에서 게시글 콘텐트에 포함되지 않은 것은 삭제한다.
          storageService.delete(this.bucketName, Translator.getNcpFolderPath(old.getCategoryNo()),
              boardFile.getUuidFileName());
          log.debug(String.format("%s 파일 삭제!", boardFile.getUuidFileName()));
          boardFiles.remove(i);
        }
      }

      if (boardFiles.size() > 0) {
        board.setFileList(boardFiles);
      }
    }

    boardService.updateBoard(board);

    // 게시글을 변경하는 과정에서 세션에 임시 보관한 첨부파일 목록 정보를 제거한다.
    sessionStatus.setComplete();

    return "redirect:list?categoryNo=" + board.getCategoryNo();

  }
  @GetMapping("board/modify") // 수정 폼으로 들어가기
  public void modifyBoard(@RequestParam("boardNo") int boardNo,
      @RequestParam("categoryNo") int categoryNo, Model model) {
    Board board = boardService.getBoard(boardNo, categoryNo);
    model.addAttribute("board", board);
    model.addAttribute("categoryNo", categoryNo);
  }


  @GetMapping("board/delete")  // 게시글 삭제 (상태만 변경)
  public String deleteBoard(int categoryNo, int boardNo, HttpSession session) throws Exception {

    Member loginUser = (Member) session.getAttribute("loginUser");
    if (loginUser == null) {
      throw new Exception("로그인하시기 바랍니다!");
    }

    Board board = boardService.getBoard(boardNo, categoryNo);
    if (board == null) {
      throw new Exception("게시판 번호가 유효하지 않습니다.");
    } else if (board.getWriter().getNo() != loginUser.getNo()) {
      throw new Exception("권한이 없습니다.");
    }

    List<BoardFile> files = boardService.getBoardFiles(boardNo);

    boardService.deleteBoard(boardNo);

    for (BoardFile file : files) {
      storageService.delete(this.bucketName, Translator.getNcpFolderPath(categoryNo),
          file.getUuidFileName());
    }

    return "redirect:list?categoryNo=" + categoryNo;
  }

  @PostMapping("/board/file/upload")
  @ResponseBody
  public Object fileUpload(
      MultipartFile[] files,
      HttpSession session,
      Model model,
      @RequestParam("categoryNo") int categoryNo)
      throws Exception {
    // NCP Object Storage에 저장한 파일의 이미지 이름을 보관할 컬렉션을 준비한다.
    ArrayList<BoardFile> boardFiles = new ArrayList<>();

    // 로그인 여부를 검사한다.
    Member loginUser = (Member) session.getAttribute("loginUser");
    if (loginUser == null) {
      // 로그인 하지 않았으면 빈 목록을 보낸다.
      return boardFiles;
    }

    // 클라이언트가 보낸 멀티파트 파일을 NCP Object Storage에 업로드한다.
    for (MultipartFile file : files) {
      if (file.getSize() == 0) {
        continue;
      }
      String filename = storageService.upload(this.bucketName,
          Translator.getNcpFolderPath(categoryNo), file);

      boardFiles.add(
          BoardFile.builder().uuidFileName(filename).oriFileName(file.getOriginalFilename())
              .build());
    }

    // 업로드한 파일 목록을 세션에 보관한다.
    model.addAttribute("boardFiles", boardFiles);

    // 클라이언트에서 이미지 이름을 가지고 <img> 태그를 생성할 수 있도록
    // 업로드한 파일의 이미지 정보를 보낸다.
    return boardFiles;
  }

  @PostMapping("/board/comment/add") // 댓글 또는 답글 작성
  public ResponseEntity<?> addComment(
      Comment comment,
      HttpSession session) throws Exception {

    Member loginUser = (Member) session.getAttribute("loginUser");
    if (loginUser == null) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied");
    }

    comment.setWriter(loginUser);
    commentService.addComment(comment);
    comment.setCreatedDate(new Date());
    return ResponseEntity.ok(comment);

  }

  @PostMapping("/board/comment/update") // 답글 또는 댓글 수정
  public ResponseEntity<?> updateComment(Comment comment,
      HttpSession session) throws Exception {

    Member loginUser = (Member) session.getAttribute("loginUser");
    if (loginUser == null) {
      throw new Exception("로그인하시기 바랍니다!");
    }

    Comment oldComment = commentService.getBy(comment.getCommentNo());

    if (oldComment.getWriter().getNo() != loginUser.getNo()) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied");
    }

    comment.setWriter(loginUser);
    int result = commentService.updateComment(comment);

    if (result == 1) {
      return ResponseEntity.ok(result);
    } else {
      return ResponseEntity.noContent().build();
    }

  }

  @GetMapping("/board/comment/delete") // 댓글 또는 답글 삭제 - 상태 1로 변경
  public ResponseEntity<?> deleteComment(
      @RequestParam("commentNo") int commentNo,
      HttpSession session)
      throws Exception {

    Member loginUser = (Member) session.getAttribute("loginUser"); // 로그인 요청
    if (loginUser == null) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied");
    }

    Comment oldComment = commentService.getBy(commentNo);

    if (loginUser.getNo() != oldComment.getWriter().getNo()) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied");
    }

    int result = commentService.deleteComment(commentNo);

    if (result == 1) {
      return ResponseEntity.ok(result);
    } else {
      return ResponseEntity.noContent().build();
    }
  }

  @PostMapping("/board/reply/add") // 답글 작성
  public ResponseEntity<?> addReply(Reply reply,
      HttpSession session) throws Exception {
    Member loginUser = (Member) session.getAttribute("loginUser");
    if (loginUser == null) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied");
    }

    try {
      reply.setWriter(loginUser);
      replyService.addReply(reply);
      reply.setCreatedDate(new Date());
      return ResponseEntity.ok(reply);
    } catch (Exception e) {
      return ResponseEntity.internalServerError().build();
    }
  }

  @PostMapping("/board/reply/update") // 답글 수정
  public ResponseEntity<?> updateReply(Reply reply,
      HttpSession session) {

    Member loginUser = (Member) session.getAttribute("loginUser");
    if (loginUser == null) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied");
    }

    Reply oldReply = replyService.getReply(reply.getReplyNo());

    if (oldReply.getWriter().getNo() == loginUser.getNo()) {
      reply.setWriter(loginUser);
      int result = replyService.updateReply(reply);
      if (result == 1) {
        return ResponseEntity.ok(result);
      } else {
        return ResponseEntity.noContent().build();
      }
    } else {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied");
    }
  }
  @GetMapping("/board/reply/delete") //답글 삭제
  public ResponseEntity<?> deleteReply(@RequestParam("replyNo") int replyNo,
      HttpSession session) {
    Member loginUser = (Member) session.getAttribute("loginUser");
    if (loginUser == null) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied");
    }
    Reply reply = replyService.getReply(replyNo);
    if (reply.getWriter().getNo() == loginUser.getNo()) {
      int result = replyService.deleteReply(replyNo);
      if (result == 1) {
        return ResponseEntity.ok(result);
      } else {
        return ResponseEntity.noContent().build();
      }
    } else {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied");
    }
  }

  // 조회수
  @GetMapping("/board/preview")
  public String viewBoard(@PathVariable int boardNo, Model model) {
    Board board = boardService.getBoardNo(boardNo); // 게시글 정보 가져오기
    boardService.increaseViewCount(boardNo); // 조회수 증가

    model.addAttribute("board", board);
    return "board/view";
  }

  // 추천수
  @PostMapping("/board/like")
  @ResponseBody
  public Object likeBoard(
      @RequestParam("boardNo") int boardNo,
      HttpSession session) {
    Member loginUser = (Member) session.getAttribute("loginUser");
    if (loginUser == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인을 해주세요!");
    }

    Map<String,Object> result = new HashMap<>();
    try {
      log.debug(("ffff") + boardNo);
      boardService.increaseLikeCount(boardNo, loginUser.getNo());

      result.put("status", "success");

    } catch (Exception e) {
      log.error("추천 처리 중 오류 발생", e);
      result.put("status", "fail");
    }
    return result;
  }

  @PostMapping("/board/unlike")      // 추천 취소 로직
  @ResponseBody
  public Object removeLike(
      @RequestParam int boardNo,
      HttpSession session) {

    Member loginUser = (Member) session.getAttribute("loginUser");

    Map<String,Object> result = new HashMap<>();
    try {
      boardService.decreaseLikeCount(boardNo, loginUser.getNo());
      result.put("status", "success");
    } catch (Exception e) {
      log.error("추천 처리 중 오류 발생", e);
      result.put("status", "fail");
    }
    return result;
  }

// 검색
@GetMapping("/board/search")
public String searchBoard(
    @RequestParam("categoryNo") int categoryNo, // 카테고리 번호를 요청 파라미터로 받음
    @RequestParam("type") String type,
    @RequestParam("keyword") String keyword,
    Model model) {

  List<Board> filteredBoardList;

  // 검색 유형에 따라 적절한 서비스 메서드를 호출하여 필터링된 게시글 목록을 가져옴
  if ("title".equals(type)) {
    filteredBoardList = boardService.searchByTitle(keyword);
  } else if ("content".equals(type)) {
    filteredBoardList = boardService.searchByContent(keyword);
  } else {
    // 유효하지 않은 검색 유형을 처리하는 경우
    filteredBoardList = Collections.emptyList(); // 빈 리스트 반환
    model.addAttribute("message", "검색 결과가 없습니다");
  }

  // 필터링된 게시글 목록을 화면에 전달
  model.addAttribute("list", filteredBoardList);
  model.addAttribute("categoryNo", categoryNo);
  model.addAttribute("type", type); // 검색 유형을 유지하기 위해 전달
  model.addAttribute("keyword", keyword); // 검색 키워드를 유지하기 위해 전달

  return "board/list"; // 필터링된 게시글 목록을 보여줄 뷰 페이지
  }
}
