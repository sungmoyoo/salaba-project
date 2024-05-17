package salaba.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import salaba.dao.PointHistoryDao;
import salaba.service.PointHistoryService;
import salaba.vo.PointHistory;

@RequiredArgsConstructor
@Service
public class DefaultPointHistoryService implements PointHistoryService {

  private final PointHistoryDao pointHistoryDao;

  @Override
  public List<PointHistory> selectUserPointHistory(int memberNo) {
    return pointHistoryDao.selectUserPointHistory(memberNo);
  }

}


