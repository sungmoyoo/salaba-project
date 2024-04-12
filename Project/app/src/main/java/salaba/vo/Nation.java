package salaba.vo;

import java.io.Serializable;
import lombok.Data;


@Data
public class Nation implements Serializable { // 국가

  private static final long serialVersionUID = 100L;

  private int nationNo; // 국가 번호
  private String nationName; // 이름
}
