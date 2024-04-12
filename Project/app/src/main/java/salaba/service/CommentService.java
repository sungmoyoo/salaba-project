package salaba.service;

import java.util.List;
import salaba.vo.Comment;

public interface CommentService {
  void addComment(Comment comment);  // 댓글 작성

  List<Comment> list(int boardNo);  // 게시판 번호로 댓글 조회

  int updateComment(Comment comment);  // 댓글 수정, 변경

  int deleteComment(int commentNo);  // 댓글 삭제

  Comment getComment(int commentNo);
}
