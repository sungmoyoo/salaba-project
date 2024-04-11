package salaba.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import salaba.dao.Point_historyDao;
import salaba.service.Point_historyService;
import salaba.vo.Point_history;

@RequiredArgsConstructor
@Service
public class DefaultPoint_historyService implements Point_historyService {

  private final Point_historyDao point_historyDao;

  @Override
  public List<Point_history> list(Point_history pointHistory) {
    return point_historyDao.findAll(pointHistory);
  }

}


