package salaba.vo.rentalHome;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Data
public class RentalHomeFacility implements Serializable { // 숙소 편의시설
  private static final long serialVersionUID = 100L;


  private int facilityNo; // 숙소 시설번호

  @EqualsAndHashCode.Exclude
  private int facilityCount ; // 시설 갯수

  @EqualsAndHashCode.Exclude
  private String facilityName; // 시설명
}
