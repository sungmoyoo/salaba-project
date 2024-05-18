package salaba.service;

import java.util.List;
import salaba.vo.PointHistory;

public interface PointHistoryService {

  List<PointHistory> selectUserPointHistory(int memberNo);

}
