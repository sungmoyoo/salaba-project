package salaba.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import salaba.vo.host.HostReservation;
import salaba.vo.rentalHome.RentalHome;
import salaba.vo.rentalHome.RentalHomeFacility;
import salaba.vo.rentalHome.Theme;

@Mapper
public interface HostDao {
  void addHome(RentalHome rentalHome); // 숙소 기본정보 추가

  List<HostReservation> findAllReservation(int hostNo); // 숙소 예약내역 리스트

  List<RentalHome> findAllRentalHome(int hostNo); // 숙소 정보 리스트(기본정보,시설,테마 모두)

  // 예약 상태 업데이트
  int stateUpdate(
      @Param("state")int state,
      @Param("reservationNo") int reservationNo);
}
