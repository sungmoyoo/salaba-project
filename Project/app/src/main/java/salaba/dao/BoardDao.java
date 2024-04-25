package salaba.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import salaba.vo.board.Board;

@Mapper
public interface BoardDao {  // 게시판 인터페이스

  void addBoard(Board board); // 게시글 작성

  int deleteBoard(@Param("boardNo") int boardNo); // 글 삭제

  List<Board> findAll( // 일반 게시글 목록 조회
      @Param("categoryNo") int categoryNo, @Param("offset") int offset,
      @Param("rowCount") int rowCount, @Param("headNo") int headNo);

  List<Board> findAllAnnouncements( // 공지사항 목록 조회
      @Param("categoryNo") int categoryNo, @Param("offset") int offset,
      @Param("rowCount") int rowCount, @Param("headNo") int headNo);


  Board findBy(@Param("boardNo") int boardNo, @Param("categoryNo") int categoryNo); // 상세조회

  Board findByBoardNo(@Param("boardNo") int boardNo);

  int updateBoard(Board board); // 글 변경, 수정

  int countAll(int categoryNo);// 페이징 처리
  void increaseViewCount(int boardNo); // 조회수 증가

  int increaseLikeCount(@Param("boardNo") int boardNo, @Param("memberNo") int memberNo); // 추천수 증가

  int decreaseLikeCount(@Param("boardNo") int boardNo, @Param("memberNo") int memberNo); // 추천 취소

  int countLike(@Param("boardNo") int boardNo, @Param("memberNo") int memberNo); // 한 회원의 추천수

  List<Board> searchByKeyword(@Param("keyword") String keyword, @Param("type") String type); // 검색
}
