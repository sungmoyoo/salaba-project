package salaba.service;

import java.util.List;

import salaba.vo.Member;
import salaba.vo.board.Reply;

public interface ReplyService {
  void addReply(Reply reply
      // , int ReplyNo
  );  // 답글 작성

  List<Reply> list(int commentNo);  // 댓글 번호로 답글 조회

  int updateReply(Reply reply);  // 답글 수정, 변경

  int deleteReply(int replyNo);  // 답글 삭제

  Reply getReply(int replyNo); // 조회

}
