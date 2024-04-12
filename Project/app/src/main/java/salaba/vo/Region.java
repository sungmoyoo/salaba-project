package salaba.vo;

import java.io.Serializable;
import lombok.Data;

@Data
public class Region implements Serializable {
  private static final long serialVersionUID = 100L;

  private int regionNo;
  private String regionName;
  private Nation nation;
}
