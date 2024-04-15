package salaba.service;

import salaba.vo.BoardReport;

public interface BoardReportService { // 게시판 신고 Service Interface
  void addReport(BoardReport boardReport); // 게시판 신고 작성
}