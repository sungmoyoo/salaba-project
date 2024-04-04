package salaba.vo.rentalHome;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.Data;
import org.springframework.ui.context.Theme;


@Data
public class RentalHomeDetail implements Serializable { // 숙소
  private static final long serialVersionUID = 100L;

  private int rentalHomeNo; // 숙소번호

  private int facilityNo; // 숙소시설번호

  private int facilityCount; // 시설 개수
}
