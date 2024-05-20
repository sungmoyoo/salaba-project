package salaba.vo;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import lombok.Data;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Nation implements Serializable { // 국가

  private static final long serialVersionUID = 100L;

  private int nationNo; // 국가 번호
  private String nationName; // 이름
  private List<Region> regionList; // 지역 정보 받아오기

}
