--회원가입
insert into
member(
  member_no,
  name,
  nickname,
  birthday,
  email,
  password
)
values
  (
    1,
    '이름',
    '닉네임',
    '2000-01-01',
    'user1@test.com',
    sha2('1111',256)
  );
insert into member(member_no,name,nickname,birthday,email,password)
values(2,'이름2','닉네임2','2000-01-01','user2@test.com',sha2('1111',256));
insert into member(member_no,name,nickname,birthday,email,password)
values(3,'이름3','닉네임3','2000-01-01','user3@test.com',sha2('1111',256));

--개인정보 조회
select
  n.nation_name,
  m.name,
  m.nickname,
  m.email,
  m.tel_no,
  m.birthday,
  m.sex,
  m.address,
  m.photo
from
  member m
  inner join nation n on m.nation_no=n.nation_no
where m.member_no='3';

--개인정보 수정
update
  member
set
  name='이름33',
  nickname='닉네임33',
  email='user33@test.com',
  tel_no='010-1111-2222',
  birthday='2020-01-01',
  nation_no=82,
  sex='남',
  address='주소',
  photo='a.gif'
where
  member_no='3';

--개인정보 삭제
update
  member
set
  state='',
  exit_date=''
where
  member_no='';

--로그인
select
  password
from
  member
where
  email='';

--이메일 찾기
select
  email
from
  member
where
  name='이름33' and
  birthday='2020-01-01';

--비밀번호 찾기
select
  password
from
  member
where
  name='이름33' and
  email='user33@test.com';

--비밀번호 변경
update
  member
set
  password=sha2('1111',256)
where
  member_no;

----------------------------------------------------------------------
--샘플 포인트내역 데이터
insert into point_history(member_no,save_content,save_point,save_date)
values(1,'aa','500','2020-01-01');
insert into point_history(member_no,save_content,save_point,save_date)
values(1,'aa','500','2020-01-01');
insert into point_history(member_no,save_content,save_point,save_date)
values(2,'aa','500','2020-01-01');
insert into point_history(member_no,save_content,save_point,save_date)
values(3,'aa','500','2020-01-01');
----------------------------------------------------------------------
--포인트 내역 조회
select
  save_content,
  save_point,
  save_date
from
  point_history
where
  member_no='1'
order by
  p.save_date;

-- 예약 내역
insert into 
  reservation
  (
    member_no,
    rental_home_no,
    start_date,
    end_date,
    payment_date,
    chat_file_name
  )
values
  (
    1,
    1,
    '2024-03-21',
    '2024-04-21',
    '2024-02-10',
    '채팅1'
  );

insert into 
payment
(
  payment_no,
  payment_date,
  amount,
  card_no,
  validity_date,
)
values
  (
    '1',
    '2024-01-01',
    '150000',
    '1234-5678-0000-0000',
    '2024-08'
  );

insert into reservation(reservation_no,member_no,rental_home_no,start_date,end_date,payment_date,state,chat_file_name)
values(1235,2,2,'2023-02-20','2023-03-20','2023-01-9','완료됨','채팅2');
insert into reservation(reservation_no,member_no,rental_home_no,start_date,end_date,payment_date,state,chat_file_name)
values(1236,3,3,'2022-01-19','2022-02-19','2022-12-8','취소됨','채팅3');
insert into reservation(reservation_no,member_no,rental_home_no,start_date,end_date,payment_date,state,chat_file_name)
values(1237,4,4,'2021-12-18','2021-12-18','2021-11-7','완료됨','채팅4');
insert into reservation(reservation_no,member_no,rental_home_no,start_date,end_date,payment_date,state,chat_file_name)
values(1238,5,5,'2020-11-17','2020-11-17','2020-10-6','취소됨','채팅5');

-- 예약 내역 조회
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
  reservation;



-- 선호사항
insert into member_preference(member_no,theme_no) values(1,1);
insert into member_preference(member_no,theme_no) values(2,2);
insert into member_preference(member_no,theme_no) values(3,3);
insert into member_preference(member_no,theme_no) values(4,4);
insert into member_preference(member_no,theme_no) values(5,5);

-- 선호 사항 조회
select
  member_no,
  theme_no
 from
  preference;


-- 즐겨찾기
insert into bookmark(rental_home_no,member_no) values(1,1);
insert into bookmark(rental_home_no,member_no) values(2,2);
insert into bookmark(rental_home_no,member_no) values(3,3);
insert into bookmark(rental_home_no,member_no) values(4,4);
insert into bookmark(rental_home_no,member_no) values(5,5);

-- 즐겨 찾기 조회
select
 rental_home_no,
 member_no
from
 bookmark;


-- 문의 내역
insert into question(question_no,member_no,title,content,state,register_date)
values(1,1,'문의합니다','문의내용...','진행중','2024-08-24')
insert into question(question_no,member_no,title,content,state,register_date)
values(2,2,'문의합니다','문의내용...','진행중','2024-03-06');
insert into question(question_no,member_no,title,content,state,register_date)
values(3,3,'문의합니다','문의내용...','답변 완료','2023-02-09');
insert into question(question_no,member_no,title,content,state,register_date)
values(4,4,'문의합니다','문의내용...','답변 완료','2018-01-01');
insert into question(question_no,member_no,title,content,state,register_date)
values(5,5,'문의합니다','문의내용...','답변 완료','2016-08-24');


-- 문의 내역 조회
select
  question_no,
  member_no,
  title,
  content,
  state,
  register_date
from
 question;


-- 문의 파일
insert into question_file(question_file_no,question_no,ori_file_name,uuid_file_name)
values(1,1,'file1','b1deb4d-3b7d-4bad-9bdd');
insert into question_file(question_file_no,question_no,ori_file_name,uuid_file_name)
values(2,2,'file2','dgkj-72jh-verl-0fja');
insert into question_file(question_file_no,question_no,ori_file_name,uuid_file_name)
values(3,3,'file3','9lhj-knlv-jbtn-bp5t');
insert into question_file(question_file_no,question_no,ori_file_name,uuid_file_name)
values(4,4,'file4','thbx-krjh-tbke-ahb5');
insert into question_file(question_file_no,question_no,ori_file_name,uuid_file_name)
values(5,5,'file5','argx-rhss-eq3e-a346');


-- 문의 내역 파일
select
 question_file_no,
 question_no,
 ori_file_name,
 uuid_file_name
from
  question_file;

-- 문의 답변
insert into qna(question_no,content,created_date)
values(1,'답변드립니다.','2024-05-14');
insert into qna(question_no,content,created_date)
values(2,'답변드립니다.','2023-04-10');
insert into qna(question_no,content,created_date)
values(3,'답변드립니다.','2022-03-06');
insert into qna(question_no,content,created_date)
values(4,'답변드립니다.','2021-02-02');
insert into qna(question_no,content,created_date)
values(5,'답변드립니다.','2020-12-28');

-- 답변 조회
select
 question_no,
 content,
 created_date
from
 qna;


-- 알림 내역
insert into notify_history(notify_no,member_no,content,notify_date,state)
values(1,1,'작성하신 게시글에 새 댓글이 달렸습니다.','2024-10-20','N');
insert into notify_history(notify_no,member_no,content,notify_date,state)
values(2,2,'작성하신 댓글에 새 답글이 달렸습니다.','2024-02-25','N');
insert into notify_history(notify_no,member_no,content,notify_date,state)
values(3,3,'호스트로부터 채팅이 왔습니다.','2022-03-08','Y');
insert into notify_history(notify_no,member_no,content,notify_date,state)
values(4,4,'리뷰 작성으로 500 포인트가 발급되었습니다.','2024-10-20','Y');
insert into notify_history(notify_no,member_no,content,notify_date,state)
values(5,5,'리뷰 작성으로 500 포인트가 발급되었습니다.','2023-11-05','Y');

-- 알림 update문


-- 알림 내역 조회
select
  notify_no,
  member_no,
  content,
  notify_date,
  state
from
  notify_history;

-- 결제

insert into payment(reservation_no,payment_no,payment_date,amount,card_no,validity_date,state)
values('1232','2','2024-02-02','200000','1234-5678-0000-0001','2026-07','결제 완료');
insert into payment(reservation_no,payment_no,payment_date,amount,card_no,validity_date,state)
values('1233','3','2024-03-03','370000','1234-5678-0000-0002','2028-06','결제 취소');
insert into payment(reservation_no,payment_no,payment_date,amount,card_no,validity_date,state)
values('1234','4','2024-04-04','460000','1234-5678-0000-0003','2030-05','결제 완료');
insert into payment(reservation_no,payment_no,payment_date,amount,card_no,validity_date,state)
values('1235','5','2024-05-05','290000','1234-5678-0000-0004','2032-04','결제 완료');


-- 결제 내역 조회
select
 reservation_no,
 payment_no,
 payment_date,
 amount,
 card_no,
 validity_date
from
 payment;

 -- 결제 취소