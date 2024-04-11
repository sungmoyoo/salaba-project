package salaba.service;

import java.util.List;
import salaba.vo.Member;
import salaba.vo.Point_history;

public interface Point_historyService {

  List<Point_history> list(Point_history pointHistory);

}
