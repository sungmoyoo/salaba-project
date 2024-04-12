package salaba.vo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@Data
public class Reply implements Serializable { // 답글
  private static final long serialVersionUID = 100L;

  private int replyNo;  // 답글 번호
  private int commentNo; // 댓글 번호
  private String content;  // 내용
  private Member writer;  // 답글 작성자
  private Date createdDate; // 작성일
  private char state; // 상태

}
