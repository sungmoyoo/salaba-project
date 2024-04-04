package salaba.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import salaba.vo.host.HostReservation;
import salaba.vo.rentalHome.RentalHome;
import salaba.vo.rentalHome.RentalHomeTheme;

@Mapper
public interface HostDao {
  void addHome(RentalHome rentalHome);

  List<RentalHomeTheme> findAllTheme();

  List<HostReservation> findAllReservation(int hostNo);

  int stateUpdate(
      @Param("state")int state,
      @Param("reservationNo") int reservationNo);

}
