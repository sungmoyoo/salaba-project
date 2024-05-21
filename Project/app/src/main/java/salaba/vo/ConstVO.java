package salaba.vo;

public final class ConstVO {

  // 상태 ( 결제, 문의 내역, 알림 내역, 게시판 신고 )
  public static final char state_no = '0'; // 미완료

  public static final char state_ok = '1'; // 완료

  public static final char state_cancel = '2'; //취소

  // 예약 내역
  public static final char reservation_state_await = '0'; // 승인 대기

  public static final char reservation_state_ok = '1';  // 예약 완료

  public static final char reservation_state_no = '2';// 예약 거절

  public static final String reservation_state_end = "3";// 이용 완료

  // 게시판 신고 대상 타입
  public static final char report_type_b = '0'; // 게시판

  public static final char report_type_c = '1'; // 댓글

  public static final char report_type_r = '2'; // 답글(대댓글)

  // 숙소 상태
  public static final char rental_home_state_await = '0'; // 등록 대기

  public static final char rental_home_state_run = '1';  // 운영중

  public static final char rental_home_state_paused = '2';// 중지

  public static final char rental_home_state_del = '3';// 삭제

  public static final char rental_home_state_refused = '4';// 등록거부

  public static final char rental_home_state_blocked = '5';// 제재


  // 회원 상태
  public static final char member_state_common = '0'; // 일반

  public static final char member_state_resign = '1'; // 탈퇴

  public static final char member_state_blocked = '3'; // 제재

  public static final char member_state_sleep = '4'; // 휴면gi
  
  public static final char login_ok = '0'; // 로그인 성공
  public static final char login_fail = '9'; // 로그인 실패

  // 댓글, 게시글, 답글, 숙소 리뷰 상태
  public static final char text_ok = '0'; // 일반

  public static final char text_del = '1'; // 삭제

  public static final char text_blocked = '2'; // 제재

  public static final String notify_mark = "게시글에 댓글이 달렸습니다.";

}
