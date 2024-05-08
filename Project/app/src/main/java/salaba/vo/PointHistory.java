package salaba.vo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@Data
public class PointHistory implements Serializable {

  private static final long serialVersionUID = 100L;

  private int no; // 회원 번호
  private String saveContent; // 적립 내용
  private int savePoint; // 적립 포인트
  private Date saveDate; // 적립 날짜
  private int totalPoint; // 누적 포인트

  private String myInfoMenuId;//좌측 메뉴 클릭 상태 표시를 위한 변수

}
