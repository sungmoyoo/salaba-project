package salaba.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import salaba.vo.Alarm;
import salaba.vo.Member;
import salaba.vo.Nation;


@Mapper
public interface MemberDao {

  void add(Member member);

  Member selectMemberInfo(int no);

  int myinfoUpdate(Member member);

  Member checkNickname(String nickname);

  List<Nation> getNation();

  int delete(Member member);

  Member findByEmailAndPassword(
      @Param("email") String email,
      @Param("password") String password);

  Member findEmail(Member member);

  public Member findPassword(Member member);

  public void changePasswordSave(Member member);

  public Member myinfoCheckPassword(Member member);

  void insertPreference(Member member);

  void deletePreference(Member member);

  List<Member> findAllTheme(Member sessionInfo);

  String getMemberPoint(Member member);

  Member getGrade(Member member);

  List<Member> findAllmyTheme(Member sessionInfo);

  void addNotifyHistory(Alarm alarm);

  List<Alarm> selectNotifyHistory(int memberNo);

  void updateNotifyHistory(char state, int notifyNo);
}
