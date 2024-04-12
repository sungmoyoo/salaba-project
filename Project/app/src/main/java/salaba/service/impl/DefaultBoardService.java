package salaba.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
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
      boardFileDao.addAll(board.getFileList());
    }
  }

  @Override
  public List<Board> listBoard(int categoryNo, int pageNo, int pageSize) {  // 목록 조회
    return boardDao.findAll(categoryNo, pageSize * (pageNo - 1), pageSize);
  }


  @Override
  public Board getBoard(int boardNo) {
    return boardDao.findBy(boardNo);
  } // 상세 조회

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
  public List<BoardFile> getBoardFiles(int boardNo) {
    return boardFileDao.findAllByBoardNo(boardNo);
  }

  @Override
  public BoardFile getBoardFile(int fileNo) {
    return boardFileDao.findByNo(fileNo);
  }

  @Override
  public int deleteBoardFile(int fileNo) {
    return boardFileDao.delete(fileNo);
  }

  @Override
  public int countAll(int categoryNo) {
    return boardDao.countAll(categoryNo);
  }

}