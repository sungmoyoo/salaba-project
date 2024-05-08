package salaba.vo;

import java.io.Serializable;
import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Reservation2 implements Serializable { // 임시

  private static final long serialVersionUID = 100L;

  private int memberNo;

  private int rentalHomeNo;

  private Date startDate;

  private Date endDate;

  private String chatFileName;

  private int numberOfPeople;
}
