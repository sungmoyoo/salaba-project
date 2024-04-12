package salaba.service;

import java.util.List;

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
}
