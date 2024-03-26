--회원
insert into member(member_no,national_no,email,password,name,nickname,birthday,tel_no,state,address,
sex,join_date,last_login_date,exit_date,warning_count,photo)
values(1,82,'user1.test.com',sha2('1111',256),'이름1','회원1','2000-01-01','010-1111-1111','0','주소1',
'1','2024-03-20','2024-03-21',null,'a.gif')
insert into member(member_no,national_no,email,password,name,nickname,birthday,tel_no,state,address,
sex,join_date,last_login_date,exit_date,warning_count,photo)
values(2,82,'user2.test.com',sha2('1111',256),'이름2','회원2','2000-02-02','010-2222-2222','0','주소2',
'1','2024-03-21','2024-03-22',null,'b.gif')
insert into member(member_no,national_no,email,password,name,nickname,birthday,tel_no,state,address,
sex,join_date,last_login_date,exit_date,warning_count,photo)
values(3,82,'user3.test.com',sha2('1111',256),'이름3','회원3','2000-03-03','010-3333-3333','0','주소3',
'2','2024-03-22','2024-03-23','2','c.gif')
insert into member(member_no,national_no,email,password,name,nickname,birthday,tel_no,state,address,
sex,join_date,last_login_date,exit_date,warning_count,photo)
values(4,81,'user4.test.com',sha2('1111',256),'이름4','회원4','2000-04-04','010-4444-4444','1','주소4',
'2','2024-03-20','2024-03-21','1','d.gif')
insert into member(member_no,national_no,email,password,name,nickname,birthday,tel_no,state,address,
sex,join_date,last_login_date,exit_date,warning_count,photo)
values(4,86,'user5.test.com',sha2('1111',256),'이름5','회원5','2000-05-05','010-5555-5555','0','주소5',
'2','2024-03-20','2024-03-21',null,'.gif')

select
  member_no,
  national_no,
  email,
  password,
  name,
  nickname,
  birthday,
  tel_no,
  state,
  address,
  sex,
  join_date,
  last_login_date,
  exit_date,
  warning_count,
  photo
 from
  member;

--포인트 내역
insert into point_history(member_no,save_content,save_point,save_date)
values(1,'숙소 리뷰 작성','200','2024-03-21')
insert into point_history(member_no,save_content,save_point,save_date)
values(1,'댓글작성','50','2024-03-21')
insert into point_history(member_no,save_content,save_point,save_date)
values(2,'숙소 리뷰 작성','200','2024-03-21')
insert into point_history(member_no,save_content,save_point,save_date)
values(2,'숙소 리뷰 작성','200','2024-03-22')
insert into point_history(member_no,save_content,save_point,save_date)
values(2,'댓글 작성','50','2024-03-22')
insert into point_history(member_no,save_content,save_point,save_date)
values(3,'숙소 리뷰 작성','200','2024-03-23')

select
  member_no,
  save_content,
  save_point,
  save_date
from
  point_history;