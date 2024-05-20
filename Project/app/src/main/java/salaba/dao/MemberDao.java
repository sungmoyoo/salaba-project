package salaba.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import salaba.vo.Alarm;
import salaba.vo.Member;
import salaba.vo.Nation;
import salaba.vo.board.Board;


@Mapper
public interface MemberDao {

  int addMember(Member member);

  Member selectMemberInfo(int no);

  int updateUserInfo(Member member);

  int checkNickname(String nickname);

  int checkEmail(String email);

  List<Nation> getNation();

  int updateMemberWithdrawal(@Param("memberNo") int memberNo);

  Member selectUserInfoForLogin(
      @Param("email") String email,
      @Param("password") String password);

  Member selectUserInfoForUpdateSession(@Param("memberNo") int memberNo);

  Member findEmail(Member member);

  Member findPassword(Member member);

  void changePasswordSave(Member member);

  int checkPassword(@Param("no") int memberNo, @Param("password") String password);

  void insertPreference(Member member);

  void deletePreference(@Param("no") int memberNo);

  String boardStateCheck(Board board);

  Member selectEmailForGoogle(@Param("email") String email);
}
