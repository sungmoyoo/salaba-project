package salaba.service;

import java.util.List;
import salaba.vo.Reservation;
import salaba.vo.rental_home.RentalHome;

public interface ReservationService {

  List<Reservation> selectReservationList(int memberNo);

  Reservation selectReservation(int reservationNo);

}
