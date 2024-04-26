package salaba.service;

import java.util.List;
import salaba.vo.Reservation;
import salaba.vo.rental_home.RentalHome;

public interface ReservationService {

  List<Reservation> reservationList(Reservation reservation);

  Reservation get(int reservationNo);

}
