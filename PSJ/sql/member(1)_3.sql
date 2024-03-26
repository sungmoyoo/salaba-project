-- 예약 내역   reservation
insert into reservation(member_no,rental_home_no,start_date,end_date,payment_date,chat_file_name,ppl_no)
values(1,1,'2024-03-21','2024-04-21','2024-02-10','채팅1','6');
insert into reservation(member_no,rental_home_no,start_date,end_date,payment_date,chat_file_name,ppl_no)
values(2,2,'2023-02-20','2023-03-20','2023-01-9','채팅2','4');
insert into reservation(member_no,rental_home_no,start_date,end_date,payment_date,chat_file_name,ppl_no)
values(3,3,'2022-01-19','2022-02-19','2022-12-8',Null,'3');
insert into reservation(member_no,rental_home_no,start_date,end_date,payment_date,chat_file_name,ppl_no)
values(4,4,'2021-12-18','2021-12-18','2021-11-7','채팅4','2');
insert into reservation(member_no,rental_home_no,start_date,end_date,payment_date,chat_file_name,ppl_no)
values(5,5,'2020-11-17','2020-11-17','2020-10-6',Null,'1');

-- 예약내역 조회
select
  reservation_no,
  member_no,
  rental_home_no,
  start_date,
  end_date,
  payment_day,
  state,
  chat_file_name
from
  reservation
WHERE
  member_no = 2
order by
  reservation_no asc;


-- 예약 변경
UPDATE reservation
SET
    start_date = '2024-04-01',
    end_date = '2024-05-01',
    payment_date = '2024-02-11',
    ppl_no = 3
WHERE
    member_no = 1
    AND rental_home_no = 1
    AND start_date = '2024-03-21'
    AND end_date = '2024-04-21'
    AND payment_date = '2024-02-10'
    AND chat_file_name = '채팅1'
    AND ppl_no = 6;


-- 사용자 예약 취소(상태 변경)
   -- 예약 취소 + 결제 취소 한꺼번에 rollback / commit : Transaction
  START TRANSACTION;

  -- 예약 취소
  UPDATE
    reservation
  SET
    status = '0'
  WHERE
    status = '1';

  -- 결제 취소
  UPDATE
    payment
  SET
    status = '0'
  WHERE
    reservation_no IN (SELECT reservation_no FROM reservation WHERE status = '0');

  COMMIT;



-- 예약 취소(관리자 일괄 삭제) (보류)
DELETE FROM
  reservation
WHERE
    status = '0'
  AND
    start_date <= '2024-03-26';


-- 선호사항  preference
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
  preference;
 where
   member_no = 5
 order by
  theme_no asc;

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
select
 rental_home_no,
 member_no
from
 bookmark
where
  member_no = 1
order by
  rental_home_no;

-- 즐겨찾기 업데이트
UPDATE bookmark
SET rental_home_no = 1
WHERE member_no = 4;


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

-- 문의 내역 조회
SELECT
  q.*,
  qf.ori_file_name,
  qf.uuid_file_name
FROM
  question q
LEFT JOIN
  question_file qf
ON
  q.question_no = qf.question_no;
WHERE
  q.question_no = 2
ORDER BY
  register_date desc;


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
  q.*,
  qna
FROM
  qna
LEFT JOIN
  question_no q
ON
  q.question_no = qna_no;
WHERE
  qna_no = 2
ORDER BY
  created_date desc;



-- 알림 내역  notify_history
insert into notify_history(member_no,content,notify_date,state)
values(1,'작성하신 게시글에 새 댓글이 달렸습니다.','2024-10-20','N');
insert into notify_history(member_no,content,notify_date,state)
values(2,'작성하신 댓글에 새 답글이 달렸습니다.','2024-02-25','N');
insert into notify_history(member_no,content,notify_date,state)
values(3,'호스트로부터 채팅이 왔습니다.','2022-03-08','Y');
insert into notify_history(member_no,content,notify_date,state)
values(4,'리뷰 작성으로 500 포인트가 발급되었습니다.','2024-10-20','N');
insert into notify_history(member_no,content,notify_date,state)
values(5,'리뷰 작성으로 500 포인트가 발급되었습니다.','2023-11-05','Y');

-- 알림 조회
select
  member_no,
  content,
  notify_date,
  state
from
  notify_history
order by
  notify_date desc;


-- 알림 상태 업데이트
UPDATE notify_history
SET content = '변경내용',
    notify_date = '2024-10-20',
    state = 'Y'
WHERE notify_no = 4;


-- 결제 payment
insert into payment(reservation_no,payment_date,amount,card_no,validity_date)
values('1','2024-01-01','150000','1234-5678-0000-0000','2024-08-01');
insert into payment(reservation_no,payment_date,amount,card_no,validity_date)
values('2','2024-02-02','200000','1234-5678-0000-0001','2026-07-01');
insert into payment(reservation_no,payment_date,amount,card_no,validity_date)
values('3','2024-03-03','370000','1234-5678-0000-0002','2028-06-01');
insert into payment(reservation_no,payment_date,amount,card_no,validity_date)
values('4','2024-04-04','460000','1234-5678-0000-0003','2030-05-01');
insert into payment(reservation_no,payment_date,amount,card_no,validity_date)
values('5','2024-05-05','290000','1234-5678-0000-0004','2032-04-01');

-- 결제내역 조회
select
 reservation_no,
 payment_date,
 amount,
 card_no,
 validity_date
from
 payment
where
 reservation_no = 5
order by
 reservation_no asc payment_date desc;



 -- 결제 취소 테이블 생성
 CREATE TABLE cancellation (
     cancellation_no INT AUTO_INCREMENT PRIMARY KEY,
     payment_no INT,
     cancellation_date DATE,
     FOREIGN KEY (payment_no) REFERENCES payment(payment_no)
 );

 -- 결제 취소
insert into cancellation(payment_no, cancellation_date) values('1','2024-01-01');
insert into cancellation(payment_no, cancellation_date) values('2','2024-02-01');
insert into cancellation(payment_no, cancellation_date) values('3','2024-03-01');
insert into cancellation(payment_no, cancellation_date) values('4','2024-04-01');
insert into cancellation(payment_no, cancellation_date) values('5','2024-05-01');


 -- 결제 취소내역
select
 payment_no,
 cancellation_date
from
 cancellation
where
 payment_no = 1
order by
 cancellation_date desc;

 -- 결제 및 취소내역 전체 조회
SELECT
  p.*, c.cancellation_date
 FROM
   payment p
 LEFT JOIN
  cancellation c
 ON
  p.payment_no = c.payment_no;


