package salaba.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import salaba.vo.Board;

@Mapper
public interface BoardDao {  // 게시판 인터페이스

  void addBoard(Board board); // 게시글 작성

  int deleteBoard(@Param("boardNo") int boardNo); // 글 삭제

  List<Board> findAll( // 조회
      @Param("categoryNo") int categoryNo, @Param("offset") int offset,
      @Param("rowCount") int rowCount, @Param("headNo") int headNo);

  Board findBy(@Param("boardNo") int boardNo, @Param("categoryNo") int categoryNo); // 상세조회

  int updateBoard(Board board); // 글 변경, 수정

  int countAll(int categoryNo);// count

  List<Board> findHistory( // 작성글 내역
      @Param("offset") int offset,
      @Param("rowCount") int rowCount,
      @Param("no") int no);

  int countAllHistory(int no);// count

  List<Board> findCommentHistory( // 작성글 내역
      @Param("offset") int offset,
      @Param("rowCount") int rowCount,
      @Param("no") int no);

  int countAllCommentHistory(int no);// count

}
