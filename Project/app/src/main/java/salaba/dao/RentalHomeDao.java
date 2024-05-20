package salaba.dao;

import java.sql.Date;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import salaba.vo.Region;
import salaba.vo.rental_home.RentalHome;
import salaba.vo.rental_home.RentalHomeFacility;
import salaba.vo.rental_home.RentalHomePhoto;
import salaba.vo.rental_home.RentalHomeReport;
import salaba.vo.rental_home.RentalHomeReview;
import salaba.vo.rental_home.Theme;

@Mapper
public interface RentalHomeDao {

  // 숙소 조회 메인(추천수)
  List<RentalHome> rentalHomeDefaultSelect();

  // 숙소 조회 메인(로그인 유저 선호 사항으로 목록 출력)
  List<RentalHome> rentalHomeSelectForMember(List<Theme> themes);

  // 숙소 테마별 조회
  List<RentalHome> rentalHomeThemeSelect(@Param("themeName") String themeName);

  // 숙소 지역,기간,인원수 조회
  List<RentalHome> rentalHomeConditionSelect(
      @Param("regionName") String regionName,
      @Param("checkInDate") Date checkInDate,
      @Param("checkOutDate") Date checkOutDate,
      @Param("capacity") int capacity);

  // 숙소 상세 조회
  RentalHome rentalHomeDetailSelect(int rentalHomeNo);

  // 숙소 리뷰 조회
  List<RentalHomeReview> rentalHomeReviewSelect(@Param("rental_home_no") int rentalHomeNo);

  // 숙소 리뷰 평균 조회
  double rentalHomeReviewAverage(int rentalHomeNo);

  // 숙소 시설 조회
  List<RentalHomeFacility> rentalHomeFacilitySelect(int rentalHomeNo);

  // 숙소 사진 조회
  List<RentalHomePhoto> rentalHomePhotoSelect(int rentalHomeNo);

  // 테마 전체 조회
  List<Theme> getAllThemes();

  // 편의 시설 전체 조회
  List<RentalHomeFacility> getAllFacilities();

  // 숙소 리뷰 작성
  void rentalHomeReviewAdd(RentalHomeReview rentalHomeReview);

  // 숙소 신고
  void rentalHomeReportAdd(RentalHomeReport rentalHomeReport);

  List<Region> getAllRegion();

  RentalHome getReservationInfo(int rentalHomeNo);
}
