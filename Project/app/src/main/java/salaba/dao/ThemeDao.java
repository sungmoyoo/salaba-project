package salaba.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import salaba.vo.host.HostReservation;
import salaba.vo.rentalHome.RentalHome;
import salaba.vo.rentalHome.RentalHomeFacility;
import salaba.vo.rentalHome.Theme;

@Mapper
public interface ThemeDao {
  void addTheme(List<Theme> themes); // 숙소 테마 추가

  List<Theme> findAllTheme(); // 전체 테마 리스트
}
