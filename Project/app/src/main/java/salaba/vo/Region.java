package salaba.vo;

import lombok.Data;

@Data
public class Region {
  private static final long serialVersionUID = 100L;

  private int regionNo;
  private String regionName;
  private Nation nation;
}
