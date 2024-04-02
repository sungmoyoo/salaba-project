package salaba.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import salaba.vo.HostReservation;

@Mapper
public interface HostReservationDao {
  List<HostReservation> findAll(int hostNo);

  int update(
      @Param("state")int state,
      @Param("reservationNo") int reservationNo);

}
