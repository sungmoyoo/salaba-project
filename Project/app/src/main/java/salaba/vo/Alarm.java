package salaba.vo;

import java.io.Serializable;
import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Alarm implements Serializable {
  private static final long serialVersionUID = 100L;

  private int notifyNo; // 알림번호

  private int memberNo; // 회원번호

  private String content; // 내용

  private Date notifyDate; // 일시
  
  private String mark; // 알람 표시 내용
}
