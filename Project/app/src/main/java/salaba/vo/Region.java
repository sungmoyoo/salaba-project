package salaba.vo;

import java.io.Serializable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Region implements Serializable {
  private static final long serialVersionUID = 100L;

  private int regionNo;
  private String regionName;
}
