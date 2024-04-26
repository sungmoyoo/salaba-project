package salaba.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import salaba.dao.BoardFileDao;
import salaba.dao.BoardDao;
import salaba.service.BoardService;
import salaba.vo.BoardFile;
import salaba.vo.Board;

@RequiredArgsConstructor
@Service
public class DefaultBoardService implements BoardService { // 게시판 ServiceImpl 구현체

  private static final Log log = LogFactory.getLog(DefaultBoardService.class);
  private final BoardDao boardDao;  // 게시판 DAO
  private final BoardFileDao boardFileDao; // 게시판 첨부파일 DAO

  @Transactional
  @Override
  public void addBoard(Board board) {  // 글 작성
    boardDao.addBoard(board);
    if (board.getFileList() != null && board.getFileList().size() > 0) {
      for (BoardFile boardFile : board.getFileList()) {
        boardFile.setBoardNo(board.getBoardNo());
      }
      log.debug("xxxx" + board.getFileList());
      boardFileDao.addAll(board.getFileList());
    }
  }

  @Override
  public List<Board> listBoard(int categoryNo, int pageNo, int pageSize, int headNo) {  // 목록 조회
    return boardDao.findAll(categoryNo, pageSize * (pageNo - 1), pageSize, headNo);
  }


  @Override
  public Board getBoard(int boardNo, int categoryNo) { // 상세 조회
    return boardDao.findBy(boardNo, categoryNo);
  }

  @Transactional
  @Override
  public int updateBoard(Board board) { // 수정
    int count = boardDao.updateBoard(board);
    if (board.getFileList() != null && board.getFileList().size() > 0) {
      for (BoardFile boardFile : board.getFileList()) {
        boardFile.setBoardNo(board.getBoardNo());
      }
      boardFileDao.addAll(board.getFileList());
    }
    return count;
  }

  @Transactional
  @Override
  public int deleteBoard(int boardNo) { // 삭제
    boardFileDao.deleteAll(boardNo);
    return boardDao.deleteBoard(boardNo);
  }

  @Override
  public List<BoardFile> getBoardFiles(int boardNo) { return boardFileDao.findAllByBoardNo(boardNo); }

  @Override
  public BoardFile getBoardFile(int fileNo) {
    return boardFileDao.findByNo(fileNo);
  }

  @Override
  public int deleteBoardFile(int fileNo) {
    return boardFileDao.delete(fileNo);
  } // 게시글 첨부파일 삭제

  @Override
  public int countAll(int categoryNo) {
    return boardDao.countAll(categoryNo);
  } // count

  @Override
  public void addAllFiles(List<BoardFile> boardFiles) {
    boardFileDao.addAll(boardFiles);
  }

  @Transactional
  @Override
  public void incrementViewCount(int boardNo, int categoryNo) { // 조회수 증가
    Board board = boardDao.findBy(boardNo, categoryNo); // 해당 게시글 조회
    if (board != null) {
      int currentViewCount = board.getViewCount(); // 현재 조회수 가져오기
      board.setViewCount(currentViewCount + 1); // 조회수 증가
      boardDao.updateBoard(board); // 증가된 조회수를 데이터베이스에 업데이트
    } else {
      log.error("게시글이 존재하지 않습니다. 게시글 번호: " + boardNo);
    }
  }

  @Override
  public int countAllHistory(int no) {
    return boardDao.countAllHistory(no);
  } // count

  @Override
  public List<Board> boardHistory(int pageNo, int pageSize, int no) {  // 목록 조회
    return boardDao.findHistory(pageSize * (pageNo - 1), pageSize, no);
  }

  @Override
  public int countAllCommentHistory(int no) {
    return boardDao.countAllCommentHistory(no);
  } // count

  @Override
  public List<Board> commentHistory(int pageNo, int pageSize, int no) {  // 목록 조회
    return boardDao.findCommentHistory(pageSize * (pageNo - 1), pageSize, no);
  }

}