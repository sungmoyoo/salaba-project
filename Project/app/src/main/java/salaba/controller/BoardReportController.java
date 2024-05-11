package salaba.controller;

import java.util.ArrayList;
import java.util.List;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import salaba.service.BoardReportService;
import salaba.service.BoardService;
import salaba.service.StorageService;
import salaba.util.Translator;
import salaba.vo.board.Board;
import salaba.vo.board.BoardFile;
import salaba.vo.board.BoardReport;
import salaba.vo.Member;
import salaba.vo.board.BoardReportFile;

@RequiredArgsConstructor
@Controller
@SessionAttributes("boardReportFiles")
public class BoardReportController { // 게시글, 댓글, 답글 신고 컨트롤러

  private static final Log log = LogFactory.getLog(BoardController.class);
  private final BoardReportService boardReportService; // 신고 서비스
  private final BoardService boardService;
  private final StorageService storageService; // 스토리지 서비스

  private String uploadDir = "board/";

  @Value("${ncpbucketname}")
  private String bucketName;

  @PostMapping("/board/report/add")
  public ResponseEntity<?> addReport(
      BoardReport boardReport,
      @RequestParam("reportFiles") MultipartFile[] reportFiles,
      HttpSession session) {

    Member loginUser = (Member) session.getAttribute("loginUser");
    if (loginUser == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인하시기 바랍니다!");
    }

    // 이미 신고한 경우를 판별하기 위한 변수
    BoardReport reportedBoard = boardReportService.checkReported(loginUser.getNo(), boardReport.getTargetNo(), boardReport.getTargetType());

    if (reportedBoard != null) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 신고되었습니다.");
    }

    List<BoardReportFile> reportFileList = new ArrayList<>();
    try {
      for (MultipartFile file : reportFiles) {
        if (file.isEmpty()) { // 첨부파일 검사 변경
          throw new RuntimeException("첨부파일을 등록하세요!");
        }
        String filename = storageService.upload(this.bucketName, this.uploadDir, file);
        reportFileList.add(BoardReportFile.builder().uuidFileName(filename).oriFileName(file.getOriginalFilename()).build());
      }

      if (!reportFileList.isEmpty()) {
        boardReport.setReportFileList(reportFileList);
      }

      boardReport.setWriter(loginUser);

      boardReportService.addReport(boardReport);
      return ResponseEntity.ok("신고가 성공적으로 제출되었습니다.");
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}