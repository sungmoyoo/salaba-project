-- 회원
DROP TABLE IF EXISTS member;

-- 게시글 카테고리
DROP TABLE IF EXISTS board_category;

-- 게시글첨부파일
DROP TABLE IF EXISTS board_file;

-- 게시글
DROP TABLE IF EXISTS board;

-- 포인트내역
DROP TABLE IF EXISTS point_history;

-- 유저-선호사항
DROP TABLE IF EXISTS member_preference;

-- 문의파일
DROP TABLE IF EXISTS question_file;

-- 문의내역
DROP TABLE IF EXISTS question;

-- 알림내역
DROP TABLE IF EXISTS notify_history;

-- 댓글
DROP TABLE IF EXISTS comment;

-- 답글
DROP TABLE IF EXISTS reply;

-- 게시판 신고 상세
DROP TABLE IF EXISTS board_report_detail;

-- 게시판 신고 파일
DROP TABLE IF EXISTS board_report_file;

-- 즐겨찾기
DROP TABLE IF EXISTS bookmark;

-- 숙소
DROP TABLE IF EXISTS rental_home;

-- 숙소신고
DROP TABLE IF EXISTS rental_home_report;

-- 숙소신고유형
DROP TABLE IF EXISTS rental_home_report_category;

-- 지역
DROP TABLE IF EXISTS region;

-- 등급
DROP TABLE IF EXISTS grade;

-- 숙소_테마
DROP TABLE IF EXISTS rental_home_theme;

-- 테마
DROP TABLE IF EXISTS theme;

-- 숙소리뷰
DROP TABLE IF EXISTS rental_home_review;

-- 결제
DROP TABLE IF EXISTS payment;

-- 예약내역
DROP TABLE IF EXISTS reservation;

-- 숙소 상세
DROP TABLE IF EXISTS rental_home_detail;

-- 게시판 신고유형
DROP TABLE IF EXISTS board_report_category;

-- 국가
DROP TABLE IF EXISTS nation;

-- 숙소 사진
DROP TABLE IF EXISTS rental_home_photo;

-- 문의 답변
DROP TABLE IF EXISTS qna;

-- 숙소추천수
DROP TABLE IF EXISTS rental_home_like;

-- 숙소시설
DROP TABLE IF EXISTS rental_home_facility;

-- 말머리
DROP TABLE IF EXISTS head;

-- 게시글 추천수
DROP TABLE IF EXISTS board_like;

-- 게시글 공개범위
DROP TABLE IF EXISTS board_scope;

-- 회원
CREATE TABLE member (
	member_no INT NOT NULL,
	nation_no INT NULL,
	email VARCHAR(30) NOT NULL,
	password VARCHAR(255) NOT NULL,
	name VARCHAR(20) NOT NULL,
	nickname VARCHAR(20) NOT NULL,
	birthday DATE NOT NULL,
	tel_no VARCHAR(15) NULL,
	grade_no INT DEFAULT '1',
	state CHAR(1) DEFAULT '0',
	address VARCHAR(255) NULL,
	sex CHAR(1) NULL,
	join_date DATE DEFAULT (current_date),
	last_login_date DATE DEFAULT (current_date),
	exit_date DATE NULL,
	warning_count INT DEFAULT '0',
	photo VARCHAR(255) NULL
);

-- 회원
ALTER TABLE member
	ADD CONSTRAINT PK_member -- 회원 기본키
	PRIMARY KEY (
	member_no -- 회원번호
	);

-- 회원 유니크 인덱스
CREATE UNIQUE INDEX UIX_member
	ON member ( -- 회원
				email ASC,    -- 이메일
				nickname ASC  -- 닉네임
	);

ALTER TABLE member
	MODIFY COLUMN member_no INT NOT NULL AUTO_INCREMENT;

-- 게시글 카테고리
CREATE TABLE board_category (
	board_category_no INT NOT NULL,
	board_category_name char(10) NOT NULL
);

-- 게시글 카테고리
ALTER TABLE board_category
	ADD CONSTRAINT PK_board_category -- 게시글 카테고리 Primary key
	PRIMARY KEY (
	board_category_no -- 게시글카테고리번호
	);

-- 게시글 카테고리 유니크 인덱스
CREATE UNIQUE INDEX UIX_board_category
	ON board_category ( -- 게시글 카테고리
				board_category_name ASC -- 게시글카테고리명
	);

-- 게시글첨부파일
CREATE TABLE board_file (
	file_no INT NOT NULL,
	board_no INT NOT NULL,
	ori_file_name VARCHAR(255) NOT NULL,
	uuid_file_name VARCHAR(255) NOT NULL
);

-- 게시글첨부파일
ALTER TABLE board_file
	ADD CONSTRAINT PK_board_file -- 게시글첨부파일 기본키
	PRIMARY KEY (
	file_no -- 파일번호
	);

-- 게시글첨부파일 유니크 인덱스
CREATE UNIQUE INDEX UIX_board_file
	ON board_file ( -- 게시글첨부파일
				uuid_file_name ASC -- 파일명(uuid)
	);

ALTER TABLE board_file
	MODIFY COLUMN file_no INT NOT NULL AUTO_INCREMENT;

-- 게시글
CREATE TABLE board (
	board_no INT NOT NULL,
	member_no INT NOT NULL,
	board_category_no INT NOT NULL,
	head_no INT NOT NULL,
	title VARCHAR(30) NOT NULL,
	content text NULL,
	like_count INT DEFAULT '0',
	created_date DATE DEFAULT (current_date),
	view_count INT DEFAULT '0',
	state CHAR(1) DEFAULT '0',
	scope_no INT NOT NULL DEFAULT '0',
	region_no INT NULL
);

-- 게시글
ALTER TABLE board
	ADD CONSTRAINT PK_board -- 게시글 기본키
	PRIMARY KEY (
	board_no -- 게시글번호
	);

ALTER TABLE board
	MODIFY COLUMN board_no INT NOT NULL AUTO_INCREMENT;

-- 포인트내역
CREATE TABLE point_history (
	member_no INT NOT NULL,
	save_content VARCHAR(30) NOT NULL,
	save_point INT NOT NULL,
	save_date DATE DEFAULT (current_date)
);

-- 유저-선호사항
CREATE TABLE member_preference (
	member_no INT NOT NULL,
	theme_no INT NOT NULL
);

-- 유저-선호사항
ALTER TABLE member_preference
	ADD CONSTRAINT PK_member_preference -- 유저-선호사항 기본키
	PRIMARY KEY (
	member_no, -- 회원번호
	theme_no   -- 테마번호
	);

-- 문의파일
CREATE TABLE question_file (
	question_file_no INT NOT NULL,
	question_no INT NOT NULL,
	ori_file_name VARCHAR(255) NOT NULL,
	uuid_file_name VARCHAR(255) NOT NULL
);

-- 문의파일
ALTER TABLE question_file
	ADD CONSTRAINT PK_question_file -- 문의파일 기본키
	PRIMARY KEY (
	question_file_no -- 문의파일번호
	);

-- 문의파일 유니크 인덱스
CREATE UNIQUE INDEX UIX_question_file
	ON question_file ( -- 문의파일
				uuid_file_name ASC -- 파일명(uuid)
	);

ALTER TABLE question_file
	MODIFY COLUMN question_file_no INT NOT NULL AUTO_INCREMENT;

-- 문의내역
CREATE TABLE question (
	question_no INT NOT NULL,
	member_no INT NOT NULL,
	title VARCHAR(30) NOT NULL,
	content text NULL,
	state char(1) NOT NULL,
	register_date DATE DEFAULT (current_date)
);

-- 문의내역
ALTER TABLE question
	ADD CONSTRAINT PK_question -- 문의내역 기본키
	PRIMARY KEY (
	question_no -- 문의번호
	);

ALTER TABLE question
	MODIFY COLUMN question_no INT NOT NULL AUTO_INCREMENT;

-- 알림내역
CREATE TABLE notify_history (
	notify_no INT NOT NULL,
	member_no INT NOT NULL,
	content VARCHAR(255) NOT NULL,
	notify_date DATE DEFAULT (current_date),
	state char(1) DEFAULT '0'
);

-- 알림내역
ALTER TABLE notify_history
	ADD CONSTRAINT PK_notify_history -- 알림내역 기본키
	PRIMARY KEY (
	notify_no -- 알림번호
	);

ALTER TABLE notify_history
	MODIFY COLUMN notify_no INT NOT NULL AUTO_INCREMENT;

-- 댓글
CREATE TABLE comment (
	comment_no INT NOT NULL,
	board_no INT NOT NULL,
	member_no INT NOT NULL,
	content text NOT NULL,
	created_date DATETIME DEFAULT (current_date),
	state char(1) DEFAULT '0'
);

-- 댓글
ALTER TABLE comment
	ADD CONSTRAINT PK_comment -- 댓글 기본키
	PRIMARY KEY (
	comment_no -- 댓글번호
	);

ALTER TABLE comment
	MODIFY COLUMN comment_no INT NOT NULL AUTO_INCREMENT;

-- 답글
CREATE TABLE reply (
	reply_no INT NOT NULL,
	comment_no INT NOT NULL,
	member_no INT NOT NULL,
	content text NOT NULL,
	created_date DATETIME DEFAULT (current_date),
	state char(1) DEFAULT '0'
);

-- 답글
ALTER TABLE reply
	ADD CONSTRAINT PK_reply -- 답글 기본키
	PRIMARY KEY (
	reply_no -- 답글번호
	);

ALTER TABLE reply
	MODIFY COLUMN reply_no INT NOT NULL AUTO_INCREMENT;

-- 게시판 신고 상세
CREATE TABLE board_report_detail (
	report_no INT NOT NULL,
	member_no INT NOT NULL,
	report_category_no INT NOT NULL,
	content text NULL,
	report_date DATE DEFAULT (current_date),
	state char(1) DEFAULT '0',
	report_target_no INT NOT NULL,
	report_target_type CHAR(1) NOT NULL
);

-- 게시판 신고 상세
ALTER TABLE board_report_detail
	ADD CONSTRAINT PK_board_report_detail -- 게시판 신고 상세 기본키
	PRIMARY KEY (
	report_no -- 신고번호
	);

ALTER TABLE board_report_detail
	MODIFY COLUMN report_no INT NOT NULL AUTO_INCREMENT;

-- 게시판 신고 파일
CREATE TABLE board_report_file (
	report_no INT NOT NULL,
	ori_file_name VARCHAR(255) NOT NULL,
	uuid_file_name VARCHAR(255) NOT NULL
);

-- 게시판 신고 파일 유니크 인덱스
CREATE UNIQUE INDEX UIX_board_report_file
	ON board_report_file ( -- 게시판 신고 파일
				uuid_file_name ASC -- 파일명(uuid)
	);

-- 즐겨찾기
CREATE TABLE bookmark (
	rental_home_no INT NOT NULL,
	member_no INT NOT NULL
);

-- 숙소
CREATE TABLE rental_home (
	rental_home_no INT NOT NULL,
	member_no INT NOT NULL,
	region_no INT NOT NULL,
	name VARCHAR(255) NOT NULL,
	explanation TEXT NOT NULL,
	address TEXT NOT NULL,
	price INT NOT NULL,
	capacity INT NOT NULL,
	lat VARCHAR(30) NOT NULL,
	lon VARCHAR(30) NOT NULL,
	state CHAR(1) NOT NULL,
	hosting_start_date DATE NOT NULL,
	hosting_end_date DATE NOT NULL,
	registe_date DATE DEFAULT (current_date),
	rental_home_rule TEXT NULL,
	clean_fee INT NOT NULL
);

-- 숙소
ALTER TABLE rental_home
	ADD CONSTRAINT PK_rental_home -- 숙소 기본키
	PRIMARY KEY (
	rental_home_no -- 숙소번호
	);

ALTER TABLE rental_home
	MODIFY COLUMN rental_home_no INT NOT NULL AUTO_INCREMENT;

-- 숙소신고
CREATE TABLE rental_home_report (
	rental_home_no INT NOT NULL,
	member_no INT NOT NULL,
	report_category_no INT NOT NULL,
	content text NULL
);

-- 숙소신고
ALTER TABLE rental_home_report
	ADD CONSTRAINT PK_rental_home_report -- 숙소신고 기본키
	PRIMARY KEY (
	rental_home_no, -- 숙소번호
	member_no       -- 회원번호
	);

-- 숙소신고유형
CREATE TABLE rental_home_report_category (
	rental_home_report_no INT NOT NULL,
	rental_home_report_name VARCHAR(30) NOT NULL
);

-- 숙소신고유형
ALTER TABLE rental_home_report_category
	ADD CONSTRAINT PK_rental_home_report_category -- 숙소신고유형 기본키
	PRIMARY KEY (
	rental_home_report_no -- 신고유형번호
	);

-- 지역
CREATE TABLE region (
	region_no INT NOT NULL,
	region_name VARCHAR(50) NOT NULL,
	nation_no INT NOT NULL
);

-- 지역
ALTER TABLE region
	ADD CONSTRAINT PK_region -- 지역 기본키
	PRIMARY KEY (
	region_no -- 지역번호
	);

-- 지역 유니크 인덱스
CREATE UNIQUE INDEX UIX_region
	ON region ( -- 지역
				region_name ASC -- 지역명
	);

-- 등급
CREATE TABLE grade (
	grade_no INT NOT NULL,
	grade_name VARCHAR(5) NOT NULL
);

-- 등급
ALTER TABLE grade
	ADD CONSTRAINT PK_grade -- 등급 기본키
	PRIMARY KEY (
	grade_no -- 등급카테고리번호
	);

-- 등급 유니크 인덱스
CREATE UNIQUE INDEX UIX_grade
	ON grade ( -- 등급
				grade_name ASC -- 등급명
	);

-- 숙소_테마
CREATE TABLE rental_home_theme (
	rental_home_no INT NOT NULL,
	theme_no INT NOT NULL
);

-- 숙소_테마
ALTER TABLE rental_home_theme
	ADD CONSTRAINT PK_rental_home_theme -- 숙소_테마 기본키
	PRIMARY KEY (
	rental_home_no, -- 숙소번호
	theme_no        -- 테마번호
	);

-- 테마
CREATE TABLE theme (
	theme_no INT NOT NULL,
	theme_name VARCHAR(20) NOT NULL
);

-- 테마
ALTER TABLE theme
	ADD CONSTRAINT PK_theme -- 테마 기본키
	PRIMARY KEY (
	theme_no -- 테마번호
	);

-- 숙소리뷰
CREATE TABLE rental_home_review (
	reservation_no INT NOT NULL,
	created_date DATE DEFAULT (current_date),
	score INT NOT NULL,
	review VARCHAR(255) NOT NULL,
	state CHAR(1) DEFAULT '0'
);

-- 숙소리뷰
ALTER TABLE rental_home_review
	ADD CONSTRAINT PK_rental_home_review -- 숙소리뷰 기본키
	PRIMARY KEY (
	reservation_no -- 예약번호
	);

-- 결제
CREATE TABLE payment (
	reservation_no INT NOT NULL,
	payment_no VARCHAR(255) NOT NULL,
	payment_date DATETIME DEFAULT (current_date),
	amount INT NOT NULL,
	card_no VARCHAR(20) NOT NULL,
	validity_date DATE NOT NULL,
	state CHAR(1) DEFAULT '0'
);

-- 결제
ALTER TABLE payment
	ADD CONSTRAINT PK_payment -- 결제 기본키
	PRIMARY KEY (
	reservation_no -- 예약번호
	);

-- 결제 유니크 인덱스
CREATE UNIQUE INDEX UIX_payment
	ON payment ( -- 결제
				payment_no ASC -- 결제번호
	);

-- 예약내역
CREATE TABLE reservation (
	reservation_no INT NOT NULL,
	member_no INT NOT NULL,
	rental_home_no INT NOT NULL,
	start_date DATETIME NOT NULL,
	end_date DATETIME NOT NULL,
	state CHAR(1) DEFAULT '0',
	chat_file_name VARCHAR(255) NOT NULL,
	number_of_people INT DEFAULT '1'
);

-- 예약내역
ALTER TABLE reservation
	ADD CONSTRAINT PK_reservation -- 예약내역 기본키
	PRIMARY KEY (
	reservation_no -- 예약번호
	);

ALTER TABLE reservation
	MODIFY COLUMN reservation_no INT NOT NULL AUTO_INCREMENT;

-- 숙소 상세
CREATE TABLE rental_home_detail (
	rental_home_no INT NOT NULL,
	facility_no INT NOT NULL,
	facility_count INT NOT NULL
);

-- 숙소 상세
ALTER TABLE rental_home_detail
	ADD CONSTRAINT PK_rental_home_detail -- 숙소 상세 기본키
	PRIMARY KEY (
	rental_home_no, -- 숙소번호
	facility_no     -- 숙소시설번호
	);

-- 게시판 신고유형
CREATE TABLE board_report_category (
	report_category_no INT NOT NULL,
	report_type VARCHAR(10) NOT NULL
);

-- 게시판 신고유형
ALTER TABLE board_report_category
	ADD CONSTRAINT PK_board_report_category -- 게시판 신고유형 기본키
	PRIMARY KEY (
	report_category_no -- 신고유형번호
	);

-- 게시판 신고유형 유니크 인덱스
CREATE UNIQUE INDEX UIX_board_report_category
	ON board_report_category ( -- 게시판 신고유형
				report_type ASC -- 신고유형
	);

-- 국가
CREATE TABLE nation (
	nation_no INT NOT NULL,
	nation_name VARCHAR(10) NOT NULL
);

-- 국가
ALTER TABLE nation
	ADD CONSTRAINT PK_nation -- 국가 기본키
	PRIMARY KEY (
	nation_no -- 국가번호
	);

-- 국가 유니크 인덱스
CREATE UNIQUE INDEX UIX_nation
	ON nation ( -- 국가
				nation_name ASC -- 국가명
	);

-- 숙소 사진
CREATE TABLE rental_home_photo (
	photo_no INT NOT NULL,
	ori_photo_name VARCHAR(255) NOT NULL,
	uuid_photo_name VARCHAR(255) NOT NULL,
	photo_explanation VARCHAR(20) NOT NULL,
	rental_home_no INT NOT NULL,
	photo_order INT DEFAULT '0'
);

-- 숙소 사진
ALTER TABLE rental_home_photo
	ADD CONSTRAINT PK_rental_home_photo -- 숙소 사진 기본키
	PRIMARY KEY (
	photo_no -- 사진번호
	);

-- 숙소 사진 유니크 인덱스
CREATE UNIQUE INDEX UIX_rental_home_photo
	ON rental_home_photo ( -- 숙소 사진
				uuid_photo_name ASC -- 사진(uuid)
	);

ALTER TABLE rental_home_photo
	MODIFY COLUMN photo_no INT NOT NULL AUTO_INCREMENT;

-- 문의 답변
CREATE TABLE qna (
	question_no INT NOT NULL,
	content text NULL,
	created_date DATE DEFAULT (current_date)
);

-- 문의 답변
ALTER TABLE qna
	ADD CONSTRAINT PK_qna -- 문의 답변 기본키
	PRIMARY KEY (
	question_no -- 문의번호
	);

-- 숙소추천수
CREATE TABLE rental_home_like (
	rental_home_no INT NOT NULL,
	member_no INT NOT NULL
);

-- 숙소추천수
ALTER TABLE rental_home_like
	ADD CONSTRAINT PK_rental_home_like -- 숙소추천수 기본키
	PRIMARY KEY (
	rental_home_no, -- 숙소번호
	member_no       -- 회원번호
	);

-- 숙소시설
CREATE TABLE rental_home_facility (
	facility_no INT NOT NULL,
	facility_name VARCHAR(30) NOT NULL
);

-- 숙소시설
ALTER TABLE rental_home_facility
	ADD CONSTRAINT PK_rental_home_facility -- 숙소시설 기본키
	PRIMARY KEY (
	facility_no -- 숙소시설번호
	);

-- 말머리
CREATE TABLE head (
	head_no INT NOT NULL,
	head_content char(5) NOT NULL
);

-- 말머리
ALTER TABLE head
	ADD CONSTRAINT PK_head -- 말머리 기본키
	PRIMARY KEY (
	head_no -- 말머리 번호
	);

-- 게시글 추천수
CREATE TABLE board_like (
	board_no INT NOT NULL,
	member_no INT NOT NULL
);

-- 게시글 추천수
ALTER TABLE board_like
	ADD CONSTRAINT PK_board_like -- 게시글 추천수 기본키
	PRIMARY KEY (
	board_no,  -- 게시글번호
	member_no  -- 회원번호
	);

-- 게시글 공개범위
CREATE TABLE board_scope (
	scope_no INT NOT NULL,
	scope_name char(10) NOT NULL
);

-- 게시글 공개범위
ALTER TABLE board_scope
	ADD CONSTRAINT PK_board_scope -- 게시글 공개범위 기본키
	PRIMARY KEY (
	scope_no -- 게시글 공개범위번호
	);

-- 게시글 공개범위 유니크 인덱스
CREATE UNIQUE INDEX UIX_board_scope
	ON board_scope ( -- 게시글 공개범위
				scope_name ASC -- 게시글 공개 범위명
	);

ALTER TABLE board_scope
	MODIFY COLUMN scope_no INT NOT NULL AUTO_INCREMENT;

-- 회원
ALTER TABLE member
	ADD CONSTRAINT FK_nation_TO_member -- 국가 -> 회원
	FOREIGN KEY (
	nation_no -- 국가번호
	)
	REFERENCES nation ( -- 국가
	nation_no -- 국가번호
	);

-- 회원
ALTER TABLE member
	ADD CONSTRAINT FK_grade_TO_member -- 등급 -> 회원
	FOREIGN KEY (
	grade_no -- 등급카테고리번호
	)
	REFERENCES grade ( -- 등급
	grade_no -- 등급카테고리번호
	);

-- 게시글첨부파일
ALTER TABLE board_file
	ADD CONSTRAINT FK_board_TO_board_file -- 게시글 -> 게시글첨부파일
	FOREIGN KEY (
	board_no -- 게시글번호
	)
	REFERENCES board ( -- 게시글
	board_no -- 게시글번호
	);

-- 게시글
ALTER TABLE board
	ADD CONSTRAINT FK_member_TO_board -- 회원 -> 게시글
	FOREIGN KEY (
	member_no -- 회원번호
	)
	REFERENCES member ( -- 회원
	member_no -- 회원번호
	);

-- 게시글
ALTER TABLE board
	ADD CONSTRAINT FK_board_category_TO_board -- 게시글 카테고리 -> 게시글
	FOREIGN KEY (
	board_category_no -- 게시글카테고리번호
	)
	REFERENCES board_category ( -- 게시글 카테고리
	board_category_no -- 게시글카테고리번호
	);

-- 게시글
ALTER TABLE board
	ADD CONSTRAINT FK_head_TO_board -- 말머리 -> 게시글
	FOREIGN KEY (
	head_no -- 말머리 번호
	)
	REFERENCES head ( -- 말머리
	head_no -- 말머리 번호
	);

-- 게시글
ALTER TABLE board
	ADD CONSTRAINT FK_board_scope_TO_board -- 게시글 공개범위 -> 게시글
	FOREIGN KEY (
	scope_no -- 게시글 공개범위번호
	)
	REFERENCES board_scope ( -- 게시글 공개범위
	scope_no -- 게시글 공개범위번호
	);

-- 게시글
ALTER TABLE board
	ADD CONSTRAINT FK_region_TO_board -- 지역 -> 게시글
	FOREIGN KEY (
	region_no -- 지역번호
	)
	REFERENCES region ( -- 지역
	region_no -- 지역번호
	);

-- 포인트내역
ALTER TABLE point_history
	ADD CONSTRAINT FK_member_TO_point_history -- 회원 -> 포인트내역
	FOREIGN KEY (
	member_no -- 회원번호
	)
	REFERENCES member ( -- 회원
	member_no -- 회원번호
	);

-- 유저-선호사항
ALTER TABLE member_preference
	ADD CONSTRAINT FK_member_TO_member_preference -- 회원 -> 유저-선호사항
	FOREIGN KEY (
	member_no -- 회원번호
	)
	REFERENCES member ( -- 회원
	member_no -- 회원번호
	);

-- 유저-선호사항
ALTER TABLE member_preference
	ADD CONSTRAINT FK_theme_TO_member_preference -- 테마 -> 유저-선호사항
	FOREIGN KEY (
	theme_no -- 테마번호
	)
	REFERENCES theme ( -- 테마
	theme_no -- 테마번호
	);

-- 문의파일
ALTER TABLE question_file
	ADD CONSTRAINT FK_question_TO_question_file -- 문의내역 -> 문의파일
	FOREIGN KEY (
	question_no -- 문의번호
	)
	REFERENCES question ( -- 문의내역
	question_no -- 문의번호
	);

-- 문의내역
ALTER TABLE question
	ADD CONSTRAINT FK_member_TO_question -- 회원 -> 문의내역
	FOREIGN KEY (
	member_no -- 회원번호
	)
	REFERENCES member ( -- 회원
	member_no -- 회원번호
	);

-- 알림내역
ALTER TABLE notify_history
	ADD CONSTRAINT FK_member_TO_notify_history -- 회원 -> 알림내역
	FOREIGN KEY (
	member_no -- 회원번호
	)
	REFERENCES member ( -- 회원
	member_no -- 회원번호
	);

-- 댓글
ALTER TABLE comment
	ADD CONSTRAINT FK_board_TO_comment -- 게시글 -> 댓글
	FOREIGN KEY (
	board_no -- 게시글번호
	)
	REFERENCES board ( -- 게시글
	board_no -- 게시글번호
	);

-- 댓글
ALTER TABLE comment
	ADD CONSTRAINT FK_member_TO_comment -- 회원 -> 댓글
	FOREIGN KEY (
	member_no -- 회원번호
	)
	REFERENCES member ( -- 회원
	member_no -- 회원번호
	);

-- 답글
ALTER TABLE reply
	ADD CONSTRAINT FK_comment_TO_reply -- 댓글 -> 답글
	FOREIGN KEY (
	comment_no -- 댓글번호
	)
	REFERENCES comment ( -- 댓글
	comment_no -- 댓글번호
	);

-- 답글
ALTER TABLE reply
	ADD CONSTRAINT FK_member_TO_reply -- 회원 -> 답글
	FOREIGN KEY (
	member_no -- 회원번호
	)
	REFERENCES member ( -- 회원
	member_no -- 회원번호
	);

-- 게시판 신고 상세
ALTER TABLE board_report_detail
	ADD CONSTRAINT FK_board_report_category_TO_board_report_detail -- 게시판 신고유형 -> 게시판 신고 상세
	FOREIGN KEY (
	report_category_no -- 신고유형번호
	)
	REFERENCES board_report_category ( -- 게시판 신고유형
	report_category_no -- 신고유형번호
	);

-- 게시판 신고 상세
ALTER TABLE board_report_detail
	ADD CONSTRAINT FK_member_TO_board_report_detail -- 회원 -> 게시판 신고 상세
	FOREIGN KEY (
	member_no -- 회원번호
	)
	REFERENCES member ( -- 회원
	member_no -- 회원번호
	);

-- 게시판 신고 파일
ALTER TABLE board_report_file
	ADD CONSTRAINT FK_board_report_detail_TO_board_report_file -- 게시판 신고 상세 -> 게시판 신고 파일
	FOREIGN KEY (
	report_no -- 신고번호
	)
	REFERENCES board_report_detail ( -- 게시판 신고 상세
	report_no -- 신고번호
	);

-- 즐겨찾기
ALTER TABLE bookmark
	ADD CONSTRAINT FK_member_TO_bookmark -- 회원 -> 즐겨찾기
	FOREIGN KEY (
	member_no -- 회원번호
	)
	REFERENCES member ( -- 회원
	member_no -- 회원번호
	);

-- 즐겨찾기
ALTER TABLE bookmark
	ADD CONSTRAINT FK_rental_home_TO_bookmark -- 숙소 -> 즐겨찾기
	FOREIGN KEY (
	rental_home_no -- 숙소번호
	)
	REFERENCES rental_home ( -- 숙소
	rental_home_no -- 숙소번호
	);

-- 숙소
ALTER TABLE rental_home
	ADD CONSTRAINT FK_region_TO_rental_home -- 지역 -> 숙소
	FOREIGN KEY (
	region_no -- 지역번호
	)
	REFERENCES region ( -- 지역
	region_no -- 지역번호
	);

-- 숙소
ALTER TABLE rental_home
	ADD CONSTRAINT FK_member_TO_rental_home -- 회원 -> 숙소
	FOREIGN KEY (
	member_no -- 회원번호
	)
	REFERENCES member ( -- 회원
	member_no -- 회원번호
	);

-- 숙소신고
ALTER TABLE rental_home_report
	ADD CONSTRAINT FK_member_TO_rental_home_report -- 회원 -> 숙소신고
	FOREIGN KEY (
	member_no -- 회원번호
	)
	REFERENCES member ( -- 회원
	member_no -- 회원번호
	);

-- 숙소신고
ALTER TABLE rental_home_report
	ADD CONSTRAINT FK_rental_home_TO_rental_home_report -- 숙소 -> 숙소신고
	FOREIGN KEY (
	rental_home_no -- 숙소번호
	)
	REFERENCES rental_home ( -- 숙소
	rental_home_no -- 숙소번호
	);

-- 숙소신고
ALTER TABLE rental_home_report
	ADD CONSTRAINT FK_rental_home_report_category_TO_rental_home_report -- 숙소신고유형 -> 숙소신고
	FOREIGN KEY (
	report_category_no -- 신고유형번호
	)
	REFERENCES rental_home_report_category ( -- 숙소신고유형
	rental_home_report_no -- 신고유형번호
	);

-- 지역
ALTER TABLE region
	ADD CONSTRAINT FK_nation_TO_region -- 국가 -> 지역
	FOREIGN KEY (
	nation_no -- 국가번호
	)
	REFERENCES nation ( -- 국가
	nation_no -- 국가번호
	);

-- 숙소_테마
ALTER TABLE rental_home_theme
	ADD CONSTRAINT FK_rental_home_TO_rental_home_theme -- 숙소 -> 숙소_테마
	FOREIGN KEY (
	rental_home_no -- 숙소번호
	)
	REFERENCES rental_home ( -- 숙소
	rental_home_no -- 숙소번호
	);

-- 숙소_테마
ALTER TABLE rental_home_theme
	ADD CONSTRAINT FK_theme_TO_rental_home_theme -- 테마 -> 숙소_테마
	FOREIGN KEY (
	theme_no -- 테마번호
	)
	REFERENCES theme ( -- 테마
	theme_no -- 테마번호
	);

-- 숙소리뷰
ALTER TABLE rental_home_review
	ADD CONSTRAINT FK_reservation_TO_rental_home_review -- 예약내역 -> 숙소리뷰
	FOREIGN KEY (
	reservation_no -- 예약번호
	)
	REFERENCES reservation ( -- 예약내역
	reservation_no -- 예약번호
	);

-- 결제
ALTER TABLE payment
	ADD CONSTRAINT FK_reservation_TO_payment -- 예약내역 -> 결제
	FOREIGN KEY (
	reservation_no -- 예약번호
	)
	REFERENCES reservation ( -- 예약내역
	reservation_no -- 예약번호
	);

-- 예약내역
ALTER TABLE reservation
	ADD CONSTRAINT FK_rental_home_TO_reservation -- 숙소 -> 예약내역
	FOREIGN KEY (
	rental_home_no -- 숙소번호
	)
	REFERENCES rental_home ( -- 숙소
	rental_home_no -- 숙소번호
	);

-- 예약내역
ALTER TABLE reservation
	ADD CONSTRAINT FK_member_TO_reservation -- 회원 -> 예약내역
	FOREIGN KEY (
	member_no -- 회원번호
	)
	REFERENCES member ( -- 회원
	member_no -- 회원번호
	);

-- 숙소 상세
ALTER TABLE rental_home_detail
	ADD CONSTRAINT FK_rental_home_TO_rental_home_detail -- 숙소 -> 숙소 상세
	FOREIGN KEY (
	rental_home_no -- 숙소번호
	)
	REFERENCES rental_home ( -- 숙소
	rental_home_no -- 숙소번호
	);

-- 숙소 상세
ALTER TABLE rental_home_detail
	ADD CONSTRAINT FK_rental_home_facility_TO_rental_home_detail -- 숙소시설 -> 숙소 상세
	FOREIGN KEY (
	facility_no -- 숙소시설번호
	)
	REFERENCES rental_home_facility ( -- 숙소시설
	facility_no -- 숙소시설번호
	);

-- 숙소 사진
ALTER TABLE rental_home_photo
	ADD CONSTRAINT FK_rental_home_TO_rental_home_photo -- 숙소 -> 숙소 사진
	FOREIGN KEY (
	rental_home_no -- 숙소번호
	)
	REFERENCES rental_home ( -- 숙소
	rental_home_no -- 숙소번호
	);

-- 문의 답변
ALTER TABLE qna
	ADD CONSTRAINT FK_question_TO_qna -- 문의내역 -> 문의 답변
	FOREIGN KEY (
	question_no -- 문의번호
	)
	REFERENCES question ( -- 문의내역
	question_no -- 문의번호
	);

-- 숙소추천수
ALTER TABLE rental_home_like
	ADD CONSTRAINT FK_rental_home_TO_rental_home_like -- 숙소 -> 숙소추천수
	FOREIGN KEY (
	rental_home_no -- 숙소번호
	)
	REFERENCES rental_home ( -- 숙소
	rental_home_no -- 숙소번호
	);

-- 숙소추천수
ALTER TABLE rental_home_like
	ADD CONSTRAINT FK_member_TO_rental_home_like -- 회원 -> 숙소추천수
	FOREIGN KEY (
	member_no -- 회원번호
	)
	REFERENCES member ( -- 회원
	member_no -- 회원번호
	);

-- 게시글 추천수
ALTER TABLE board_like
	ADD CONSTRAINT FK_board_TO_board_like -- 게시글 -> 게시글 추천수
	FOREIGN KEY (
	board_no -- 게시글번호
	)
	REFERENCES board ( -- 게시글
	board_no -- 게시글번호
	);

-- 게시글 추천수
ALTER TABLE board_like
	ADD CONSTRAINT FK_member_TO_board_like -- 회원 -> 게시글 추천수
	FOREIGN KEY (
	member_no -- 회원번호
	)
	REFERENCES member ( -- 회원
	member_no -- 회원번호
	);