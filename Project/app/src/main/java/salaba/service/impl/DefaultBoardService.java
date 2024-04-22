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
import salaba.vo.board.BoardFile;
import salaba.vo.board.Board;

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
    return boardDao.findAll(categoryNo, pageNo, pageSize, headNo);
  }


  @Override
  public Board getBoard(int boardNo, int categoryNo) { // 상세 조회
    return boardDao.findBy(boardNo, categoryNo);
  }

  @Override
  public Board getBoardNo(int boardNo) { // 댓글 조회
    increaseViewCount(boardNo); // 조회수 증가
    return boardDao.findByBoardNo(boardNo);
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

  @Transactional
  @Override
  public int deleteBoardFile(int fileNo) {
    return boardFileDao.delete(fileNo);
  } // 게시글 첨부파일 삭제

  @Override
  public int countAll(int categoryNo) {
    return boardDao.countAll(categoryNo);
  } // count

  @Transactional
  @Override
  public void addAllFiles(List<BoardFile> boardFiles) {
    boardFileDao.addAll(boardFiles);
  }


  @Transactional
  @Override
  public void increaseViewCount(int boardNo) {
    boardDao.increaseViewCount(boardNo); // DAO 메서드 호출
  }

  @Transactional
  @Override
  public int increaseLikeCount(int boardNo, int memberNo) {
    return boardDao.increaseLikeCount(boardNo, memberNo);

  } // 게시물의 추천 수 증가 메서드 호출


  @Override
  public int decreaseLikeCount(int boardNo, int memberNo) {
    return boardDao.decreaseLikeCount(boardNo, memberNo);
  }


// 검색 기능

}