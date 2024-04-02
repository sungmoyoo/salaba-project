package salaba.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import salaba.dao.HostReservationDao;
import salaba.service.HostReservationService;
import salaba.vo.HostReservation;

@RequiredArgsConstructor
@Service
public class DefaultHostReservation implements HostReservationService {

  private static final Log log = LogFactory.getLog(DefaultHostReservation.class);
  private final HostReservationDao hostReservationDao;

  @Override
  public List<HostReservation> list(int hostNo) {
    return hostReservationDao.findAll(hostNo);
  }

  @Transactional
  @Override
  public int stateUpdate(int state, int reservationNo) {
    return hostReservationDao.update(state, reservationNo);
  }
}
