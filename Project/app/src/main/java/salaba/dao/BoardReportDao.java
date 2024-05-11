package salaba.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import salaba.vo.board.BoardFile;
import salaba.vo.board.BoardReport;
import salaba.vo.board.BoardReportFile;

@Mapper
public interface BoardReportDao { // 게시판 신고 인터페이스
  void addReport(BoardReport boardReport); // 신고 내역 작성

  BoardReport checkReported(@Param("memberNo") int memberNo, @Param("targetNo") int targetNo,
      @Param("targetType")String targetType); // 이미 신고한 사용자 처리
}
