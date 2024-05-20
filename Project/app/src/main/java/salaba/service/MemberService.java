package salaba.service;

import java.util.List;
import salaba.vo.Alarm;
import salaba.vo.Member;
import salaba.vo.Nation;
import salaba.vo.board.Board;

public interface MemberService {

  int addMember(Member member);

  Member selectUserInfoForLogin(int no);

  int updateUserInfo(Member member);

  int checkNickname(String nickname);

  int checkEmail(String email);

  List<Nation> getNation();

  int updateMemberWithdrawal(int memberNo);

  Member selectUserInfoForLogin(String email, String password);

  Member selectUserInfoForUpdateSession(int memberNo);

  Member findEmail(Member member);

  Member findPassword(Member member);

  void changePasswordSave(Member member);

  int checkPassword(int memberNo, String password);

  void insertPreference(Member member);

  void deletePreference(int memberNo);

  String boardStateCheck(Board board);

  Member selectEmailForGoogle(String email);

}
