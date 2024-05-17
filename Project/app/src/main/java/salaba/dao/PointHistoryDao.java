package salaba.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import salaba.vo.PointHistory;

@Mapper
public interface PointHistoryDao {

  List<PointHistory> selectUserPointHistory(@Param("memberNo") int memberNo);

}
