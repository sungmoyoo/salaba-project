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

  @Override  // 사용자가 신고한 내역 조회
  public List<BoardReport> list(int categoryNo, int pageNo, int pageSize) {
    return boardReportDao.findAll(categoryNo, pageSize * (pageNo - 1), pageSize);
  }

  @Override // 상세 조회
  public BoardReport get(int reportNo) {
    return boardReportDao.findBy(reportNo);
  }

  @Override // 신고 파일 조회 - 신고 번호로 조회
  public List<BoardFile> getBoardFiles(int reportNo) {
    return boardFiledao.findAllByReportNo(reportNo);
  }
}


