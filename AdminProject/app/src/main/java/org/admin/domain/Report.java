package org.admin.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Report {
    // 숙소신고 테이블의 칼럼을 게시물, 댓글, 답글 신고 테이블이 대부분 포함하고 있기 때문에
    // 하나의 클래스로 관리
    // => thymeleaf에서 데이터를 보여주기에도 용이하다.
    private int reportNo; //숙소신고는 pk칼럼이 없어서 비어있는 상태
    private int category;
    private String categoryName;
    private String content;
    private String state;
    private String stateStr;
    private Date reportDate;
    private int targetNo; //신고당한 숙소번호, 게시물, 댓글, 답글 번호
    private String targetType;
    private Member reporter;
    private Member writer;
    private ReportTarget reportTarget; //게시글, 댓글
    private Rental targetRental; //숙소

    private List<ReportFile> reportFiles;
}
