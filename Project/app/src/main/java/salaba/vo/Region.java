package salaba.vo;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Region implements Serializable {
  private static final long serialVersionUID = 100L;

  private int regionNo;
  private String regionName;
  private int nationNo; // 국가 번호

}
