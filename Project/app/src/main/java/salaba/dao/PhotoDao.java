package salaba.dao;


import org.apache.ibatis.annotations.Mapper;
import salaba.vo.rental_home.RentalHome;


@Mapper
public interface PhotoDao {

  void addPhoto(RentalHome rentalHome); // 숙소 사진 추가

  int deletePhoto(String uuidPhotoName); // 숙소 사진 삭제

}
