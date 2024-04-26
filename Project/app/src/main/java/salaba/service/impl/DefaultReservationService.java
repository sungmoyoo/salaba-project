package salaba.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import salaba.dao.QuestionDao;
import salaba.dao.ReservationDao;
import salaba.service.QuestionService;
import salaba.service.ReservationService;
import salaba.vo.Question;
import salaba.vo.Reservation;
import salaba.vo.rental_home.RentalHome;

@RequiredArgsConstructor
@Service
public class DefaultReservationService implements ReservationService {

  private final ReservationDao reservationDao;

  @Override
  public List<Reservation> reservationList(Reservation reservation) {
    return reservationDao.findAll(reservation);
  }

  @Override
  public Reservation get(int reservationNo) {
    return reservationDao.findBy(reservationNo);
  }
}