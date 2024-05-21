package salaba.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import salaba.vo.rental_home.RentalHome;

@Mapper
public interface BookmarkDao {


  List<RentalHome> selectUserBookMark(@Param("memberNo") int memberNo);

  int deleteBookMark(@Param("memberNo") int memberNo , @Param("rentalHomeNo") int rentalHomeNo);

  int selectOneBookMark(@Param("memberNo") int memberNo, @Param("rentalHomeNo") int rentalHomeNo);

  int addBookMark(@Param("memberNo") int memberNo, @Param("rentalHomeNo") int rentalHomeNo);
}
