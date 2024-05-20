package salaba.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import salaba.vo.Nation;
import salaba.vo.board.Board;
import salaba.vo.board.BoardFile;
import salaba.vo.rental_home.RentalHomePhoto;

@Mapper
public interface BoardDao {  // 게시판 인터페이스

  void addBoard(Board board); // 게시글 작성

  List<Nation> findAllNationsAndRegions(); // 모든 국가, 지역 정보를 가져오기

  int deleteBoard(@Param("boardNo") int boardNo); // 글 삭제

  List<Board> findAll( // 일반 게시글 목록 조회
      @Param("categoryNo") int categoryNo, @Param("offset") int offset,
      @Param("rowCount") int rowCount, @Param("headNo") int headNo);

  List<Board> findAnnouncements( // 상단에 고정시킬 공지사항 조회
      @Param("categoryNo") int categoryNo,
      @Param("offset") int offset);

  Board findBy(@Param("boardNo") int boardNo, @Param("categoryNo") int categoryNo); // 상세조회

  int isLiked(@Param("memberNo") int memberNo, @Param("boardNo")int boardNo); // 내 추천 여부


  List<BoardFile> boardThumbnail(int boardNo);  // 게시글 썸네일

  Board findByBoardNo(@Param("boardNo") int boardNo);

  int updateBoard(Board board); // 글 변경, 수정

  int countAll(int categoryNo);// (공지사항 제외) 목록 페이징 처리
  void increaseViewCount(int boardNo); // 조회수 증가

  int increaseLikeCount(@Param("boardNo") int boardNo, @Param("memberNo") int memberNo); // 추천수 증가(board_like insert)

  int decreaseLikeCount(@Param("boardNo") int boardNo, @Param("memberNo") int memberNo); // 추천 취소(board_like delete)

  List<Board> searchByKeyword(@Param("keyword") String keyword, @Param("type") String type, @Param("categoryNo") int categoryNo); // 검색

  int countFiltered(int categoryNo, String type, String keyword); // 검색으로 필터링해 페이징 처리

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
  
  int selectBoardWriterInfo(@Param("boardNo") int boardNo); // 게시글 작성자 회원번호 가져오기
}