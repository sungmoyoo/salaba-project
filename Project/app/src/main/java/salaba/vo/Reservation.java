package salaba.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.Data;
import salaba.vo.rental_home.RentalHomePhoto;

@Data
public class Reservation implements Serializable {

  private static final long serialVersionUID = 100L;

  private String name; // 숙소명
  private int reservationNo; // 예약 번호
  private int no; // 로그인 회원번호
  private String startDate; // 체크인
  private String endDate; // 체크아웃
  private String numberOfPeople; // 인원수
  private String state; // 예약상태
  private String stateNm; // 예약상태 명
  private String photo; // 회원사진파일 명
  private String lat;
  private String lon;
  private List<RentalHomePhoto> photoList; //숙소사진파일 리스트

  private String paymentDate; // 결제일
  private String nickname; // 닉네임
  private String telNo; // 전화번호
  private String rentalHomeRule; // 이용규칙
  private String address; // 주소
  private String amount; // 결제금액
  private String rtnDate; // 환불정책 일자

}
