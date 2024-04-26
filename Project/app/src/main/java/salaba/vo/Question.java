package salaba.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.Data;

@Data
public class Question implements Serializable {

  private static final long serialVersionUID = 100L;

  private int no; // 회원 번호
  private int questionNo; // 질문 번호
  private String title; // 질문 제목
  private String content; // 질문 내용
  private String state; // 질문 상태
  private Date registerDate; // 작성 날짜

}
