package salaba.dao;

import java.io.File;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import salaba.vo.rentalHome.RentalHomePhoto;
import salaba.vo.rentalHome.Theme;

@Mapper
public interface PhotoDao {
  void addPhoto(List<RentalHomePhoto> Photos); // 숙소 사진 추가

}
