package salaba.vo.rental_home;

import java.io.Serializable;
import lombok.Data;

@Data
public class Region implements Serializable { // 지역
  private static final long serialVersionUID = 100L;

  private int regionNo; // 지역번호
  
  private String regionName; // 지역명
  
  National national; // 국가
}
