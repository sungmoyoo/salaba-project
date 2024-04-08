package salaba.service;

import java.util.List;
import salaba.vo.host.HostReservation;
import salaba.vo.rentalHome.RentalHome;
import salaba.vo.rentalHome.RentalHomeFacility;
import salaba.vo.rentalHome.Theme;

// 호스트 예약관리 서비스 인터페이스
public interface HostService {
  void rentalHomeAdd(RentalHome rentalHome); // 숙소 추가(기본정보, 시설, 테마 따로 DB에 저장)

//  int rentalHomeUpdate(RentalHome rentalHome);

  RentalHome getRentalHome(int rentalHomeNo);

  List<Theme> themeList(); // 전체 테마 리스트

  List<RentalHomeFacility> facilityList(); // 전체 숙소 시설 리스트

  List<RentalHome> rentalHomeList(int hostNo); // 호스트 숙소 리스트

  List<HostReservation> list(int hostNo); // 호스트 예약내역 리스트

  int stateUpdate(int state, int reservationNo); // 호스트 예약상태 업데이트



}
