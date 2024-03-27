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
  m.member_no,
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
  left join nation n on m.nation_no=n.nation_no
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
delete from member where member_no='3';

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
--테스트 포인트내역 데이터
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

--호스팅 중인 숙소 정보
--숙소 관리 리스트
select
  name,
  capacity,
  lat,
  lon,
  price,
  state,
  hosting_start_date,
  hosting_end_date
from
  rental_home
order by
  state;

--숙소 관리 상세 조회
select
  rp.ori_photo_name,
  rp.photo_explanation,
  rh.state,
  rh.name,
  rh.price,
  rh.clean_fee,
  rh.explanation,
  rh.capacity,
  rf.facility_count,
  rf.facility_name
from
  rental_home rh
  left join rental_home_photo rp on rp.photo_no=rh.photo_no
  left join rental_home_facility rf on rf.facility_no=rh.facility_no
where
  rental_home_no='1';

--숙소 관리 상세 변경
update
  rental_home
set
  photo_no='',
  photo_explanation='',
  state='',
  name='',
  price='',
  clean_fee='',
  explanation='',
  capacity='',
  facility_count='',
  facility_no=''
where
  rental_home_no='';

--수입 관리
select
  rp.ori_photo_name,
  rh.name,
  rh.address,
  sum(rh.rental_home_no) price_sum,
  count(rh.rental_home_no) member_count,
  rh.registe_date
from
  rental_home rh
  left join rental_home_photo rp on rp.photo_no=rh.photo_no
order by
  rh.registe_date;

--수입 상세 보기