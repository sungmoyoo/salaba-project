package salaba.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import salaba.vo.BoardFile;

@Mapper
public interface BoardFileDao { // 게시판, 신고 첨부파일 인터페이스

  void add(BoardFile file); // 파일 추가

  int addAll(List<BoardFile> files); // 파일 전체 추가

  int delete(int fileNo); // 파일 삭제

  int deleteAll(int boardNo); // 파일 전체 삭제

  BoardFile findByNo(int fileNo); // 파일 찾기

  List<BoardFile> findAllByBoardNo(int boardNo); // 게시판 번호로 게시판 첨부파일 찾기

  List<BoardFile> findAllByReportNo(int reportNo); // 신고 번호로 신고 첨부파일 찾기
}
