package salaba.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import salaba.service.BoardService;
import salaba.service.CommentService;
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


  @Value("${ncp.bucketname}")
  private String bucketName;

  @GetMapping("board/main") // 게시글 전체 메인화면
  public void mainBoard(Model model) throws Exception {
    List<Board> reviewBoardList = boardService.listBoard(0, 1, 4, 1);
    List<Board> infoBoardList = boardService.listBoard(1, 1, 5, 1);
    List<Board> communityBoardList = boardService.listBoard(2, 1, 5, 1);
    model.addAttribute("review", sort(reviewBoardList));
    model.addAttribute("information", sort(infoBoardList));
    model.addAttribute("community", sort(communityBoardList));
  }

  // 보드 리스트를 말머리 순으로 정렬해주는 함수
  private List<Board> sort(List<Board> boardList) {
    List<Board> sortedList = new ArrayList<>();
    List<Board> headNo1List = new ArrayList<>();
    List<Board> otherHeadNoList = new ArrayList<>();

    // headNo가 1인 경우(=공지)와 나머지 경우 분류
    for (Board board : boardList) {
      if (board.getCategoryNo() == 0) { // 카테고리 번호가 0인 경우(후기 게시판에는 공지x) 정렬하지 않고 그대로 추가
        sortedList.add(board);
      } else if (board.getHeadNo() == 1) {
        headNo1List.add(board);
      } else {
        otherHeadNoList.add(board);
      }
    }

    // headNo가 1인 경우에 대해서만 최대 2개까지 정렬하여 결과 리스트에 추가
    headNo1List.sort((o1, o2) -> Integer.compare(o1.getHeadNo(), o2.getHeadNo()));
    int count = 0;
    for (Board board : headNo1List) {
      if (count < 2) {
        sortedList.add(board);
        count++;
      }
    }
    sortedList.addAll(otherHeadNoList);
    return sortedList;
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
      @RequestParam(defaultValue = "5") int pageSize,
      @RequestParam(defaultValue = "1") int headNo,
      Model model) throws Exception {

    if (pageSize < 3 || pageSize > 20) {  // 페이지 설정
      pageSize = 5;
    }

    if (pageNo < 1) {
      pageNo = 1;
    }

    int numOfRecord = boardService.countAll(categoryNo);
    int numOfPage = numOfRecord / pageSize + ((numOfRecord % pageSize) > 0 ? 1 : 0);

    if (pageNo > numOfPage) {
      pageNo = numOfPage;
    }

    model.addAttribute("boardName",
        categoryNo == 0 ? "후기게시판" : (categoryNo == 1 ? "정보공유게시판" : "자유게시판"));
    model.addAttribute("categoryNo", categoryNo);
    model.addAttribute("headNo", headNo);

    List<Board> boardList = boardService.listBoard(categoryNo, pageNo, pageSize, 1);
    log.debug(boardList);
    boardList = sort(boardList); // 정렬 함수 호출
    model.addAttribute("list", boardList);

    model.addAttribute("pageNo", pageNo);
    model.addAttribute("pageSize", pageSize);
    model.addAttribute("numOfPage", numOfPage);
  }

  @GetMapping("board/view")  // 게시글 조회
  public String viewBoard(@RequestParam("categoryNo") int categoryNo, // 카테고리 번호
      @RequestParam("boardNo") int boardNo, // 게시글 번호
      @RequestParam(value = "commentNo", required = false) Integer commentNo,
      // 댓글번호 - 필수x, 답글 찾을 때 필요
      Model model,
      HttpSession session) throws Exception {

    Board board = boardService.getBoard(boardNo, categoryNo);
    //log.debug("abcdefg"+ board);
    if (board == null) {
      throw new Exception("번호가 유효하지 않습니다.");
    }

    // 조회수 증가 (게시글 존재 및 접근 가능 확인 후)
    boardService.increaseViewCount(boardNo);

    Member loginUser = (Member) session.getAttribute("loginUser"); // 로그인
    if (loginUser == null) { // 로그인 정보 요청
      throw new Exception("로그인하시기 바랍니다!");
    }
    System.out.println(board.getScopeNo());

    // 공개 범위에 따라 접근 제어
    switch (board.getScopeNo()) {
      case 2: // 작성자만
        if (board.getWriter().getNo() != loginUser.getNo()) {
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

    if (commentNo != null) { // 댓글이 있을 시 댓글과 답글을 함께 조회
      Comment comment = commentService.getComment(commentNo); // 댓글 번호로 답글 찾기
      model.addAttribute("commentList", commentService.list(board.getBoardNo()));  // 댓글 조회
      model.addAttribute("replyList", replyService.list(comment.getCommentNo()));  // 답글 조회
    }
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

  @GetMapping("board/modify")
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
      int categoryNo)
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

//  @GetMapping("board/file/delete") // 게시판 첨부 파일 삭제
//  public String fileDelete(int categoryNo, int fileNo, HttpSession session) throws Exception {
//
//    Member loginUser = (Member) session.getAttribute("loginUser");
//    if (loginUser == null) {
//      throw new Exception("로그인하시기 바랍니다!");
//    }
//
//    BoardFile file = boardService.getBoardFile(fileNo);
//    if (file == null) {
//      throw new Exception("첨부파일 번호가 유효하지 않습니다.");
//    }
//
//    Member writer = boardService.getBoard(file.getBoardNo()).getWriter();
//    if (writer.getNo() != loginUser.getNo()) {
//      throw new Exception("권한이 없습니다.");
//    }
//
//    boardService.deleteBoardFile(fileNo);
//
//    storageService.delete(this.bucketName, this.uploadDir, file.getUuidFileName());
//
//    return "redirect:../view?categoryNo=" + categoryNo + "&boardNo=" + file.getBoardNo();
//  }

  @PostMapping("board/addComment") // 댓글 또는 답글 작성
  public String addComment(
      HttpServletRequest request,
      HttpSession session,
      @RequestParam("boardNo") int boardNo,
      @RequestParam(value = "commentNo", required = false) Integer commentNo) throws Exception {

    Member loginUser = (Member) session.getAttribute("loginUser");
    if (loginUser == null) {
      throw new Exception("로그인하시기 바랍니다!");
    }

    // 댓글 또는 답글의 내용
    String content = request.getParameter("comment");

    // 이미지 경로(photo) - 폼에서 제출하지 않은 경우 적절한 방법으로 가져와야 함
    //String photo = "";

    // 게시판 정보 가져오기
    Board board = boardService.getBoardNo(boardNo);

    // 댓글 또는 답글에 해당하는지 확인하여 처리
    // 댓글 또는 답글에 해당하는지 확인하여 처리
    if (content != null && !content.isEmpty()) {
      String type = request.getParameter("type");
      if (type == null) {
        type = "comment"; // 기본값으로 댓글로 설정
      }

      if (type.equals("reply")) { // 답글인 경우
        Reply reply = new Reply();
        reply.setContent(content);
        reply.setWriter(loginUser);
        // reply.setPhoto(photo); // 이미지 경로 설정
        replyService.addComment(reply);
      } else { // 댓글인 경우
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setWriter(loginUser);
        // comment.setPhoto(photo);
        comment.setBoard(board); // 게시판 정보 설정
        commentService.addComment(comment, boardNo);
      }
    } else {
      throw new IllegalArgumentException("댓글 또는 답글을 작성해주세요.");
    }

    return "redirect:view?boardNo=" + boardNo; // 적절한 경로로 리다이렉트
  }


  @PostMapping("comment/update") // 답글 또는 댓글 수정
  public String updateComment(@ModelAttribute("reply") Reply reply,
      @ModelAttribute("comment") Comment comment, HttpSession session) throws Exception {

    Member loginUser = (Member) session.getAttribute("loginUser");
    if (loginUser == null) {
      throw new Exception("로그인하시기 바랍니다!");
    }

    if (reply.getReplyNo() != 0) { // 답글인 경우
      Reply oldReply = replyService.getComment(reply.getReplyNo());
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

  @GetMapping("comment/delete") // 댓글 또는 답글 삭제 - 상태 1로 변경
  public String deleteComment(
      @RequestParam(required = false) Integer replyNo,
      @RequestParam(required = false) Integer commentNo,
      Board board,
      HttpSession session)
      throws Exception {

    Member loginUser = (Member) session.getAttribute("loginUser"); // 로그인 요청
    if (loginUser == null) {
      throw new Exception("로그인하시기 바랍니다!");
    }

    if (replyNo != null) {
      Reply reply = replyService.getComment(replyNo);
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
  public ResponseEntity<?> likeBoard(
      @RequestParam("boardNo") int boardNo,
      HttpSession session) {
    Member loginUser = (Member) session.getAttribute("loginUser");
    if (loginUser == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인을 해주세요!");
    }

    try {
      log.debug(("ffff") + boardNo);
      boardService.increaseLikeCount(boardNo, loginUser.getNo());
      return ResponseEntity.status(HttpStatus.OK).body("추천 완료");
    } catch (Exception e) {
      log.error("추천 처리 중 오류 발생", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("추천 처리 중 오류 발생!");
    }

  }

  @PostMapping("/board/unlike")      // 추천 취소 로직
  public ResponseEntity<?> removeLike(
      @RequestParam int boardNo,
      HttpSession session) {

    Member loginUser = (Member) session.getAttribute("loginUser");

    try {
      boardService.decreaseLikeCount(boardNo, loginUser.getNo());
      return ResponseEntity.status(HttpStatus.OK).body("추천 취소");
    } catch (Exception e) {
      log.error("추천 처리 중 오류 발생", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("추천 처리 중 오류 발생!");
    }
  }

  // 검색
//  @GetMapping("/search")
//  public String search(@RequestParam("type") String type,
//      @RequestParam("q") String query,
//      Model model) {
//    List<Board> searchResults = boardService.search(type, query);
//    model.addAttribute("searchResults", searchResults);
//    return "searchResults";  // 검색 결과를 보여줄 뷰 페이지
//  }

}
