package salaba.vo;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import lombok.Data;
import salaba.vo.rental_home.Theme;


@Data
public class Member implements Serializable { // 회원

  private static final long serialVersionUID = 100L;

  private int no; // 회원 번호
  private String email; // 이메일
  private String password; // 비밀번호
  private String name; // 이름
  private String nickname; // 닉네임
  private Date birthday; // 생년월일
  private String telNo; // 전화번호
  private String address; // 주소
  private char sex; // 성별
  private String photo; // 사진
  private char state; // 상태
  private Date exitDate; // 회원탈퇴 날짜
  private int nationNo; // 국적 - 국가번호

  private Grade grade; // 등급
  private List<Theme> themes; // 테마
  private List<PointHistory> pointHistory; // 포인트

}