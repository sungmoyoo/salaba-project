package salaba.service;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import salaba.vo.rental_home.RentalHome;

public interface BookmarkService {

  List<RentalHome> selectUserBookMark(int memberNo);
  int deleteBookMark(int memberNo , int rentalHomeNo);

  int selectOneBookMark(int memberNo, int rentalHomeNo);

  int addBookMark(int memberNo, int rentalHomeNo);

}
