package salaba.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import salaba.vo.board.Comment;

@Mapper
public interface CommentDao {
  void addComment(@Param("comment") Comment comment, @Param("boardNo") int boardNo);  // 댓글 작성

  int deleteComment(@Param("commentNo") int commentNo);  // 댓글 삭제

  List<Comment> findAll(int boardNo);   // 댓글 조회

  int updateComment(Comment comment); // 댓글 수정

}
