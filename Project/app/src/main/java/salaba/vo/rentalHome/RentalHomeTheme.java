package salaba.vo.rentalHome;

import java.io.Serializable;
import lombok.Data;

@Data
public class RentalHomeTheme implements Serializable { // 지역

  private static final long serialVersionUID = 100L;

  private int themeNo;

  private String themeName;
}
