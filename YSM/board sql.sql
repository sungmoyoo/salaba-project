-- 커뮤니티 홈 화면 조회
select
  b.board_no,
  b.title,
  b.created_date,
  count(bl.member_no) like_count,
  m.name,
  h.head_content,
  bf.file_no,
  bf.uuid_file_name
from
  board b
  inner join head h on b.head_no=h.head_no
  inner join member m on b.member_no=m.member_no
  left join board_like bl on b.board_no=bl.board_no
  left join board_file bf on b.board_no=bf.board_no
group by
  b.board_no
order by
  like_count desc;



-- 정보공유/자유 게시판 리스트 조회
-- 정렬은 기본 최신순, 추천순, 조회순은 order by만 바꾸면 됨
select 
  b.board_no,
  b.title,
  b.view_count,
  b.created_date,
  h.head_content,
  m.name,
  count(distinct bl.member_no) like_count,
  count(distinct c.comment_no) + count(distinct r.reply_no) as comment_reply_count
from 
  board b 
  inner join head h on b.head_no=h.head_no
  inner join member m on b.member_no=m.member_no
  inner join board_like bl on b.board_no=bl.board_no
  left join comment c on b.board_no = c.board_no
  left join reply r on c.comment_no = r.comment_no
where
  b.board_category_no=1
group by
  b.board_no
order by
  b.created_date;



-- 후기 게시판 리스트 조회
select 
  b.title,
  b.view_count,
  b.created_date,
  bf.uuid_file_name uuid_file_name,
  h.head_content,
  m.name,
  count(bl.member_no) like_count
from 
  board b 
  inner join head h on b.head_no=h.head_no
  inner join member m on b.member_no=m.member_no
  left join board_like bl on b.board_no=bl.board_no
  left join board_file bf on b.board_no=bf.board_no
where
  b.board_category_no=3
group by
  b.board_no
order by
  b.created_date desc;



-- 뷰 조회 (공통)
-- null값 허용
select 
  b.title,
  b.view_count,
  b.created_date,
  bf.uuid_file_name uuid_file_name,
  h.head_content,
  m.name,
  (select 
    COUNT(*) 
  from 
    board_like bl 
  where 
    bl.board_no = b.board_no) like_count,
  c.content comment_content,
  mc.name comment_writer,
  c.created_date comment_date,
  r.content reply_content,
  mr.name reply_writer,
  r.created_date reply_date
from 
  board b 
  inner join head h on b.head_no = h.head_no
  inner join member m on b.member_no = m.member_no
  LEFT JOIN board_file bf on b.board_no = bf.board_no
  LEFT JOIN comment c on b.board_no = c.board_no
  LEFT JOIN reply r on c.comment_no = r.comment_no
  LEFT JOIN member mc on c.member_no = mc.member_no
  LEFT JOIN member mr on r.member_no = mr.member_no
where
  b.board_no = 3;



--게시판 카테고리 등록
insert into board_category(category_no, category) values
  (1, "정보"), (2, "자유"), (3, "후기");

--말머리 분류 등록
insert into head(head_no, head_content) values
  (1, "공지"), (2, "잡담"), (3, "꿀팁");

--게시판 글 등록
insert into board(title, content, member_no, board_category_no, head_no) values
  ("자유1", "내용1", 1, 2, 1)

--게시판 첨부파일 등록
insert into board_file(ori_file_name, uuid_file_name, board_no) values
  ("a1.png","585a1429-2a79-4940-9488-6cea5bb9cb95", 3), ("a2.png","52588691-d763-45fe-8de6-8a632e08384a", 3),
  ("b1.png","00ee3655-34d0-4121-a2c0-b4b3a4f9053e", 3);





--댓글 등록
insert into comment(content, board_no, member_no) values
  ("댓글3", 5, 1), ("댓글4", 5, 3);

--답글 등록
insert into reply(content, comment_no, member_no) values
  ("답글1", 9, 2), ("답글2", 9, 2)
  ("답글3", 10, 2), ("답글4", 10, 2);

-- 게시글 추천수 등록
insert into board_like(board_no, member_no) values
  (1, 1), (1, 2), (1, 3),
  (2, 1), (2, 2), (2, 3);






--신고 등록
insert into board_report_detail(content, member_no, report_category_no) values
  ("혐오 댓글 신고합니다.", 1, 1), ("도배글입니다.", 2, 2);

--신고 유형 등록
insert into board_report_category(report_category_no, report_type) values 
  (1, "혐오"), (2, "도배"), (3, "욕설"), (4, "음란");

--신고 파일 등록
insert into board_report_file(report_no, ori_file_name, uuid_file_name) values
  (1, "r1.jpg", "8a223655-43d0-4291-b2a0-d4b3a4f9053e"), 
  (2, "r2.jpg", "3f0f0cea-195b-4b7f-82d5-966ba4f2ab94");







-- 테스트 등급 데이터
insert into grade(grade_no, grade_name) values
(1, "b"), (2, "s"), (3, "g");

-- 테스트 회원 데이터
insert into member(email, password, name, nickname, birthday) values 
  ("1@test.com", 1111, "유저1", "별명1", "2000-01-01"),
  ("2@test.com", 1111, "유저2", "별명2", "2000-01-01"),
  ("3@test.com", 1111, "유저3", "별명3", "2000-01-01");





--게시글 조회수 업데이트
update 
  board
set 
  view_count = view_count + 1
where 
  board_no = "게시글번호";



--게시글 수정
update 
  board
set
  title="변경 제목",
  content="변경 내용",
  -- if문 사용, 말머리 변경 있을 때 말머리 변경
  head_no="변경 말머리번호"
where
  board_no="게시글번호";

--댓글 수정
update
  comment
set
  content="변경 내용"
where
  comment_no="댓글번호";

--답글 수정
update
  reply
set
  content="변경 내용"
where
  reply_no="답글번호";




--게시글 추천수 삭제
--추천수는 바로 삭제해도 된다. 
delete from board_like where board_no="게시글번호"; --게시글이 삭제 되었을 때
delete from board_like where member_no="회원번호"; --회원이 추천 취소 눌렀을 떄


--게시글/댓글/답글 삭제
--: 게시글/댓글/답글은 삭제되어도 데이터를 보존해야 한다.
--: 삭제 시 상태를 삭제 번호로 변경하고, 내용을 비우는 방식
--게시글 삭제
--(상태 변경)
update
  board
set
  state=${state}
where
  board_no=${board_no}
  content=${content};

--(일괄 삭제)
delete from board where state=${state};



--게시글 첨부파일 삭제
delete from board_file where file_no="파일번호"; --특정 파일만 삭제
delete from board_file where board_no="게시글번호"; --전체 삭제



--댓글 삭제
--(상태 변경)
update
  comment
set
  state=${state};
where
  comment_no=${comment_no}
  content=${content};

--(일괄 삭제)
delete from comment where state=${state};



--답글 삭제
--(상태 변경)
update
  board
set
  state=${state};
where
  reply_no=${reply_no}
  content=${content};

--(일괄 삭제)
delete from reply where state=${state};



-- 테스트할 때 delete 또는 drop 사용 금지 truncate 