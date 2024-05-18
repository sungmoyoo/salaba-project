package salaba.service;

import salaba.vo.board.BoardReport;

public interface BoardReportService { // 게시판 신고 Service Interface
  void addReport(BoardReport boardReport); // 게시판 신고 작성

  BoardReport checkReported(int memberNo, int targetNo, String targetType); // 이미 신고한 게시글/댓글/답글 여부 확인

}