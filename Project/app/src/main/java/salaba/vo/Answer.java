package salaba.vo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@Data
public class Answer implements Serializable {

  private static final long serialVersionUID = 100L;

  private int no; // 질문 번호
  private String answer; // 답변
  private Date answerDate; // 답변 날짜
  
}
