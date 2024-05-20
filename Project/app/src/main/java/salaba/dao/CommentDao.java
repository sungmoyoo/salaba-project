package salaba.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import salaba.vo.board.Comment;

@Mapper
public interface CommentDao {
  void addComment(Comment comment);  // 댓글 작성

  int deleteComment(@Param("commentNo") int commentNo);  // 댓글 삭제

  List<Comment> findAll(int boardNo);   // 댓글 조회

  int updateComment(Comment comment); // 댓글 수정

  Comment findBy(@Param("commentNo") int commentNo);

  int selectCommentWriterInfo(@Param("commentNo") int commentNo); // 댓글 회원 번호 가져오기

}
