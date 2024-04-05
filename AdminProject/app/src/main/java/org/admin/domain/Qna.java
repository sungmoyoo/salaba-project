package org.admin.domain;

import lombok.Data;

import java.sql.Date;

@Data
public class Qna {
    private int questionNo;
    private int memberNo;
    private String title;
    private String content;
    private char state;
    private Date regDate;
    private String answer;
    private Date ansDate;
}
