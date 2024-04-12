package salaba.vo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@Data
public class Point_history implements Serializable {

  private static final long serialVersionUID = 100L;

  private int no; // 회원 번호
  private String saveContent; // 적립 내용
  private int savePoint; // 적립 포인트
  private Date saveDate; // 적립 날짜

}
