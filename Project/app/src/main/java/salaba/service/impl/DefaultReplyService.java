package salaba.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import salaba.dao.ReplyDao;
import salaba.service.ReplyService;
import salaba.vo.Reply;

@RequiredArgsConstructor
@Service
public class DefaultReplyService implements ReplyService { // 답글

  private final ReplyDao replyDao;

  @Override
  public void addComment(Reply reply) {
    replyDao.addComment(reply);
  } // 답글 작성

  @Override
  public List<Reply> list(int commentNo) { return replyDao.findAll(commentNo);} // 댓글 번호로 답글 조회

  @Override
  public int updateComment(Reply reply) {
    return replyDao.updateComment(reply);
  } // 답글 업데이트

  @Override
  public int deleteComment(int replyNo) {
    return replyDao.deleteComment(replyNo);
  }

  @Override
  public Reply get(int replyNo) { return null;}

}
