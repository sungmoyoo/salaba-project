package salaba.vo.host;

import java.io.Serializable;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;

@Data
public class HostReservation implements Serializable { // 호스트 예약내역 (결제완료)

  private static final long serialVersionUID = 100L;

  private String rentalHomeName; //숙소 이름
  private String memberName; // 예약자명
  private int reservationNo; // 예약 번호
  private int memberNo; // 예약자번호
  private int hostNo; // 호스트명
  private int rentalHomeNo; // 숙소 번호
  private Date startDate; // 시작일
  private Date endDate; // 종료일
  private int state; // 상태
  private Date paymentDate; // 결제일
  private int amount; // 결제금액
  private int numberOfPeople; // 이용 인원수
  
  // WebSocketSession은 Spring에서 Websocket Connection이 맺어진 세션
  // Stomp를 사용할 경우 이 세션이 있어야
  // 메시지 브로커가 해당 세션에 있는 클라이언트들에게 메시지를 전달한다.
//  private Set<WebSocketSession> sessions = new HashSet<>();


}
