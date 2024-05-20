package salaba.service;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import salaba.vo.board.Board;
import salaba.vo.board.Comment;
import salaba.vo.board.Reply;

public interface CommentService {
  void addComment(Comment comment);  // 댓글 작성

  List<Comment> list(int boardNo);  // 게시판 번호로 댓글 조회


  int updateComment(Comment comment);  // 댓글 수정, 변경

  int deleteComment(int commentNo);  // 댓글 삭제

  Comment getBy(int commentNo);

  int selectCommentWriterInfo(int commentNo); // 댓글 회원 번호 가져오기
}
