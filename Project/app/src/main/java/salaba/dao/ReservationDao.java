package salaba.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import salaba.vo.Reservation;

@Mapper
public interface ReservationDao {


  List<Reservation> selectReservationList(@Param("memberNo") int memberNo);

  Reservation selectReservation(@Param("reservationNo") int reservationNo);

  int updateReservationForCancel(@Param("reservationNo") int reservationNo);
}
