package salaba.vo;

import java.io.Serializable;
import java.sql.Date;
import lombok.Data;

@Data
public class PointHistory implements Serializable {

  private static final long serialVersionUID = 100L;

  private int no;
  private String saveContent; // 적립 내용
  private int savePoint; // 적립 포인트
  private Date saveDate; // 적립 날짜
  private int totalPoint;

}
