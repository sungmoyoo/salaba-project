package salaba.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import salaba.vo.board.BoardReportFile;

@Mapper
public interface BoardReportFileDao { // 신고 첨부파일 인터페이스
  int addAllFiles(List<BoardReportFile> reportFiles); // 신고 파일 전체 추가
}
