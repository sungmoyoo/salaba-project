package salaba.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import salaba.vo.host.HostReservation;
import salaba.vo.rental_home.RentalHome;


@Mapper
public interface HostDao {

  void addHome(RentalHome rentalHome, @Param("hostNo") int hostNo); // 숙소 기본정보 추가

  List<HostReservation> findAllReservation(int hostNo); // 숙소 예약내역 리스트

  List<RentalHome> findAllRentalHome(int hostNo); // 숙소 정보 리스트(기본정보,시설,테마 모두)

  RentalHome findByRentalHomeNo(int rentalHomeNo); // 숙소 번호로 찾기

  int updateRentalHome(RentalHome rentalHome); // 숙소 정보 업데이트

  int rentalHomeStateUpdate( // 숙소 상태 업데이트
      @Param("state") char state,
      @Param("rentalHomeNo") int rentalHomeNo
  );

  int reservationStateUpdate( // 예약 상태 업데이트
      @Param("state") char state,
      @Param("reservationNo") int reservationNo);

  int deleteRentalHome(int rentalHomeNo); // 숙소 삭제

}
