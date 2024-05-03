package salaba.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.Data;
import salaba.vo.rental_home.Theme;


@Data
public class Grade implements Serializable {

  private static final long serialVersionUID = 100L;

  private int gradeNo; // 등급번호
  private String gradeName; // 등급명

}
