package salaba.vo.rentalHome;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import lombok.Data;
import org.springframework.ui.context.Theme;


@Data
public class RentalHome implements Serializable { // 숙소

  private static final long serialVersionUID = 100L;

  private int rentalHomeNo; // 숙소번호

  private int regionNo; // 지역

  private String name; // 숙소 이름

  private int memberNo; // 호스트 회원번호

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

  List<RentalHomeFacility> rentalHomeFacilities; // 숙소 편의 시설

  List<RentalHomePhoto> rentalHomePhotos; // 숙소 사진

  List<Theme> RentalHomeThemes; // 숙소 테마

  List<RentalHomeReview> rentalHomeReviews; // 숙소 리뷰

  private int rentalHomeLikeCount; // 숙소 추천수
}
