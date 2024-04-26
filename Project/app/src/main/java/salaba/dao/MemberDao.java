package salaba.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import salaba.vo.Member;
import salaba.vo.Nation;
import salaba.vo.rental_home.Theme;

@Mapper
public interface MemberDao {

  public void add(Member member);

  public Member selectMemberInfo(int no);

  public int myinfoUpdate(Member member);

  public Member checkNickname(String nickname);

  public List<Nation> getNation();

  public int delete(Member member);

  public Member findByEmailAndPassword(
      @Param("email") String email,
      @Param("password") String password);

  public Member findEmail(Member member);

  public Member findPw(Member member);

  public void chgPwSave(Member member);

  public Member chkPw(Member member);

  public void insertPreference(Member member);

  public void deletePreference(Member member);

  List<Member> findAllTheme(Member sessionInfo);

}
