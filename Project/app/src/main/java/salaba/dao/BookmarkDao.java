package salaba.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import salaba.vo.Bookmark;
import salaba.vo.Reservation;

@Mapper
public interface BookmarkDao {


  List<Bookmark> findAll(Bookmark bookmark);

}
