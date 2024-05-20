package salaba.vo.rental_home;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import salaba.vo.Region;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RentalHome implements Serializable { // 숙소
  private static final long serialVersionUID = 100L;

  private int rentalHomeNo; // 숙소번호

  private Region region; // 지역
  
  private String name; // 숙소 이름
  
  private String explanation; // 숙소 설명
  
  private String address; // 상세 주소
  
  private int price; // 가격
  
  private int capacity; // 수용인원
  
  private String lat; // 위도
  
  private String lon; // 경도
  
  private char state; // 상태
  
  private Date hostingStartDate; // 호스팅 시작일
  
  private Date hostingEndDate; // 호스팅 종료일
  
  private Date registeDate; // 숙소 등록일
  
  private String rentalHomeRule; // 숙소 이용 규칙
  
  private int cleanFee; // 청소비

  private List<RentalHomeFacility> rentalHomeFacilities; // 숙소 편의 시설

  private List<RentalHomePhoto> rentalHomePhotos; // 숙소 사진

  private List<Theme> themes; // 숙소 테마

  private List<RentalHomeReview> rentalHomeReviews; // 숙소 리뷰
  
  private int rentalHomeLikeCount; // 숙소 추천수
  
  private Date checkInDate; // 체크인 날짜
  
  private Date checkOutDate; // 체크아웃 날짜
}
