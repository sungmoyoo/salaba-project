package salaba.service;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import salaba.vo.rental_home.RentalHome;

public interface BookmarkService {

  List<RentalHome> selectUserBookMark(int memberNo);
  int deleteBookMark(@Param("memberNo") int memberNo , @Param("rentalHomeNo") int rentalHomeNo);
}
