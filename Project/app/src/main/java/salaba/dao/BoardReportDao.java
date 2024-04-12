package salaba.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import salaba.vo.BoardReport;

@Mapper
public interface BoardReportDao { // 게시판 신고 인터페이스

  void addReport(BoardReport boardReport); // 신고 내역 작성

  List<BoardReport> findAll(  // 신고 내역 찾기
      @Param("categoryNo") int categoryNo,
      @Param("offset") int offset,
      @Param("rowCount") int rowCount);

  BoardReport findBy(int reportNo); // 신고 번호로 신고 내역 조회

}
