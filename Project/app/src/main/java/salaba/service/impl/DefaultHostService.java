package salaba.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import salaba.dao.FacilityDao;
import salaba.dao.HostDao;
import salaba.dao.ThemeDao;
import salaba.service.HostService;
import salaba.vo.host.HostReservation;
import salaba.vo.rentalHome.RentalHome;
import salaba.vo.rentalHome.RentalHomeFacility;
import salaba.vo.rentalHome.Theme;

//호스트 예약관리 서비스 구현체
@RequiredArgsConstructor
@Service
public class DefaultHostService implements HostService {

  private static final Log log = LogFactory.getLog(DefaultHostService.class);
  private final HostDao hostDao;
  private final ThemeDao themeDao;
  private final FacilityDao facilityDao;

  // 호스트 숙소등록
  @Override
  public void addRentalHome(RentalHome rentalHome) {
    hostDao.addHome(rentalHome);
    themeDao.addTheme(rentalHome.getRentalHomeThemes());
    facilityDao.addFacility(rentalHome.getRentalHomeFacilities());
  }

  // 전체 테마를 가져오는 메서드
  @Override
  public List<Theme> themeList() {
    return themeDao.findAllTheme();
  }

  // 전체 편의시설을 가져오는 메서드
  @Override
  public List<RentalHomeFacility> facilityList() {
    return facilityDao.findAllFacility();
  }

  // 호스트의 전체 숙소를 가져오는 메서드
  @Override
  public List<RentalHome> rentalHomeList(int hostNo) {
    return hostDao.findAllRentalHome(hostNo);
  }

  // 호스트의 전체 예약정보를 가져오는 메서드
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
