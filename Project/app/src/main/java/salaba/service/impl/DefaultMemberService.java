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
  public Member findPw(Member member) {
    return memberDao.findPw(member);
  }

  @Override
  public void chgPwSave(Member member) {
    memberDao.chgPwSave(member);
  }

  @Override
  public Member chkPw(Member member) {
    return memberDao.chkPw(member);
  }
}
