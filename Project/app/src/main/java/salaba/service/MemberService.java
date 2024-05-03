package salaba.service;

import java.util.List;
import salaba.vo.Alarm;
import salaba.vo.Member;
import salaba.vo.Nation;
import salaba.vo.rental_home.Theme;

public interface MemberService {

  void add(Member member);

  Member get(int no);

  int myinfoUpdate(Member member);

  Member checkNickname(String nickname);

  List<Nation> getNation();

  int delete(Member member);

  Member get(String email, String password);

  Member findEmail(Member member);

  Member findPw(Member member);

  void chgPwSave(Member member);

  Member chkPw(Member member);

  void insertPreference(Member member);

  void deletePreference(Member member);

  List<Member> themeList(Member sessionInfo);

  void insertNotifyHistory(Alarm alarm);

  List<Alarm> selectNotifyHistory(int memberNo);

  void updateNotifyHistory(int notifyNo);
}
