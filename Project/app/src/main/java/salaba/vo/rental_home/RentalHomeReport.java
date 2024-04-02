package salaba.vo.rental_home;

import java.io.Serializable;
import lombok.Data;

@Data
public class RentalHomeReport implements Serializable { // 숙소 신고
  private static final long serialVersionUID = 100L;

  private int rentalHomeNo; // 숙소 번호

  private int memberNo; // 회원 번호

  private String content; // 내용
  
  private int reportCategoryNo; // 신고 카테고리 번호
}
