package salaba.vo.rental_home;

import java.io.Serializable;
import java.sql.Date;
import lombok.Data;

@Data
public class RentalHomeReview implements Serializable {

  private static final long serialVersionUID = 100L;
  
  private int reservationNo; // 예약 번호
  
  private Date createdDate; // 작성 일시
  
  private int score; // 평점
  
  private String review; // 리뷰 내용
  
  private char state; // 상태
}
