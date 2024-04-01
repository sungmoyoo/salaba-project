-- 숙소 신고 내역 목록
select
  t1.rental_home_no,
  t1.content,
  t1.state,
  t1.report_date,
  t2.rental_home_report_name
from 
  rental_home_report t1,
  rental_home_report_category t2
where
  t1.report_category_no = t2.rental_home_report_no
order by
  t1.report_date


-- 숙소 신고 내용 보기 -- UI 추가 해야함
select 
  t1.rental_home_no,
  t1.member_no,
  t1.content,
  t1.report_date,
  t1.state
  t2.report_category_name
from 
  rental_home_report t1, rental_home_report_category
where
  t1.rental_home_no = #{rental_home_no} and
  t1.report_category_no = t2.report_category_no

-- 숙소 신고 처리
update 
  rental_home_report 
set 
  state = #{state} 
where 
  rental_home_no = #{rental_home_no}

update
  rental_home
set
  state = #{state}
where
  rental_home_no = #{rental_home_no}

-- 게시글 & 댓글 신고 목록 보기
select
  report_no,
  content,
  state,
  report_date,
  report_target_type
from
  board_report_detail
where
  report_target_type = #{report_target_type}
order by
  report_date desc

-- 게시글 & 댓글 신고 내용 보기
select
  t1.report_no,
  t1.member_no,
  t1.content,
  t1.report_date,
  t1.report_target_no,
  t1.state,
  t2.uuid_file_name,
  t2.ori_file_name,
  t3.report_type
from
  board_report_detail t1,
  board_report_file t2,
  board_report_category t3
where
  t1.report_no = #{report_no} and
  t1.report_no = t2.report_no and
  t1.report_category_no = t3.report_category_no


-- 게시글 & 댓글 신고 처리
update
  board_report_detail
set
  state = #{state}
where
  report_no = #{report_no}

update
  member
set
  warning_count = #{warning_count}
where 
  member_no = #{member_no}


-- 문의 내역 목록 조회
select
  question_no,
  title,
  state,
  register_date
from
  question
order by
  register_date

-- 문의 내역 보기
select
  t1.title,
  t1.content,
  t2.ori_file_name,
  t2.uuid_file_name,
  t3.nickname
from
  question t1,
  question_file t2,
  member t3
where
  ( t1.member_no = #{member_no} and t3.member_no = #{member_no} ) and
  ( t1.question_no = #{question_no} and t2.question_no = #{question_no} )

-- 문의 답변
insert into qna(question_no,content) values(#{question_no},#{content})

update
  question
set
  state = #{state}
where
  question_no = #{question_no}


-- 회원 목록
select
  member_no,
  name,
  nickname,
  email,
  tel_no,
  join_date,
  state
from
  member

-- 회원 상세 정보
select
  t1.member_no,
  t1.name,
  t1.email,
  t1.tel_no,
  t1.join_date,
  t2.grade_name,
  t1.state,
  t1.warning_count
from
  member t1,
  grade t2
where
  t1.member_no = #{member_no} and
  t2.grade_no = t1.grade_no

-- 회원 경고 처리
update
  member
set
  warning_count = #{warning_count}
where 
  member_no = #{member_no}


-- 회원 등급 변경
update
  grade
set
  grade_category_no = #{grade_category_no}
where
  member_no = #{member_no}

-- 호스트 목록 조회
select
  t1.member_no,
  t1.name,
  t1.nickname,
  t1.email,
  t1.tel_no,
  t1.join_date,
  count(t2.rental_home_no) regist_count
from
  member t1,
  rental_home t2
where
  t1.member_no = #{member_no} and
  t1.member_no = t2.member_no

-- 호스트 목록 상세보기
select
  t1.member_no,
  t1.name,
  t1.email,
  t2.hosting_start_date,
  t2.hosting_end_date,
  t2.rental_home_no,
  t2.address,
  t3.facility_name
from
  member t1,
  rental_home t2,
  rental_home_facility t3,
  rental_home_detail t4
where
  t1.member_no = #{member_no} and 
  t2.member_no = t1.member_no and
  t3.facility_no in( 100 , 101 , 102 , 103 ) and
  t3.facility_no = t4.facility_no and
  t2.rental_home_no = t4.rental_home_no
order by
  t2.rental_home_no
  

-- 숙소 등록 심사
select
  t1.rental_home_no,
  t1.name home_name,
  t1.registe_date,
  t1.state,
  t2.name user_name
from
  rental_home t1,
  member t2
where
  t1.member_no = t2.member_no
order by
  t1.registe_date desc,
  t1.rental_home_no desc



-- 숙소 등록 심사 상세
select
  t1.rental_home_no,
  t1.registe_date,
  t1.name,
  t1.price,
  t1.lat,
  t1.lon,
  t1.capacity,
  t1.explanation,
  t1.address,
  t2.name,
  t2.member_no,
  t3.uuid_photo_name,
  t3.photo_explanation,
  t4.facility_no,
  t4.facility_count,
  t5.facility_name,
  t6.theme_no,
  t7.theme_name
from
  rental_home t1,
  member t2,
  rental_home_photo t3,
  rental_home_detail t4,
  rental_home_facility t5,
  rental_home_theme t6,
  theme t7
where
  t1.rental_home_no = 1 and 
  ( t1.member_no = t2.member_no and
  t1.rental_home_no = t3.rental_home_no and
  t1.rental_home_no = t4.rental_home_no and
  t4.facility_no = t5.facility_no and
  t1.rental_home_no = t6.rental_home_no and
  t6.theme_no = t7.theme_no )
  
-- 숙소 등록 심사 상태 변경
update
  rental_home
set
  state = #{state}
where
  rental_home_no = #{rental_home_no}

