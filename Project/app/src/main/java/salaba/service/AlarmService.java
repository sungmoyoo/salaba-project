package salaba.service;

import java.util.List;
import salaba.vo.Alarm;

public interface AlarmService {

  void addNotifyHistory(Alarm alarm);

  List<Alarm> selectNotifyHistory(int memberNo);

  void updateNotifyHistory(char state, int notifyNo);

}
