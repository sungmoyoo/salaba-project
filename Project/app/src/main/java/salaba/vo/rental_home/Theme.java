package salaba.vo.rental_home;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Theme implements Serializable { // 테마
  private static final long serialVersionUID = 100L;

  private int themeNo; // 테마번호
  
  private String themeName; // 테마명
}
