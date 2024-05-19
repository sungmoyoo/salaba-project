package salaba.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import salaba.dao.ReservationDao;
import salaba.service.ReservationService;
import salaba.vo.Reservation;

@RequiredArgsConstructor
@Service
public class DefaultReservationService implements ReservationService {

  private final ReservationDao reservationDao;

  @Override
  public List<Reservation> selectReservationList(int memberNo) {
    return reservationDao.selectReservationList(memberNo);
  }

  @Override
  public Reservation selectReservation(int reservationNo) {
    return reservationDao.selectReservation(reservationNo);
  }

  @Override
  public int updateReservationForCancel(int reservationNo) {
    return reservationDao.updateReservationForCancel(reservationNo);
  }
}