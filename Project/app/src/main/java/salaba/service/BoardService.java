package salaba.service;

import java.util.List;
import salaba.vo.BoardFile;
import salaba.vo.Board;
import salaba.vo.PointHistory;

public interface BoardService {
  void addBoard(Board board); // 게시판 글 작성

  List<Board> listBoard(int categoryNo, int pageNo, int pageSize, int headNo); // 게시판 조회

  Board getBoard(int boardNo, int categoryNo); // 게시판 상세 조회

  int updateBoard(Board board); // 게시판 업데이트

  int deleteBoard(int boardNo); // 게시판 삭제

  List<BoardFile> getBoardFiles(int boardNo);  // 게시판 번호로 첨부파일 목록 조회

  BoardFile getBoardFile(int fileNo); // 첨부파일 조회

  int deleteBoardFile(int fileNo); // 첨부파일 삭제

  int countAll(int categoryNo); // count

  void addAllFiles(List<BoardFile> boardFiles); // 파일 추가

  void incrementViewCount(int boardNo, int categoryNo); // 게시글 조회수 증가

  int countAllHistory(int memberNo); // count

  List<Board> boardHistory(int pageNo, int pageSize, int no); // 작성글 내역

  int countAllCommentHistory(int memberNo); // count

  List<Board> commentHistory(int pageNo, int pageSize, int no); // 작성글 내역
}
