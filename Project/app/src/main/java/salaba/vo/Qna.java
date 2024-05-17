package salaba.vo;

import java.io.Serializable;
import java.sql.Date;
import lombok.Data;

@Data
public class Qna implements Serializable {

  private static final long serialVersionUID = 100L;

  private int questionNo;
  private String answer; // 답변
  private Date answerDate; // 답변 날짜
  
}
