package salaba.service;

import java.util.List;
import salaba.vo.Reservation;

public interface ReservationService {

  List<Reservation> selectReservationList(int memberNo);

  Reservation selectReservation(int reservationNo);

  int updateReservationForCancel(int reservationNo);
}
