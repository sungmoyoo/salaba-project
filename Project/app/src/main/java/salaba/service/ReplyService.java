package salaba.service;

import java.util.List;
import salaba.vo.Comment;
import salaba.vo.Reply;

public interface ReplyService {
  void addComment(Reply reply);  // 답글 작성

  List<Reply> list(int commentNo);  // 댓글 번호로 답글 조회

  int updateComment(Reply reply);  // 답글 수정, 변경

  int deleteComment(int replyNo);  // 답글 삭제


  Reply get(int replyNo); // 조회
}
