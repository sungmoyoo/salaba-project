package salaba.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import salaba.dao.HostDao;
import salaba.service.HostService;
import salaba.vo.host.HostReservation;
import salaba.vo.rentalHome.RentalHome;
import salaba.vo.rentalHome.RentalHomeTheme;

//호스트 예약관리 서비스 구현체
@RequiredArgsConstructor
@Service
public class DefaultHostService implements HostService {

  private static final Log log = LogFactory.getLog(DefaultHostService.class);
  private final HostDao hostDao;

  // 호스트 숙소등록
  @Override
  public void rentalHomeAdd(RentalHome rentalHome) {
    hostDao.addHome(rentalHome);
  }

  @Override
  public List<RentalHomeTheme> themeList() {
    return hostDao.findAllTheme();
  }

  // DB에서 해당 회원번호가 가진 모든 예약정보를 받아오는 메서드
  @Override
  public List<HostReservation> list(int hostNo) {
    return hostDao.findAllReservation(hostNo);
  }

  // 예약내역을 업데이트하는 DAO 호출하는 메서드
  @Transactional
  @Override
  public int stateUpdate(int state, int reservationNo) {
    return hostDao.stateUpdate(state, reservationNo);
  }



}
