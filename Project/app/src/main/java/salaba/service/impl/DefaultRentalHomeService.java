package salaba.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import salaba.dao.RentalHomeDao;
import salaba.service.RentalHomeService;
import salaba.vo.rental_home.RentalHome;
import salaba.vo.rental_home.RentalHomeReview;
import salaba.vo.rental_home.Theme;

@RequiredArgsConstructor
@Service
public class DefaultRentalHomeService implements RentalHomeService {

  private final RentalHomeDao rentalHomeDao;

  @Override
  public RentalHome getRentalHomeMain() {
    return rentalHomeDao.rentalHomeDefaultSelect(); // 비로그인 숙소 목록
  }

  @Override
  public RentalHome getRentalHomeMainForLoginUser(List<Theme> themes) {
    return rentalHomeDao.rentalHomeSelectForMember(themes); // 로그인 숙소 목록(선호 사항을 선택한 유저 한정)
  }

  @Override
  public RentalHome getRentalHomeDetailView(int rentalHomeNo) { // 숙소 상세 조회
    return rentalHomeDao.rentalHomeDetailSelect(rentalHomeNo);
  }


  @Override
  public void addRentalHomeReview(RentalHomeReview rentalHomeReview) { // 숙소 리뷰 작성
    rentalHomeDao.rentalHomeReviewAdd(rentalHomeReview);
  }
}
