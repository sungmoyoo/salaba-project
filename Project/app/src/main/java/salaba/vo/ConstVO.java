package salaba.vo;

public class ConstVO {
  
  // 상태 ( 결제, 예약 내역, 문의 내역, 알림 내역, 게시판 신고 )
  public final char state_no = '0'; // 미완료

  public final char state_ok = '1'; // 완료

  // 게시판 신고 대상 타입
  public final char report_type_b = '0'; // 게시판

  public final char report_type_c = '1'; // 댓글

  public final char report_type_r = '2'; // 답글(대댓글)

  // 숙소 상태
  public final char rental_home_state_await = '0'; // 등록 대기

  public final char rental_home_state_run = '1';  // 운영중

  public final char rental_home_state_paused = '2';// 중지

  public final char rental_home_state_del = '3';// 삭제

  public final char rental_home_state_refused = '4';// 등록거부

  public final char rental_home_state_blocked = '5';// 제재


  // 회원 상태
  public final char member_state_common = '0'; // 일반

  public final char member_state_resign = '1'; // 탈퇴

  public final char member_state_blocked = '3'; // 제재

  public final char member_state_sleep = '4'; // 휴면


  // 댓글, 게시글, 답글, 숙소 리뷰 상태
  public final char text_ok = '0'; // 일반

  public final char text_del = '1'; // 삭제

  public final char text_blocked = '2'; // 제재

}
