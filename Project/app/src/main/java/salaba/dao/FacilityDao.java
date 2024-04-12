package salaba.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import salaba.vo.host.HostReservation;
import salaba.vo.rentalHome.RentalHome;
import salaba.vo.rentalHome.RentalHomeFacility;

@Mapper
public interface FacilityDao {
  void addFacility(RentalHome rentalHome);

  int deleteAllFacility(int rentalHomeNo);

  List<RentalHomeFacility> findAllFacility();


}
