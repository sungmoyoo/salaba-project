package salaba.vo.rental_home;

import java.io.Serializable;
import lombok.Data;

@Data
public class National implements Serializable {
  private static final long serialVersionUID = 100L;
  
  private int nationalNo; // 국가번호
  
  private String nationalName; // 국가명
}
