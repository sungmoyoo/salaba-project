package salaba.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import salaba.vo.rental_home.RentalHome;
import salaba.vo.rental_home.Theme;


@Mapper
public interface ThemeDao {
  void addTheme(RentalHome rentalHome); // 숙소 테마 추가

  int deleteAllTheme(int rentalHomeNo); // 숙소 테마 전체 삭제

  List<Theme> findAllTheme(); // 전체 테마 리스트


}
