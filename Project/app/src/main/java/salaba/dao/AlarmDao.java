package salaba.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import salaba.vo.Alarm;

@Mapper
public interface AlarmDao {
  void addNotifyHistory(Alarm alarm);

  List<Alarm> selectNotifyHistory(int memberNo);

  void updateNotifyHistory(char state, int notifyNo);
}
