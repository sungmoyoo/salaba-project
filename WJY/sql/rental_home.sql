insert into member(member_no, email, password, name, nickname, birthday)
values(1, 'wnsdus1008@gmail.com', sha2('1234', 256), 'junyeon', 'jun', '1996-10-08');
-- 세팅
-- 국가
insert into national(national_no, national_name) values (82, 'KR');
insert into national(national_no, national_name) values (81, 'JP');
insert into national(national_no, national_name) values (1, 'USA');
insert into national(national_no, national_name) values (86, 'CH');
insert into national(national_no, national_name) values (44, 'UK');

-- 지역
insert into region(region_no, region_name, national_no) values(1, 'seoul',82);
insert into region(region_no, region_name, national_no) values(2, 'jeju', 82);

-- 숙소 편의 시설
insert into rental_home_facility(facility_no, facility_name) values(1, 'airConditioner');
insert into rental_home_facility(facility_no, facility_name) values(2, 'bed');
insert into rental_home_facility(facility_no, facility_name) values(3, 'wifi');
insert into rental_home_facility(facility_no, facility_name) values(4, 'kitchen');
insert into rental_home_facility(facility_no, facility_name) values(5, 'tv');
insert into rental_home_facility(facility_no, facility_name) values(6, 'freeParking');
insert into rental_home_facility(facility_no, facility_name) values(7, 'swimmingPool');
insert into rental_home_facility(facility_no, facility_name) values(8, 'Bathtub');
insert into rental_home_facility(facility_no, facility_name) values(9, 'washingMachine');
insert into rental_home_facility(facility_no, facility_name) values(10, 'Barbecue');

-- 숙소테마
insert into theme(theme_no, theme_name) values(1, 'beach');
insert into theme(theme_no, theme_name) values(2, 'mountain');
insert into theme(theme_no, theme_name) values(3, 'downtown');
insert into theme(theme_no, theme_name) values(4, 'countryside');
insert into theme(theme_no, theme_name) values(5, 'tropical');
insert into theme(theme_no, theme_name) values(6, 'arctic');
insert into theme(theme_no, theme_name) values(7, 'viewOfTheSea');
insert into theme(theme_no, theme_name) values(100, 'house');
insert into theme(theme_no, theme_name) values(101, 'apartment');
insert into theme(theme_no, theme_name) values(102, 'hotel');

-- 숙소 신고 유형 - 물리이름 안지어짐(데이터 타입 늘려야함)
insert into rental_home_report_category(no, name)
values(1, '부정확하거나 틀린 정보가 있어요');
insert into rental_home_report_category
values(2, '실제 숙소가 아닙니다');
insert into rental_home_report_category
values(3, '사기입니다');
insert into rental_home_report_category
values(4, '불쾌합니다');
insert into rental_home_report_category
values(5, '기타');

-----------------------------------------------------------------------------------------------

-- 숙소 등록
insert into rental_home(
member_no,
region_no,
name,
explanation,
address,
price,
capacity,
lat,
lon,
state,
hosting_start_date, -- 호스팅 시작, 종료일 null로 설정 or, 등록화면에 추가
hosting_end_date
)
values
(
1,
2,
'라마다 제주 시티 호텔',
'제주 시내에 자리하고있어 위치적으로 편리하며, 가성비가 좋은 호텔',
'대한민국 제주도 제주 중앙로 304',
400000,
2,
'33.49349680955714',
'126.53430217348503',
'1',
'2024-03-24',
'2025-03-24'
);

select * from member;
select * from region;
-- 숙소_편의시설 관계 테이블
insert into rental_home_detail(rental_home_no, facility_no, facility_count)
values(1, 1, 2);
insert into rental_home_detail(rental_home_no, facility_no, facility_count)
values(1, 2, 4);
insert into rental_home_detail(rental_home_no, facility_no, facility_count)
values(1, 3, 2);
insert into rental_home_detail(rental_home_no, facility_no, facility_count)
values(1, 4, 2);
insert into rental_home_detail(rental_home_no, facility_no, facility_count)
values(1, 5, 2);
insert into rental_home_detail(rental_home_no, facility_no, facility_count)
values(1, 6, 3);
insert into rental_home_detail(rental_home_no, facility_no, facility_count)
values(1, 7, 0);
insert into rental_home_detail(rental_home_no, facility_no, facility_count)
values(1, 8, 2);
insert into rental_home_detail(rental_home_no, facility_no, facility_count)
values(1, 9, 2);
insert into rental_home_detail(rental_home_no, facility_no, facility_count)
values(1, 10, 1);


-- 숙소_테마 관계테이블(추가, 삭제)
insert into rental_home_theme(rental_home_no, theme_no)
values(1, 1);
insert into rental_home_theme(rental_home_no, theme_no)
values(1, 5);
insert into rental_home_theme(rental_home_no, theme_no)
values(1, 7);
insert into rental_home_theme(rental_home_no, theme_no)
values(1, 100);
delete from rental_home_theme
where rental_home_no = ? and theme_no = ?;

-- 숙소 사진 등록
insert into rental_home_photo(
ori_photo_name,
uuid_photo_name,
photo_explanation,
rental_home_no
)
VALUES(
    'bathroom.jpg',
    '4fab1fea-88eb-41d1-99f8-d16308e13d4f',
    '화장실- 욕조',
    1
    );

-----------------------------------------------------------------------------------------------
-- 예약관리

-- 승인 대기중인 예약
select
    rent.name,
    m.name,
    res.reservation_no,
    res.member_no,
    res.rental_home_no,
    res.start_date,
    res.end_date,
    res.payment_date,
    res.state
from
    reservation res
inner join
    member m on res.member_no = m.member_no
inner join
    rental_home rent on res.rental_home_no = rent.rental_home_no
where
    res.state = '0'
order by
    res.start_date asc;


-- 승인 완료된 예약
select
    rent.name,
    m.name,
    res.reservation_no,
    res.member_no,
    res.rental_home_no,
    res.start_date,
    res.end_date,
    res.payment_date,
    res.state
from
    reservation res
inner join
    member m on res.member_no = m.member_no
inner join
    rental_home rent on res.rental_home_no = rent.rental_home_no
where
    res.state = '1'
order by
    res.start_date asc;

-- 예약 승인
update reservation
set state = '1'
where reservation_no = ?;

-- 에약 거절
update reservation
set state = '2'
where reservation_no = ?;

-----------------------------------------------------------------------------------------------

-- 숙소관리
select
rental_home_no
name,
explanation,
address,
price,
capacity,
state,
hosting_start_date,
hosting_end_date
where
member_no = ?;

-----------------------------------------------------------------------------------------------

-- 숙소 상세 진입
-- 숙소 정보와 사진
select
rp.photo_no,
rp.ori_photo_name,
rp.uuid_photo_name,
rp.photo_explanation,
r.region_no,
r.name,
r.explanation,
r.address,
r.price,
r.capacity,
r.lat,
r.lon,
r.state,
r.hosting_start_date,
r.hosting_end_date,
r.registe_date
from rental_home r
inner join rental_home_photo rp on rp.rental_home_no = r.rental_home_no
order by rp.photo_no desc;

-- 숙소 편의시설
select
rhf.facility_name,
rhd.facility_count
from rental_home_facility rhf
inner join rental_home_detail rhd on rhf.facility_no = rhd.facility_no
where rhd.rental_home_no = ?;

-----------------------------------------------------------------------------------------------

-- 숙소 관리 - 상세

-- 사진 변경 -
-- 사진 삭제
delete from rental_home_photo
where photo_no = ?;
-- 사진 추가
insert into rental_home_photo(
ori_photo_name,
uuid_photo_name,
photo_explanation,
rental_home_no
)
VALUES(
    'bed.jpg',
    '0fb1a4ab-eebc-4372-96bb-c2b9d31fcb13',
    '침대',
    1
);

update rental_home
set
name = '라마다 제주 시티 호텔2',
address = '대한민국 제주도 제주 중앙로 304',
region_no = 2,
explanation = '제주 시내에 자리하고있어 위치적으로 편리하며, 가성비가 좋은 호텔',
price = 500000,
capacity = 4,
lat = '33.49349680955714',
lon = '126.53430217348503',
state = '1',
hosting_start_date = '2024-03-25',
hosting_end_date = '2025-03-25'
where
rental_home_no = ?;


-- 숙소 편의시설 정보 변경
update rental_home_detail
set facility_count = 4
where rental_home_no = ? and facility_no = ?;

-- 숙소 테마 변경(추가, 삭제)
delete from rental_home_theme
where rental_home_no = ? and theme_no = ?;

insert into rental_home_theme(rental_home_no, theme_no)
values(1, 7);
insert into rental_home_theme(rental_home_no, theme_no)
values(1, 100);

------------------------------------------------------------------------------------------------------------

-- 한달살기- 메인(추천수 순서)
select
rhp.uuid_photo_name,
rh.rental_home_no,
rh.name,
rh.price,
count(rhl.rental_home_no) as like_count
from rental_home rh
inner join rental_home_photo rhp on rhp.rental_home_no = rh.rental_home_no
inner join rental_home_like rhl on rhl.rental_home_no = rh.rental_home_no
order by like_count asc;

-- 한달살기 - 테마 검색

select
rhp.uuid_photo_name,
rh.rental_home_no,
rh.name,
rh.price,
count(rhl.rental_home_no) as like_count
from rental_home rh
inner join rental_home_photo rhp on rhp.rental_home_no = rh.rental_home_no
inner join rental_home_like rhl on rhl.rental_home_no = rh.rental_home_no
inner join rental_home_theme rht on rht.rental_home_no = rh.rental_home_no
where (rht.theme_no) in (
    select
    theme_no
    from theme
    where theme_name like '%?%'
)
limit 1;

-- 한달살기 - 위치 검색
select
rhp.uuid_photo_name,
rh.rental_home_no,
rh.name,
rh.price,
count(rhl.rental_home_no) as like_count
from rental_home rh
inner join rental_home_photo rhp on rhp.rental_home_no = rh.rental_home_no
inner join rental_home_like rhl on rhl.rental_home_no = rh.rental_home_no
inner join region r on r.region_no = rh.region_no
where (r.region_name) in (
    select
    region_no
    from region
    where region_name like '%?%';
)
limit 1;

------------------------------------------------------------------------------------------------------------

-- 한달살기 - 지도

------------------------------------------------------------------------------------------------------------

-- 한달살기 - 숙소 상세
select
rp.photo_no,
rp.ori_photo_name,
rp.uuid_photo_name,
rp.photo_explanation,
r.region_no,
r.name,
r.explanation,
r.address,
r.price,
r.capacity,
r.lat,
r.lon,
r.state,
r.hosting_start_date,
r.hosting_end_date,
r.registe_date
from rental_home r
inner join rental_home_photo rp on rp.rental_home_no = r.rental_home_no
order by rp.photo_no desc;

-- 숙소 편의시설
select
rhf.facility_name,
rhd.facility_count
from rental_home_facility rhf
inner join rental_home_detail rhd on rhf.facility_no = rhd.facility_no
where rhd.rental_home_no = ?;

-- 숙소 리뷰 평균
select
avg(rhr.score) as average
from reservation r
inner join rental_home_review rhr on r.reservation_no = rhr.reservation_no
where r.rental_home_no = ?

-- 숙소 리뷰
select
rhr.created_date,
rhr.score,
rhr.review
from reservation r
inner join rental_home_review rhr on r.reservation_no = rhr.reservation_no
where r.rental_home_no = ?
order by rhr.created_date;


------------------------------------------------------------------------------------------------------------

-- 숙소 신고
insert into rental_home_report(rental_home_no, member_no, report_category_no, content)
values(1, 1, 2, '갔는데 숙소가 없어요 호스트와 연락도 안되요');

------------------------------------------------------------------------------------------------------------

-- 숙소 예약
insert into reservation(member_no, rental_home_no, start_date, end_date, state)
values(1, 1, '2024-03-24', 2024-05-24, '0');


------------------------------------------------------------------------------------------------------------

-- 숙소 결제
insert into payment(payment_no, amount, card_no, validity_date, state)
values('API로 받아옴', 1000000, '1111-1111-1111-1111', '2024-10-01', 1);

------------------------------------------------------------------------------------------------------------

-- 숙소별 정산
select
rh.rental_home_no,
rh.name,
r.region_name,
rh.address,
rh.capacity,
r.start_date,
r.end_date,
p.amount
from rental_home rh
inner join region r on rh.region_no = r.region_no
inner join reservation r on r.rental_home_no = rh.rental_home_no
inner join payment p on p.reservation_no = r.reservation_no
where rh.member_no = ?
order by r.start_date asc;

-- 숙소 정산날짜별 정산 상세
select
rh.name,
rh.price
rh.capacity
rh.address,
r.region_name,
rv.start_date,
rv.end_date,
p.amount
m.name
from rental_home rh
inner join region r on rh.region_no = r.region_no
inner join reservation rv on r.rental_home_no = rh.rental_home_no
inner join member m on m.member_no = rv.member_no
inner join payment p on p.reservation_no = r.reservation_no
where rh.rental_home_no = ?
order by r.start_date asc;




-- 숙소 신고 유형 - 물리이름 안지어짐(데이터 타입 늘려야함)
-- 숙소 사진의 순서 표시
-- 숙소 상세(이용규칙, 청소비 db에 저장해야함)
-- reservation - payment_date, chat_file null 로 설정, 요청사항 db에 없음
-- 결제 - 누굴위한 예약 - db에 없음
-- 예약 내역, 결제 payment_date 겹침 예약 내역에서 지워야 할듯
-- 시작일로부터 몇일후를 정산날짜로 할지
-- 에약 테이블 시작, 종료일 DATETIME으로




