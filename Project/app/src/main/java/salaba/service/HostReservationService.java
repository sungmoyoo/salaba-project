package salaba.service;

import java.util.List;
import salaba.vo.HostReservation;

public interface HostReservationService {

  List<HostReservation> list(int hostNo);

  int stateUpdate(int state, int reservationNo);
}
