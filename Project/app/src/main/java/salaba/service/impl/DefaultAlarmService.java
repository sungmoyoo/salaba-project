package salaba.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import salaba.dao.AlarmDao;
import salaba.service.AlarmService;
import salaba.vo.Alarm;

@RequiredArgsConstructor // 생성자 자동생성
@Service
public class DefaultAlarmService implements AlarmService {

  private final AlarmDao alarmDao;

  @Override
  public void addNotifyHistory(Alarm alarm) {
    alarmDao.addNotifyHistory(alarm);
  }

  @Override
  public List<Alarm> selectNotifyHistory(int memberNo) {
    return alarmDao.selectNotifyHistory(memberNo);
  }

  @Override
  public void updateNotifyHistory(char state, int notifyNo) {
    alarmDao.updateNotifyHistory(state, notifyNo);
  }
}
