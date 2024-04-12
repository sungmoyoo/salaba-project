package salaba.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import salaba.vo.Reply;

@Mapper
public interface ReplyDao {
  void addComment(Reply reply);  // 답글 작성

  int deleteComment(@Param("replyNo") int replyNo);  // 답글 삭제

  List<Reply> findAll(@Param("commentNo") int commentNo);   // 답글 조회

  int updateComment(Reply reply); // 답글 수정

}
