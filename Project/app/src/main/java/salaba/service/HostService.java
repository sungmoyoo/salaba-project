package salaba.service;

import java.util.List;
import salaba.vo.host.HostReservation;
import salaba.vo.rentalHome.RentalHome;
import salaba.vo.rentalHome.RentalHomeTheme;

// 호스트 예약관리 서비스 인터페이스
public interface HostService {
  void rentalHomeAdd(RentalHome rentalHome);

  List<RentalHomeTheme> themeList();

  List<HostReservation> list(int hostNo); // 호스트 예약내역 리스트

  int stateUpdate(int state, int reservationNo); // 호스트 예약상태 업데이트


}
