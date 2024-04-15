package salaba.dao;

import org.apache.ibatis.annotations.Mapper;
import salaba.vo.BoardReport;

@Mapper
public interface BoardReportDao { // 게시판 신고 인터페이스
  void addReport(BoardReport boardReport); // 신고 내역 작성
}
