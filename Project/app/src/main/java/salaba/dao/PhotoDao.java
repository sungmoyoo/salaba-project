package salaba.dao;

import java.io.File;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.expression.spel.ast.Literal;
import salaba.vo.rentalHome.RentalHome;
import salaba.vo.rentalHome.RentalHomePhoto;
import salaba.vo.rentalHome.Theme;

@Mapper
public interface PhotoDao {
  void addPhoto(RentalHome rentalHome); // 숙소 사진 추가

  int deletePhoto(int photoNo); // 숙소 사진 삭제
}
