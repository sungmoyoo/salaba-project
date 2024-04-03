package salaba.vo.rentalHome;

import java.io.Serializable;
import lombok.Data;

@Data
public class Nation implements Serializable { // 국가

  private static final long serialVersionUID = 100L;

  int nationNo;
  String nationName;

}
