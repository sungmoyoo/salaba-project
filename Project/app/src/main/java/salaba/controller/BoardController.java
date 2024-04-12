package salaba.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import salaba.service.BoardReportService;
import salaba.service.BoardService;
import salaba.service.CommentService;
import salaba.service.ReplyService;
import salaba.service.StorageService;
import salaba.vo.BoardFile;
import salaba.vo.Board;
import salaba.vo.BoardReport;
import salaba.vo.Comment;
import salaba.vo.Member;
import salaba.vo.Reply;

@RequiredArgsConstructor
@Controller
@RequestMapping("/board")
@SessionAttributes("boardFiles") // boardFiles 로 저장되는 객체는 세션에 보관한다.
public class BoardController {  // 게시판, 댓글, 답글, 신고 컨트롤러

  private static final Log log = LogFactory.getLog(BoardController.class);
  private final BoardService boardService; // 게시판 서비스
  private final StorageService storageService; // 스토리지 서비스
  private final CommentService commentService; // 댓글 서비스
  private final ReplyService replyService; // 답글 서비스
  private final BoardReportService boardReportService; // 신고 서비스
  private String uploadDir = "board/";

  @Value("${ncp.ss.bucketname}")
  private String bucketName;

  @GetMapping("main") // 게시글 전체 메인화면
  public void mainBoard(
      @RequestParam(defaultValue = "1") int pageNo,
      @RequestParam(defaultValue = "5") int pageSize,
      HttpSession session,
      Model model) throws Exception {

    Member loginuser = (Member) session.getAttribute("loginUser");
    model.addAttribute("loginUser", loginuser);

    if (pageSize < 3 || pageSize > 20) {  // 페이지 설정
      pageSize = 5;
    }

    if (pageNo < 1) {
      pageNo = 1;
    }

    int totalNumOfRecord = 0;
    List<Board> allBoards = new ArrayList<>();

    // 전체 카테고리의 게시글을 가져옴
    for (int i = 0; i < 3; i++) {
      int numOfRecord = boardService.countAll(i);
      totalNumOfRecord += numOfRecord;
      allBoards.addAll(boardService.listBoard(i, pageNo, pageSize));
    }

    int numOfPage = totalNumOfRecord / pageSize + ((totalNumOfRecord % pageSize) > 0 ? 1 : 0);

    if (pageNo > numOfPage) {
      pageNo = numOfPage;
    }

    model.addAttribute("boardName", "전체 게시판");
    model.addAttribute("main", allBoards);
    model.addAttribute("pageNo", pageNo);
    model.addAttribute("pageSize", pageSize);
    model.addAttribute("numOfPage", numOfPage);
  }

    @GetMapping("form") // 게시글 폼
  public void form(int categoryNo, Model model) throws Exception {
    model.addAttribute("boardName", categoryNo == 0 ? "후기게시판" :
        (categoryNo == 1 ? "정보공유게시판" : "자유게시판")); // 카테고리 별 분류 - 0 : 후기 / 1 : 정보공유 / 2 : 자유
    model.addAttribute("categoryNo", categoryNo);
  }

  @PostMapping("addBoard")
  public String addBoard(  // 게시글 작성
      Board board,
      @RequestParam("scopeNo") int scopeNo, // 공개범위
      @RequestParam("headNo") int headNo, // 말머리
      HttpSession session,
      SessionStatus sessionStatus) throws Exception {

    Member loginUser = (Member) session.getAttribute("loginUser"); // 로그인
    if (loginUser == null) { // 로그인 정보 요청
      throw new Exception("로그인하시기 바랍니다!");
    }
    board.setWriter(loginUser); // 작성자 로그인 설정
    board.setScopeNo(scopeNo); // 공개범위 설정
    board.setHeadNo(headNo); // 말머리 설정

    // 게시글 등록 시 삽입한 이미지 목록을 세션에서 가져온다.
    List<BoardFile> boardFiles = (List<BoardFile>) session.getAttribute("boardFiles");

      for (int i = boardFiles.size() - 1; i >= 0; i--) {
        BoardFile boardFile = boardFiles.get(i);
        if (board.getContent().indexOf(boardFile.getUuidFileName()) == -1) {
          // Object Storage에 업로드 한 파일 중에서 게시글 콘텐트에 포함되지 않은 것은 삭제한다.
          storageService.delete(this.bucketName, this.uploadDir, boardFile.getUuidFileName());
          log.debug(String.format("%s 파일 삭제!", boardFile.getUuidFileName()));
          boardFiles.remove(i);
        }
      }
    if (boardFiles.size() > 0) { // 파일이 한 개 이상일 경우 저장
      board.setFileList(boardFiles);
    }

    boardService.addBoard(board);

    // 게시글을 등록하는 과정에서 세션에 임시 보관한 첨부파일 목록 정보를 제거한다.
    sessionStatus.setComplete();

    return "redirect:list?categoryNo=" + board.getCategoryNo();
  }

  @GetMapping("list")  // 게시글 목록
  public void listBoard(
      @RequestParam int categoryNo,
      @RequestParam(defaultValue = "1") int pageNo,
      @RequestParam(defaultValue = "3") int pageSize,
      HttpSession session,
      Model model) throws Exception {
    Member loginuser = (Member) session.getAttribute("loginUser");
    model.addAttribute("loginUser", loginuser);

    if (pageSize < 3 || pageSize > 20) {  // 페이지 설정
      pageSize = 3;
    }

    if (pageNo < 1) {
      pageNo = 1;
    }

    int numOfRecord = boardService.countAll(categoryNo);
    int numOfPage = numOfRecord / pageSize + ((numOfRecord % pageSize) > 0 ? 1 : 0);

    if (pageNo > numOfPage) {
      pageNo = numOfPage;
    }

    model.addAttribute("boardName", categoryNo == 0 ? "후기게시판" :
        (categoryNo == 1 ? "정보공유게시판" : "자유게시판"));
    model.addAttribute("categoryNo", categoryNo);
    model.addAttribute("list", boardService.listBoard(categoryNo,pageNo, pageSize));
    model.addAttribute("pageNo", pageNo);
    model.addAttribute("pageSize", pageSize);
    model.addAttribute("numOfPage", numOfPage);
  }

  @GetMapping("view")  // 게시글 조회
  public void viewBoard(
      @RequestParam("categoryNo") int categoryNo, // 카테고리 번호
      @RequestParam("boardNo") int boardNo, // 게시글 번호
      @RequestParam(value="commentNo", required = false) Integer commentNo, // 댓글번호 - 필수x, 답글 찾을 때 필요
      Model model) throws Exception {

    Board board = boardService.getBoard(boardNo);
    log.debug("abcdefg"+ board);
    if (board == null) {
      throw new Exception("번호가 유효하지 않습니다.");
    }

    model.addAttribute("categoryNo", categoryNo); // 카테고리 별 분류
    model.addAttribute("board", board); // 게시판
    model.addAttribute("boardName", categoryNo == 0 ? "후기게시판" :
        (categoryNo == 1 ? "정보공유게시판" : "자유게시판")); // 0: 후기 게시판 - 1 : 정보공유게시판

    if (commentNo != null) { // 댓글이 있을 시 댓글과 답글을 함께 조회
      Comment comment = commentService.getComment(commentNo); // 댓글 번호로 답글 찾기
      model.addAttribute("commentList", commentService.list(board.getBoardNo()));  // 댓글 조회
      model.addAttribute("replyList", replyService.list(comment.getCommentNo()));  // 답글 조회
    }
  }


  @PostMapping("updateBoard")
  public String updateBoard( // 게시글 수정
      Board board,
      HttpSession session,
      SessionStatus sessionStatus) throws Exception {

    Member loginUser = (Member) session.getAttribute("loginUser");
    if (loginUser == null) {
      throw new Exception("로그인하시기 바랍니다!");
    }

    Board old = boardService.getBoard(board.getBoardNo());
    if (old == null) {
      throw new Exception("번호가 유효하지 않습니다.");
    }
    else if (old.getWriter().getNo() != loginUser.getNo()) {
      throw new Exception("권한이 없습니다.");
    }

    // 게시글 변경할 때 삽입한 이미지 목록을 세션에서 가져온다.
    List<BoardFile> boardFiles = (List<BoardFile>) session.getAttribute("boardFiles");

    if (old.getFileList().size() > 0) {
      // 기존 게시글에 등록된 이미지 목록과 합친다.
      boardFiles.addAll(old.getFileList());
    }

    for (int i = boardFiles.size() - 1; i >= 0; i--) {
      BoardFile boardFile = boardFiles.get(i);
      if (board.getContent().indexOf(boardFile.getUuidFileName()) == -1) {
        // Object Storage에 업로드 한 파일 중에서 게시글 콘텐트에 포함되지 않은 것은 삭제한다.
        storageService.delete(this.bucketName, this.uploadDir, boardFile.getUuidFileName());
        log.debug(String.format("%s 파일 삭제!", boardFile.getUuidFileName()));
        boardFiles.remove(i);
      }
    }

    if (boardFiles.size() > 0) {
      board.setFileList(boardFiles);
    }

      boardService.updateBoard(board);

    // 게시글을 변경하는 과정에서 세션에 임시 보관한 첨부파일 목록 정보를 제거한다.
    sessionStatus.setComplete();

      return "redirect:list?categoryNo=" + board.getCategoryNo();

    }

  @GetMapping("deleteBoard")  // 게시글 삭제
  public String deleteBoard(int categoryNo, int boardNo, HttpSession session) throws Exception {

    Member loginUser = (Member) session.getAttribute("loginUser");
    if (loginUser == null) {
      throw new Exception("로그인하시기 바랍니다!");
    }

    Board board = boardService.getBoard(boardNo);
    if (board == null) {
      throw new Exception("게시판 번호가 유효하지 않습니다.");
    } else if (board.getWriter().getNo() != loginUser.getNo()) {
      throw new Exception("권한이 없습니다.");
    }

    List<BoardFile> files = boardService.getBoardFiles(boardNo);

    boardService.deleteBoard(boardNo);

    for (BoardFile file : files) {
      storageService.delete(this.bucketName, this.uploadDir, file.getUuidFileName());
    }

    return "redirect:list?categoryNo=" + categoryNo;
  }

  @PostMapping("file/upload")
  @ResponseBody
  public Object fileUpload(
      MultipartFile[] files,
      HttpSession session,
      Model model) throws Exception {
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
      String filename = storageService.upload(this.bucketName, this.uploadDir, file);
      boardFiles.add(BoardFile.builder().uuidFileName(filename).oriFileName(filename).build());
    }

    // 업로드한 파일 목록을 세션에 보관한다.
    model.addAttribute("boardFiles", boardFiles);

    // 클라이언트에서 이미지 이름을 가지고 <img> 태그를 생성할 수 있도록
    // 업로드한 파일의 이미지 정보를 보낸다.
    return boardFiles;
  }

  @GetMapping("file/delete") // 게시판 첨부 파일 삭제
  public String fileDelete(int categoryNo, int fileNo, HttpSession session) throws Exception {

    Member loginUser = (Member) session.getAttribute("loginUser");
    if (loginUser == null) {
      throw new Exception("로그인하시기 바랍니다!");
    }

    BoardFile file = boardService.getBoardFile(fileNo);
    if (file == null) {
      throw new Exception("첨부파일 번호가 유효하지 않습니다.");
    }

    Member writer = boardService.getBoard(file.getBoardNo()).getWriter();
    if (writer.getNo() != loginUser.getNo()) {
      throw new Exception("권한이 없습니다.");
    }

    boardService.deleteBoardFile(fileNo);

    storageService.delete(this.bucketName, this.uploadDir, file.getUuidFileName());

    return "redirect:../view?categoryNo=" + categoryNo + "&boardNo=" + file.getBoardNo();
  }

  @PostMapping("addComment") // 댓글 또는 답글 작성
  public String addComment(
      @ModelAttribute("reply") Reply reply,
      @ModelAttribute("comment") Comment comment,
      Board board,
      HttpSession session) throws Exception {

    Member loginUser = (Member) session.getAttribute("loginUser"); // 로그인 요청
    if (loginUser == null) {
      throw new Exception("로그인하시기 바랍니다!");
    }
    board.setWriter(loginUser);

    if (reply != null) { // 답글인 경우
      reply.setWriter(loginUser);
      System.out.println(reply);
      replyService.addComment(reply);
    } else if (comment != null) { // 댓글인 경우
      comment.setWriter(loginUser);
      System.out.println(comment);
      commentService.addComment(comment);
    } else {
      throw new IllegalArgumentException("댓글 또는 답글을 작성해주세요.");
    }

    return "redirect:list";
  }


  @PostMapping("updateComment") // 답글 또는 댓글 수정
  public String updateComment(
      @ModelAttribute("reply") Reply reply,
      @ModelAttribute("comment") Comment comment,
      HttpSession session) throws Exception {

    Member loginUser = (Member) session.getAttribute("loginUser");
    if (loginUser == null) {
      throw new Exception("로그인하시기 바랍니다!");
    }

    if (reply.getReplyNo() != 0) { // 답글인 경우
      Reply oldReply = replyService.get(reply.getReplyNo());
      if (oldReply == null) {
        throw new Exception("답글 번호가 유효하지 않습니다.");
      } else if (oldReply.getWriter().getNo() != loginUser.getNo()) {
        throw new Exception("권한이 없습니다.");
      }
      replyService.updateComment(reply);
    } else if (comment.getCommentNo() != 0) { // 댓글인 경우
      Comment oldComment = commentService.getComment(comment.getCommentNo());
      if (oldComment == null) {
        throw new Exception("댓글 번호가 유효하지 않습니다.");
      } else if (oldComment.getWriter().getNo() != loginUser.getNo()) {
        throw new Exception("권한이 없습니다.");
      }
      commentService.updateComment(comment);
    } else {
      throw new IllegalArgumentException("수정할 댓글 또는 답글 번호를 제공해야 합니다.");
    }

    return "redirect:list";
  }

  @GetMapping("deleteComment") // 댓글 또는 답글 삭제
  public String deleteComment(
      @RequestParam(required = false) Integer replyNo,
      @RequestParam(required = false) Integer commentNo,
      Board board,
      HttpSession session) throws Exception {

    Member loginUser = (Member) session.getAttribute("loginUser"); // 로그인 요청
    if (loginUser == null) {
      throw new Exception("로그인하시기 바랍니다!");
    }

    if (replyNo != null) {
      Reply reply = replyService.get(replyNo);
      if (reply == null) {
        throw new Exception("답글 번호가 유효하지 않습니다.");
      } else if (reply.getWriter().getNo() != loginUser.getNo()) {
        throw new Exception("권한이 없습니다.");
      }
      replyService.deleteComment(replyNo);
    } else if (commentNo != null) {
      Comment comment = commentService.getComment(commentNo);
      if (comment == null) {
        throw new Exception("댓글 번호가 유효하지 않습니다.");
      } else if (comment.getWriter().getNo() != loginUser.getNo()) {
        throw new Exception("권한이 없습니다.");
      }
      commentService.deleteComment(commentNo);
    } else {
      throw new IllegalArgumentException("삭제할 댓글 또는 답글 번호를 제공해야 합니다.");
    }

    return "redirect:list";
  }

  @PostMapping("addReport") // 신고 작성
  public String addReport(
      BoardReport boardReport,
      MultipartFile[] boardFiles,
      HttpSession session) throws Exception {

    Member loginUser = (Member) session.getAttribute("loginUser");
    if (loginUser == null) {
      throw new Exception("로그인하시기 바랍니다!");
    }
    boardReport.setWriter(loginUser);

    ArrayList<BoardFile> files = new ArrayList<>();
    for (MultipartFile file : boardFiles) {
      if (file.getSize() == 0) {
        continue;
      }
      String filename = storageService.upload(this.bucketName, this.uploadDir, file);
      files.add(BoardFile.builder().uuidFileName(filename).oriFileName(filename).build());
    }

    if (files.size() > 0) {
      boardReport.setFileList(files);
    }

    boardReportService.addReport(boardReport);

    return "redirect:list?categoryNo="+ boardReport.getCategoryNo();
  }
}