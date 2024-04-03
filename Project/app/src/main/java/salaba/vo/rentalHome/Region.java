package salaba.vo.rentalHome;

import java.io.Serializable;
import lombok.Data;

@Data
public class Region implements Serializable { // 지역

  private static final long serialVersionUID = 100L;

  int regionNo;

  String regionName;

  Nation nation;

}