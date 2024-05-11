package salaba.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import salaba.dao.BoardReportDao;
import salaba.dao.BoardReportFileDao;
import salaba.service.BoardReportService;
import salaba.vo.board.BoardReport;
import salaba.vo.board.BoardReportFile;

@RequiredArgsConstructor // 생성자 자동생성
@Service
public class DefaultBoardReportService implements BoardReportService { // 게시판 신고 ServiceImpl 구현체

  private static final Log log = LogFactory.getLog(DefaultBoardService.class);
  private final BoardReportDao boardReportDao;
  private final BoardReportFileDao boardReportFileDao;

  @Transactional
  @Override
  public void addReport(BoardReport boardReport) { // 신고 폼 작성
    boardReportDao.addReport(boardReport);
    if (boardReport.getReportFileList() != null && boardReport.getReportFileList().size() > 0) {
      for (BoardReportFile boardReportFile : boardReport.getReportFileList()) {
        boardReportFile.setReportNo(boardReport.getReportNo());
      }
      log.debug("xxxx" + boardReport.getReportFileList());
      boardReportFileDao.addAllFiles(boardReport.getReportFileList());
    }
  }
  @Override
  public BoardReport checkReported(int memberNo, int targetNo, String targetType) { // 사용자가 이미 신고했는지 확인
    return boardReportDao.checkReported(memberNo, targetNo, targetType);
  }

}


