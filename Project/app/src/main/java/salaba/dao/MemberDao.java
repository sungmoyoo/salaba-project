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

  void add(Member member);

  Member selectMemberInfo(int no);

  int myinfoUpdate(Member member);

  int checkNickname(String nickname);

  int checkEmail(String email);

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

  String boardStateCheck(Board board);

  List<Board> searchByKeyword(@Param("keyword") String keyword, @Param("type") String type); // 검색

  int countFiltered(String type, String keyword); // 검색으로 필터링해 페이징 처리
}
