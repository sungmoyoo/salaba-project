package salaba.vo.rentalHome;

import java.io.Serializable;
import lombok.Data;


@Data
public class Facility implements Serializable { // 숙소
  private static final long serialVersionUID = 100L;

  private int facilityNo; // 숙소시설번호

  private String facilityName; // 시설명
}
