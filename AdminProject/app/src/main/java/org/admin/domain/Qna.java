package org.admin.domain;

import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
public class Qna {
    private int questionNo;
    private int memberNo;
    private String title;
    private String content;
    private String state;
    private String stateStr;
    private Date regDate;
    private String answer;
    private Date ansDate;
    private List<Photo> photos;
    private Member writer;
}
