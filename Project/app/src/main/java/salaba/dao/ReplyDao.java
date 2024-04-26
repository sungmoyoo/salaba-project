package salaba.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import salaba.vo.Member;
import salaba.vo.board.Reply;

@Mapper
public interface ReplyDao {
  void addReply(Reply reply);  // 답글 작성

  int deleteReply(@Param("replyNo") int replyNo);  // 답글 삭제

  List<Reply> findAll(@Param("commentNo") int commentNo);   // 답글 조회

  int updateReply(Reply reply); // 답글 수정

  Reply findReply(@Param("replyNo") int replyNo);

}
