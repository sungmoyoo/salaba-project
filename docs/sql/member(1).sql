-- 예약 내역   reservation  - 예약 시 기본 상태: 0 / 취소 : 1
insert into reservation(member_no,rental_home_no,start_date,end_date,chat_file_name,number_of_people)
values(1,1,'2024-03-21','2024-04-21','채팅1','6');
insert into reservation(member_no,rental_home_no,start_date,end_date,chat_file_name,number_of_people)
values(2,2,'2023-02-20','2023-03-20','채팅2','4');
insert into reservation(member_no,rental_home_no,start_date,end_date,chat_file_name,number_of_people)
values(3,3,'2022-01-19','2022-02-19','채팅3','3');
insert into reservation(member_no,rental_home_no,start_date,end_date,chat_file_name,number_of_people)
values(4,4,'2021-12-18','2021-12-18','채팅4','2');
insert into reservation(member_no,rental_home_no,start_date,end_date,chat_file_name,number_of_people)
values(5,5,'2020-11-17','2020-11-17','채팅5','1');

-- 예약하기
SELECT
    rh.name,
    r.number_of_people,
    r.start_date,
    r.end_date,
    rh.price,
 -- 영문 성 / 이름 입력받기
    m.email,
    m.country,
    m.phone_number
FROM
    reservation r
JOIN
    member m ON r.member_no = m.member_no
JOIN rental_home rh ON r.rental_home_no = rh.rental_home_no;

-- 예약내역 조회(숙소 조인)
select
  rh.name as rental_home_name,
  r.reservation_no,
  r.start_date,
  r.end_date,
  r.ppl_no,
  r.state,
from
  reservation r
JOIN
  rental_home rh ON r.rental_home_no = rh.rental_home_no;
WHERE
  member_no = 2
order by
  r.reservation_no;


-- 예약 변경
UPDATE
  reservation
SET
  start_date = '2024-04-01',
  end_date = '2024-05-01',
  number_of_people = 3
WHERE
  reservation_no = 1;


-- 사용자 예약 취소(상태 변경)
  UPDATE
    reservation
  SET
    state = '1'
  WHERE
    member_no = 2;

  -- 결제 취소
  UPDATE
    payment
  SET
    state = '1'
  WHERE
    reservation_no = 1;


-- 예약 취소(관리자 일괄 삭제) (보류)
DELETE FROM
  reservation
WHERE
    state = '0'
  AND
    start_date <= '2024-03-26';


-- 유저 선호사항 member_preference
insert into member_preference(member_no,theme_no) values(1,1);
insert into member_preference(member_no,theme_no) values(2,2);
insert into member_preference(member_no,theme_no) values(3,3);
insert into member_preference(member_no,theme_no) values(4,4);
insert into member_preference(member_no,theme_no) values(5,5);

-- 선호사항 조회
select
  member_no,
  theme_no
 from
  member_preference
 where
   member_no = 5;

-- 선호사항 업데이트
UPDATE member_preference
SET theme_no = 1
WHERE member_no = 3;


-- 즐겨찾기 bookmark
insert into bookmark(rental_home_no,member_no) values(1,1);
insert into bookmark(rental_home_no,member_no) values(2,2);
insert into bookmark(rental_home_no,member_no) values(3,3);
insert into bookmark(rental_home_no,member_no) values(4,4);
insert into bookmark(rental_home_no,member_no) values(5,5);

-- 즐겨찾기 조회
SELECT
    rh.name,
    rh.address,
    rh.price
    COUNT(b.rental_home_no) AS bookmark_count,
FROM
    rental_home rh
LEFT JOIN
    bookmark b ON rh.rental_home_no = b.rental_home_no
GROUP BY
    rh.rental_home_no;



-- 즐겨찾기 취소
Delete from
  bookmark
where
  member_no = 1
and
   rental_home_no = 3;


-- 문의 내역 question
insert into question(member_no,title,content,register_date)
values(1,'문의합니다','문의내용...','2024-08-24')
insert into question(member_no,title,content,register_date)
values(2,'문의합니다','문의내용...','2024-03-06');
insert into question(member_no,title,content,register_date)
values(3,'문의합니다',NULL,'2023-02-09');
insert into question(member_no,title,content,register_date)
values(4,'문의합니다',NULL,'2018-01-01');
insert into question(member_no,title,content,register_date)
values(5,'문의합니다',NULL,'2016-08-24');

-- 문의 파일  question_file
insert into question_file(question_no,ori_file_name,uuid_file_name)
values(1,'file1','b1deb4d-3b7d-4bad-9bdd');
insert into question_file(question_no,ori_file_name,uuid_file_name)
values(2,'file2','dgkj-72jh-verl-0fja');
insert into question_file(question_no,ori_file_name,uuid_file_name)
values(3,'file3','9lhj-knlv-jbtn-bp5t');
insert into question_file(question_no,ori_file_name,uuid_file_name)
values(4,'file4','thbx-krjh-tbke-ahb5');
insert into question_file(question_no,ori_file_name,uuid_file_name)
values(5,'file5','argx-rhss-eq3e-a346');


-- 관리자 입장 전체 문의 조회(1:1 문의내역)
SELECT
    q.question_no,
    q.member_no,
    q.title,
    q.content,
    q.register_date,
    qf.ori_file_name,
    qf.uuid_file_name
FROM
    question q
LEFT JOIN -- 첨부파일 없이 내용만 작성한 경우 NULL 값 들어감.
    question_file f
 ON q.question_no = qf.question_no;


-- 문의 답변 qna
insert into qna(question_no,content,created_date)
values('1','답변드립니다.','2024-05-14');
insert into qna(question_no,content,created_date)
values('2','답변드립니다.','2023-04-10');
insert into qna(question_no,content,created_date)
values('3','답변드립니다.','2022-03-06');
insert into qna(question_no,content,created_date)
values('4','답변드립니다.','2021-02-02');
insert into qna(question_no,content,created_date)
values('5','답변드립니다.','2020-12-28');

-- 답변 조회
SELECT
  q.question_no,
  q.content,
  q.register_date,,
  a.content,
  a.created_date
FROM
  question q
LEFT JOIN  -- 작성한 문의에 대한 답변이 없는 경우에는 해당 문의의 답변 내용은 NULL
  qna a ON q.question_no = a.question_no;
WHERE
  q.member_no = 1;


-- 알림 내역  notify_history - 0: 안읽음 / 1: 읽음
insert into notify_history(member_no,content,notify_date,state)
values(1,'작성하신 게시글에 새 댓글이 달렸습니다.','2024-10-20','0');
insert into notify_history(member_no,content,notify_date,state)
values(2,'작성하신 댓글에 새 답글이 달렸습니다.','2024-02-25','0');
insert into notify_history(member_no,content,notify_date,state)
values(3,'호스트로부터 채팅이 왔습니다.','2022-03-08','1');
insert into notify_history(member_no,content,notify_date,state)
values(4,'리뷰 작성으로 500 포인트가 발급되었습니다.','2024-10-20','1');
insert into notify_history(member_no,content,notify_date,state)
values(5,'리뷰 작성으로 500 포인트가 발급되었습니다.','2023-11-05','1');

-- 회원 별 알림 조회
select
  member_no,
  content,
  notify_date,
  state
from
  notify_history
where
  member_no = 1
 and
  notify_no = 1
order by
  notify_date;


-- 알림 상태 업데이트(확인 시 0 -> 1로 변경됨)
UPDATE notify_history
SET state = '1'
WHERE member_no = '1'
AND notify_no = '1';


-- 결제 payment - 상태 결제 시 기본: 0 / 취소 시: 1
insert into payment(reservation_no,payment_no,payment_date,amount,card_no,validity_date)
values('1','100','2024-01-01','150000','1234-5678-0000-0000','2024-08-01');
insert into payment(reservation_no,payment_no,payment_date,amount,card_no,validity_date)
values('2','101','2024-02-02','200000','1234-5678-0000-0001','2026-07-01');
insert into payment(reservation_no,payment_no,payment_date,amount,card_no,validity_date)
values('3','102','2024-03-03','370000','1234-5678-0000-0002','2028-06-01');
insert into payment(reservation_no,payment_no,payment_date,amount,card_no,validity_date)
values('4','103','2024-04-04','460000','1234-5678-0000-0003','2030-05-01');
insert into payment(reservation_no,payment_no,payment_date,amount,card_no,validity_date)
values('5','104','2024-05-05','290000','1234-5678-0000-0004','2032-04-01');


-- 결제 내역 조회
 SELECT
     r.start_date,
     r.end_date,
     rh.price,
     rh.clean_fee,
     p.tax,
     p.amount,
     ph.save_point,
    -- c.coupon,
 FROM
     payment p
 JOIN
     reservation r ON p.reservation_no = r.reservation_no
 JOIN
     rental_home rh ON r.rental_home_no = rh.rental_home_no
 LEFT JOIN
     point_history ph ON p.member_no = ph.member_no
 WHERE
     p.reservation_no = '3';


