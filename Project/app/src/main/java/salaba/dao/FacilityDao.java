package salaba.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import salaba.vo.rental_home.RentalHome;
import salaba.vo.rental_home.RentalHomeFacility;


@Mapper
public interface FacilityDao {
  void addFacility(RentalHome rentalHome);

  int deleteAllFacility(int rentalHomeNo);

  List<RentalHomeFacility> findAllFacility();


}
