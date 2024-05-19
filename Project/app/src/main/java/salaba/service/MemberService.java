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

  /////////////
  int checkNickname(String nickname);

  int checkEmail(String email);
  //////////////

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




  ///////////////////////////////////////////////////////////////////////////
  List<Board> search(String keyword, String type); // 검색 기능 업데이트

  int countFiltered(String type, String keyword); // 검색 결과 페이징 처리

  List<Board> searchByTitle(String title); // 제목으로 검색
  List<Board> searchByContent(String content); // 내용으로 검색

}
