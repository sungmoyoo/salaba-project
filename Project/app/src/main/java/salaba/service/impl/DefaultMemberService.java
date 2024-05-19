package salaba.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import salaba.dao.MemberDao;
import salaba.service.MemberService;
import salaba.vo.Member;
import salaba.vo.Nation;
import salaba.vo.board.Board;

@RequiredArgsConstructor
@Service
public class DefaultMemberService implements MemberService {

  private static final Log log = LogFactory.getLog(DefaultMemberService.class);
  private final MemberDao memberDao;

  @Override
  public int addMember(Member member) {
    return memberDao.addMember(member);
  }

  @Override
  public Member selectUserInfoForLogin(String email, String password) {
    return memberDao.selectUserInfoForLogin(email, password);
  }

  @Override
  public Member selectUserInfoForUpdateSession(int memberNo) {
    return memberDao.selectUserInfoForUpdateSession(memberNo);
  }

  @Override
  public Member selectUserInfoForLogin(int no) {
    return memberDao.selectMemberInfo(no);
  }

  @Override
  public List<Nation> getNation() {
    return memberDao.getNation();
  }

  @Override
  public int updateUserInfo(Member member) {
    return memberDao.updateUserInfo(member);
  }

  @Override
  public int checkNickname(String nickname) {
    return memberDao.checkNickname(nickname);
  }
////////////////
  @Override
  public int checkEmail(String email) {
    return memberDao.checkEmail(email);
  }
////////////
  @Override
  public int updateMemberWithdrawal(int memberNo) {
    return memberDao.updateMemberWithdrawal(memberNo);
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
  public int checkPassword(int memberNo, String password) {
    return memberDao.checkPassword(memberNo, password);
  }

  @Override
  public void insertPreference(Member member) {
    memberDao.insertPreference(member);
  }

  @Override
  public void deletePreference(int memberNo) {
    memberDao.deletePreference(memberNo);
  }

//////////////////////
  @Override
  public String boardStateCheck(Board board){ // 알람 업데이트(알람을 읽었을 경우 업데이트)
    return memberDao.boardStateCheck(board);
  }

  @Override
  public List<Board> search(String keyword, String type) { // 검색 기능
    return memberDao.searchByKeyword(keyword, type);
  }

  @Override
  public List<Board> searchByTitle(String title) {  // 제목으로 검색
    return memberDao.searchByKeyword(title, "title");
  }

  @Override
  public List<Board> searchByContent(String content) { // 내용으로 검색
    return memberDao.searchByKeyword(content, "content");
  }
  @Override
  public int countFiltered(String type, String keyword) { // 검색 결과 페이징 처리
    return memberDao.countFiltered(type, keyword);
  }
}

