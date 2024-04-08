package salaba.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import salaba.dao.FacilityDao;
import salaba.dao.HostDao;
import salaba.dao.PhotoDao;
import salaba.dao.ThemeDao;
import salaba.service.HostService;
import salaba.vo.host.HostReservation;
import salaba.vo.rentalHome.RentalHome;
import salaba.vo.rentalHome.RentalHomeFacility;
import salaba.vo.rentalHome.RentalHomePhoto;
import salaba.vo.rentalHome.Theme;

//호스트 예약관리 서비스 구현체
@RequiredArgsConstructor
@Service
public class DefaultHostService implements HostService {

  private static final Log log = LogFactory.getLog(DefaultHostService.class);
  private final HostDao hostDao;
  private final ThemeDao themeDao;
  private final FacilityDao facilityDao;
  private final PhotoDao photoDao;

  // 호스트 숙소등록
  @Override
  public void rentalHomeAdd(RentalHome rentalHome) {
    hostDao.addHome(rentalHome);


    // Null 검사는 안함 -> 모두 N.N이기 때문
    // 사진,시설,테마 객체리스트에 숙소번호 추가하여 insert
    for (RentalHomePhoto rentalHomePhoto : rentalHome.getRentalHomePhotos()) {
      rentalHomePhoto.setRentalHomeNo(rentalHome.getRentalHomeNo());
    }
    photoDao.addPhoto(rentalHome.getRentalHomePhotos());

    for (Theme theme : rentalHome.getRentalHomeThemes()) {
      theme.setRentalHomeNo(rentalHome.getRentalHomeNo());
    }
    themeDao.addTheme(rentalHome.getRentalHomeThemes());

    for (RentalHomeFacility rentalHomeFacility : rentalHome.getRentalHomeFacilities()) {
      rentalHomeFacility.setRentalHomeNo(rentalHome.getRentalHomeNo());
    }
    facilityDao.addFacility(rentalHome.getRentalHomeFacilities());
  }

  @Override
  public RentalHome getRentalHome(int rentalHomeNo) {
    return hostDao.findByRentalHomeNo(rentalHomeNo);
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
