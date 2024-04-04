package salaba.service.impl;

import java.sql.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import salaba.dao.RentalHomeDao;
import salaba.service.RentalHomeService;
import salaba.vo.rental_home.RentalHome;
import salaba.vo.rental_home.RentalHomeReport;
import salaba.vo.rental_home.RentalHomeReview;
import salaba.vo.rental_home.Theme;

@RequiredArgsConstructor
@Service
public class DefaultRentalHomeService implements RentalHomeService {

  private final static Log log = LogFactory.getLog(DefaultRentalHomeService.class);
  private final RentalHomeDao rentalHomeDao;

  @Override
  public List<RentalHome> getRentalHomeMain() {
    return rentalHomeDao.rentalHomeDefaultSelect(); // 비로그인 숙소 목록
  }

  @Override
  public List<RentalHome> getRentalHomeMainForLoginUser(List<Theme> themes) {
    return rentalHomeDao.rentalHomeSelectForMember(themes); // 로그인 숙소 목록(선호 사항을 선택한 유저 한정)
  }

  @Override
  public List<RentalHome> getRentalHomeConditionSearch(String regionName, Date checkInDate,
      Date checkOutDate, int capacity) { // 숙소 조건 검색
    return rentalHomeDao.rentalHomeConditionSelect(regionName,checkInDate,checkOutDate,capacity);
  }

  @Override
  public RentalHome getRentalHomeThemeSearch(String themeName) {
    return rentalHomeDao.rentalHomeThemeSelect(themeName);
  }

  @Override
  public RentalHome getRentalHomeDetailView(int rentalHomeNo) { // 숙소 상세 조회
    return rentalHomeDao.rentalHomeDetailSelect(rentalHomeNo);
  }

  @Override
  public void addRentalHomeReview(RentalHomeReview rentalHomeReview) { // 숙소 리뷰 작성
    rentalHomeDao.rentalHomeReviewAdd(rentalHomeReview);
  }

  @Override
  public void addRentalHomeReport(RentalHomeReport rentalHomeReport) { // 숙소 신고
    rentalHomeDao.rentalHomeReportAdd(rentalHomeReport);
  }

  @Override
  public List<RentalHomeReview> getRentalHomeReviewList(int rentalHomeNo) { // 숙소 리뷰 조회
    return rentalHomeDao.rentalHomeReviewSelect(rentalHomeNo);
  }
}
