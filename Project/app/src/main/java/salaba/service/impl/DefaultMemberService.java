package salaba.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import salaba.dao.MemberDao;
import salaba.service.MemberService;
import salaba.vo.ConstVO;
import salaba.vo.Member;
import salaba.vo.Nation;
import salaba.vo.rental_home.Theme;

@RequiredArgsConstructor
@Service
public class DefaultMemberService implements MemberService {

  private static final Log log = LogFactory.getLog(DefaultMemberService.class);
  private final MemberDao memberDao;

  @Override
  public void add(Member member) {
    memberDao.add(member);
  }

  @Override
  public Member get(String email, String password) {
    return memberDao.findByEmailAndPassword(email, password);
  }

  @Override
  public Member get(int no) {
    return memberDao.selectMemberInfo(no);
  }

  @Override
  public List<Nation> getNation() {
    return memberDao.getNation();
  }

  @Override
  public int myinfoUpdate(Member member) {
    return memberDao.myinfoUpdate(member);
  }

  @Override
  public Member checkNickname(String nickname) {
    return memberDao.checkNickname(nickname);
  }

  @Override
  public int delete(Member member) {
    ConstVO constVO = new ConstVO();

    member.setState(constVO.member_state_resign);
    return memberDao.delete(member);
  }

  @Override
  public Member findEmail(Member member) {
    return memberDao.findEmail(member);
  }

  @Override
  public Member findPassword(Member member) {
    return memberDao.findPassword(member);
  }

  @Override
  public void changePasswordSave(Member member) {
    memberDao.changePasswordSave(member);
  }

  @Override
  public Member myinfoCheckPassword(Member member) {
    return memberDao.myinfoCheckPassword(member);
  }

  @Override
  public void insertPreference(Member member) {
    memberDao.insertPreference(member);
  }

  @Override
  public void deletePreference(Member member) {
    memberDao.deletePreference(member);
  }

  @Override
  public List<Member> themeList(Member sessionInfo) {
    return memberDao.findAllTheme(sessionInfo);
  }

  @Override
  public  String getMemberPoint(Member member) {
    return memberDao.getMemberPoint(member);
  }

  @Override
  public  Member getGrade(Member member) {
    return memberDao.getGrade(member);
  }

  @Override
  public List<Member> mythemeList(Member sessionInfo) {
    return memberDao.findAllmyTheme(sessionInfo);
  }
}
