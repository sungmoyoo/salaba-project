package salaba.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import salaba.vo.PointHistory;

@Mapper
public interface PointHistoryDao {

  List<PointHistory> findAll(PointHistory pointHistory);

}
