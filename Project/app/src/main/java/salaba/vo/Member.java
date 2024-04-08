package salaba.vo;

import java.util.List;
import lombok.Data;
import salaba.vo.rental_home.Theme;

@Data
public class Member {
  private int memberNo;
  List<Theme> themes;
}
