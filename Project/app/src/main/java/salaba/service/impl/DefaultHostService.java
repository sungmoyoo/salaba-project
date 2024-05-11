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
import salaba.dao.RentalHomeDao;
import salaba.dao.ThemeDao;
import salaba.service.HostService;
import salaba.vo.Region;
import salaba.vo.host.HostReservation;
import salaba.vo.rental_home.RentalHome;
import salaba.vo.rental_home.RentalHomeFacility;
import salaba.vo.rental_home.Theme;

//호스트 예약관리 서비스 구현체
@RequiredArgsConstructor
@Service
public class DefaultHostService implements HostService {

  private static final Log log = LogFactory.getLog(DefaultHostService.class);
  private final HostDao hostDao;
  private final ThemeDao themeDao;
  private final FacilityDao facilityDao;
  private final PhotoDao photoDao;
  private final RentalHomeDao rentalHomeDao;

  // 호스트 숙소등록
  @Override
  public void rentalHomeAdd(RentalHome rentalHome, int hostNo) {
    hostDao.addHome(rentalHome, hostNo);

    // ParameterType을 사용해서 rentalHome 객체 자체를 파라미터로 넘겨 처리
    // Null 검사는 안함 -> 모두 N.N이기 때문

    photoDao.addPhoto(rentalHome);

    themeDao.deleteAllTheme(rentalHome.getRentalHomeNo());
    themeDao.addTheme(rentalHome);

    facilityDao.deleteAllFacility(rentalHome.getRentalHomeNo());
    facilityDao.addFacility(rentalHome);
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


  @Override
  public int rentalHomeUpdate(RentalHome rentalHome) {
    int count = hostDao.updateRentalHome(rentalHome);

    if (rentalHome.getRentalHomePhotos() != null) {
      photoDao.addPhoto(rentalHome);
    }

    themeDao.deleteAllTheme(rentalHome.getRentalHomeNo());
    themeDao.addTheme(rentalHome);

    facilityDao.deleteAllFacility(rentalHome.getRentalHomeNo());
    facilityDao.addFacility(rentalHome);

    return count;
  }

  // 호스트의 전체 예약정보를 가져오는 메서드
  @Override
  public List<HostReservation> reservationList(int hostNo) {
    return hostDao.findAllReservation(hostNo);
  }

  // 예약내역을 업데이트하는 DAO 호출하는 메서드
  @Transactional
  @Override
  public int reservationStateUpdate(char state, int reservationNo) {
    return hostDao.reservationStateUpdate(state, reservationNo);
  }

  // 예약내역을 업데이트하는 DAO 호출하는 메서드
  @Transactional
  @Override
  public int rentalHomeStateUpdate(char state, int reservationNo) {
    return hostDao.rentalHomeStateUpdate(state, reservationNo);
  }


  @Override
  public int delete(int rentalHomeNo) {
    return hostDao.deleteRentalHome(rentalHomeNo);
  }

  @Override
  public int deleteRentalHomePhotoByName(String uuidPhotoName) {
    return photoDao.deletePhoto(uuidPhotoName);
  }

  @Override
  public List<Region> regionList() {
    return rentalHomeDao.getAllRegion();
  }
}
