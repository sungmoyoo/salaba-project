package salaba.vo.rentalHome;

import java.io.Serializable;
import lombok.Data;

@Data
public class RentalHomeFacility implements Serializable { // 숙소 편의시설
  private static final long serialVersionUID = 100L;

  private int facilityNo; // 숙소 시설번호

  private String facilityName; // 숙소 시설명

  private int facilityCount; // 시설 갯수
}
