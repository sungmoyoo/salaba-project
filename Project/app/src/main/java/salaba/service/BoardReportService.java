package salaba.service;


import java.util.List;
import salaba.vo.BoardFile;
import salaba.vo.BoardReport;

public interface BoardReportService { // 게시판 신고 Service Interface
  void addReport(BoardReport boardReport); // 게시판 신고 작성

  List<BoardReport> list(int categoryNo, int pageNo, int pageSize);  // 특정 카테고리에 해당하는 게시판 신고 목록 조회

  BoardReport get(int reportNo);   // 게시판 신고 조회(상세보기)

  List<BoardFile> getBoardFiles(int reportNo); // 게시판 신고 파일 조회

}