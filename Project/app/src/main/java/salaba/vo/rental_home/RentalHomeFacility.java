package salaba.vo.rental_home;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class RentalHomeFacility implements Serializable { // 숙소 편의시설
  private static final long serialVersionUID = 100L;

  private int facilityNo; // 숙소 시설번호

  @EqualsAndHashCode.Exclude
  private String facilityName; // 숙소 시설명

  @EqualsAndHashCode.Exclude
  private int facilityCount; // 시설 갯수
}
