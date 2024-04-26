package salaba.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import salaba.vo.Question;
import salaba.vo.Reservation;
import salaba.vo.rental_home.RentalHome;

@Mapper
public interface ReservationDao {


  List<Reservation> findAll(Reservation reservation);

  Reservation findBy(int reservationNo);

}
