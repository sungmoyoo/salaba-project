package salaba.vo.rentalHome;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class Theme implements Serializable { // 지역

  private static final long serialVersionUID = 100L;

  private int themeNo;

  @EqualsAndHashCode.Exclude
  private String themeName;
}
