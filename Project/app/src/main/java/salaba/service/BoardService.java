package salaba.service;

import java.util.List;
import salaba.vo.board.BoardFile;
import salaba.vo.board.Board;

public interface BoardService {

  void addBoard(Board board); // 게시판 글 작성

  List<Board> listBoard(int categoryNo, int pageNo, int pageSize, int headNo); // 게시판 조회

  List<Board> mainBoard(int categoryNo, int pageNo, int pageSize, int headNo); // 게시판 조회


  Board getBoard(int boardNo, int categoryNo); // 게시판 상세 조회
  Board getBoardNo(int boardNo); // 댓글 조회, 조회수용

  int updateBoard(Board board); // 게시판 업데이트

  int deleteBoard(int boardNo); // 게시판 삭제

  List<BoardFile> getBoardFiles(int boardNo);  // 게시판 번호로 첨부파일 목록 조회

  BoardFile getBoardFile(int fileNo); // 첨부파일 조회

  int deleteBoardFile(int fileNo); // 첨부파일 삭제

  int countAll(int categoryNo); // count

  void addAllFiles(List<BoardFile> boardFiles); // 파일 추가

  void increaseViewCount(int boardNo); // 게시글 조회수 증가

  int increaseLikeCount(int boardNo, int memberNo); // 게시글 추천수 증가
  int decreaseLikeCount(int boardNo, int memberNo); // 추천 취소


 List<Board> search(String keyword, String type); // 검색 기능
  List<Board> searchByTitle(String title); // 제목으로 검색
  List<Board> searchByContent(String content); // 내용으로 검색
}
