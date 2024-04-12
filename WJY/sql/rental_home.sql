use team;
show tables;
desc member;
select * from member;
insert into member(member_no, email, password, name, nickname, birthday)
values(1, 'wnsdus1008@gmail.com', sha2('1234', 256), 'junyeon', 'jun', '1996-10-08');
-- 세팅
-- 국가
select * from nation;
insert into nation(nation_no, nation_name) values (82, 'KR');
insert into nation(nation_no, nation_name) values (81, 'JP');
insert into nation(nation_no, nation_name) values (1, 'USA');
insert into nation(nation_no, nation_name) values (86, 'CH');
insert into nation(nation_no, nation_name) values (44, 'UK');

-- 지역
select * from region;
insert into region(region_no, region_name, nation_no) values(1, 'seoul',82);
insert into region(region_no, region_name, nation_no) values(2, 'jeju', 82);

-- 숙소 편의 시설
select * from rental_home_facility;
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
select * from theme;
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
select * from rental_home_report_category;
insert into rental_home_report_category(rental_home_report_no, rental_home_report_name)
values(1, '부정확하거나 틀린 정보가 있어요');
insert into rental_home_report_category(rental_home_report_no, rental_home_report_name)
values(2, '실제 숙소가 아닙니다');
insert into rental_home_report_category(rental_home_report_no, rental_home_report_name)
values(3, '사기입니다');
insert into rental_home_report_category(rental_home_report_no, rental_home_report_name)
values(4, '불쾌합니다');
insert into rental_home_report_category(rental_home_report_no, rental_home_report_name)
values(5, '기타');

-----------------------------------------------------------------------------------------------

-- 숙소 등록
select * from rental_home;
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
    hosting_start_date,
    hosting_end_date,
    rental_home_rule,
    clean_fee
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
    '2025-03-24',
    '반려동물 입실 불가입니다.',
    20000
    
);


-- 숙소_편의시설 관계 테이블
select * from rental_home_detail;
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
select * from rental_home_theme;
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
select * from rental_home_photo;

insert into rental_home_photo(
ori_photo_name,
uuid_photo_name,
photo_explanation,
rental_home_no,
photo_order
)
VALUES(
    'bathroom.jpg',
    '4fab1fea-88eb-41d1-99f8-d16308e13d4f',
    '화장실- 욕조',
    1,
    1
    );

-----------------------------------------------------------------------------------------------
-- 예약관리
select * from reservation;
-- 예약 하기
insert into reservation(member_no, rental_home_no, start_date, end_date, state, chat_file_name, number_of_people)
values(1, 1, '2024-03-08', '2024-03-15', '0', 'examchat.txt', 2);

select * from payment;
desc payment;
insert into payment(reservation_no, payment_no, payment_date, amount, card_no, validity_date, state)
values(1, 1, '2024-03-28', 400000, '1111-1111-1111-111', '2028-09-01', '0');

-- 승인 대기중인 예약
select
    rent.name as rname,
    m.name as mname,
    rv.reservation_no,
    rv.member_no,
    rv.rental_home_no,
    rv.start_date,
    rv.end_date,
    rv.state,
    p.payment_date
from
    member m
inner join
    reservation rv on m.member_no = rv.member_no
inner join
    rental_home rent on rv.rental_home_no = rent.rental_home_no
inner join
	payment p on rv.reservation_no = p.reservation_no
where
	m.member_no = 1 and
    rv.state = '0'
order by
    rv.start_date asc;


-- 승인 완료된 예약
select
    rent.name as rname,
    m.name as mname,
    rv.reservation_no,
    rv.member_no,
    rv.rental_home_no,
    rv.start_date,
    rv.end_date,
    rv.state,
    p.payment_date
from
    member m
inner join
    reservation rv on m.member_no = rv.member_no
inner join
    rental_home rent on rv.rental_home_no = rent.rental_home_no
inner join
	payment p on rv.reservation_no = p.reservation_no
where
	m.member_no = 1 and
    rv.state = '1'
order by
    rv.start_date asc;

-- 예약 승인
update reservation
set state = '1'
where reservation_no = 1;

-- 에약 거절
update reservation
set state = '2'
where reservation_no = ?;

-----------------------------------------------------------------------------------------------

-- 숙소관리
select
    rental_home_no,
    name,
    explanation,
    address,
    price,
    capacity,
    state,
    hosting_start_date,
    hosting_end_date
from rental_home
where
    member_no = 1;

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
order by rp.photo_order desc;

-- 숙소 편의시설
select
    rhf.facility_name,
    rhd.facility_count
from rental_home_facility rhf
inner join rental_home_detail rhd on rhf.facility_no = rhd.facility_no
where rhd.rental_home_no = 1;

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
    rental_home_no,
    photo_order
)
VALUES(
    'bed.jpg',
    '0fb1a4ab-eebc-4372-96bb-c2b9d31fcb13',
    '침대',
    1,
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
    hosting_end_date = '2025-03-25',
    rental_home_rule = '반려동물 입실불가2',
    clean_fee = 30000
where
rental_home_no = 1;


-- 숙소 편의시설 정보 변경
-- 시설 개수 변경
update rental_home_detail
set facility_count = 4
where rental_home_no = ? and facility_no = ?;
-- 새로운 시설 추가, 삭제
insert into rental_home_detail(rental_home_no, facility_no, facility_count)
values(1, 1, 2);

delete from rental_home_detail where rental_home_no = ? and facility_no = ?

-- 숙소 테마 변경(추가, 삭제)
delete from rental_home_theme
where rental_home_no = ? and theme_no = ?;
select * from rental_home_theme;
insert into rental_home_theme(rental_home_no, theme_no)
values(1, 7);
insert into rental_home_theme(rental_home_no, theme_no)
values(1, 100);

------------------------------------------------------------------------------------------------------------
-- 숙소 추천하기
select * from rental_home_like;
insert into rental_home_like(rental_home_no, member_no) values(1,3);
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

select * from theme;

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
    where theme_name like '%ea%'
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
where (r.region_no) in (
    select
    region_no
    from region
    where region_name like '%j%'
)
limit 1;

------------------------------------------------------------------------------------------------------------

-- 한달살기 - 지도

------------------------------------------------------------------------------------------------------------

-- 한달살기 - 숙소 상세
select
    t1.region_no,
    t1.name,
    t1.explanation,
    t1.address,
    t1.price,
    t1.capacity,
    t1.lat,
    t1.lon,
    t1.state,
    t1.hosting_start_date,
    t1.hosting_end_date,
    t1.registe_date,
    t2.photo_no,
    t2.ori_photo_name,
    t2.uuid_photo_name,
    t2.photo_explanation,
    t2.photo_order,
    t3.facility_no,
    t3.facility_count,
    t4.facility_name,
    t6.created_date,
    t6.score,
    t6.review
from 
    rental_home t1
    inner join rental_home_photo t2 on t2.rental_home_no = t1.rental_home_no
    inner join rental_home_detail t3 on t3.rental_home_no = t1.rental_home_no
    inner join rental_home_facility t4 on t4.facility_no = t3.facility_no
    left outer join reservation t5 on t5.rental_home_no = t1.rental_home_no
    left outer join rental_home_review t6 on t6.reservation_no = t5.reservation_no 
where
    t1.rental_home_no = #{rental_home_no} and
    t6.state = '0'
order by 
    t2.photo_order asc,
    t6.created_date desc;

-- -- 숙소 편의시설
-- select
--     rhf.facility_name,
--     rhd.facility_count
-- from rental_home_facility rhf
-- inner join rental_home_detail rhd on rhf.facility_no = rhd.facility_no
-- where rhd.rental_home_no = 1;

-- 숙소 리뷰 남기기
insert into rental_home_review(reservation_no, created_date, score, review, state) values(3, '2024-03-28', 4, '추천해요', '1');

-- 숙소 리뷰 평균
select
    avg(rhr.score) as average
from reservation r
inner join rental_home_review rhr on r.reservation_no = rhr.reservation_no
where r.rental_home_no = 1;

-- 숙소 리뷰
select
    rhr.created_date,
    rhr.score,
    rhr.review
from reservation rv
inner join rental_home_review rhr on rv.reservation_no = rhr.reservation_no
where rv.rental_home_no = 1
order by rhr.created_date;


------------------------------------------------------------------------------------------------------------

-- 숙소 신고
insert into rental_home_report(rental_home_no, member_no, report_category_no, content)
values(1, 1, 2, '갔는데 숙소가 없어요 호스트와 연락도 안되요');

------------------------------------------------------------------------------------------------------------

-- 숙소 예약
insert into reservation(member_no, rental_home_no, start_date, end_date, state, chat_file_name, number_of_people)
values(3, 1, '2024-03-24', '2024-05-24', '0', 'test3.txt', 1);
select * from reservation;
-----------------------------------------------------------------------------------------------------------

-- 숙소 결제
insert into payment(reservation_no, payment_no, amount, card_no, validity_date, state)
values(3, 'API로 받아옴2', 2000000, '1111-1111-1111-1111', '2024-10-01', 1);
select * from payment;


------------------------------------------------------------------------------------------------------------

-- 숙소별 정산
select
rh.rental_home_no,
rh.name,
r.region_name,
rh.address,
rh.capacity,
rv.start_date,
rv.end_date,
p.amount
from rental_home rh
inner join region r on rh.region_no = r.region_no
inner join reservation rv on rv.rental_home_no = rh.rental_home_no
inner join payment p on p.reservation_no = rv.reservation_no
where rh.rental_home_no = 1
order by rv.start_date asc;

-- 숙소 정산날짜별 정산 상세
select
    rh.name,
    rh.price,
    rh.capacity,
    rh.address,
    r.region_name,
    rv.start_date,
    rv.end_date,
    p.amount,
    m.name
from rental_home rh
inner join region r on rh.region_no = r.region_no
inner join reservation rv on rv.rental_home_no = rh.rental_home_no
inner join member m on m.member_no = rv.member_no
inner join payment p on p.reservation_no = rv.reservation_no
where rh.rental_home_no = 1
order by rv.start_date asc;




-- 숙소 신고 유형 - 물리이름 안지어짐(데이터 타입 늘려야함)
-- 숙소 사진의 순서 표시
-- 숙소 상세(이용규칙, 청소비 db에 저장해야함)
-- reservation - payment_date, chat_file null 로 설정, 요청사항 db에 없음
-- 결제 - 누굴위한 예약 - db에 없음
-- 예약 내역, 결제 payment_date 겹침 예약 내역에서 지워야 할듯
-- 시작일로부터 몇일후를 정산날짜로 할지
-- 에약 테이블 시작, 종료일 DATETIME으로




