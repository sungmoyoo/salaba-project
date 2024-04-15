package salaba.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import salaba.dao.BoardFileDao;
import salaba.dao.BoardReportDao;
import salaba.service.BoardReportService;
import salaba.vo.BoardFile;
import salaba.vo.BoardReport;

@RequiredArgsConstructor // 생성자 자동생성
@Service
public class DefaultBoardReportService implements BoardReportService { // 게시판 신고 ServiceImpl 구현체

  private final BoardReportDao boardReportDao;
  private final BoardFileDao boardFiledao;

  @Transactional
  @Override
  public void addReport(BoardReport boardReport) { // 신고 폼 작성
    boardReportDao.addReport(boardReport);
    if (boardReport.getFileList() != null && boardReport.getFileList().size() > 0) {
      for (BoardFile boardFile : boardReport.getFileList()) {
        boardFile.setOriFileName(boardFile.getOriFileName()); // **uuid or ori?
      }
      boardFiledao.addAll(boardReport.getFileList());
    }
  }
}


