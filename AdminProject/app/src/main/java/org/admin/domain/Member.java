package org.admin.domain;

import lombok.Data;

import java.sql.Date;

@Data
public class Member {
    private int memberNo;
    private int nationNo;
    private String email;
    private String password;
    private String name;
    private String nickname;
    private Date birthDay;
    private String tel;
    private int gradeNo;
    private char state;
    private String stateStr;
    private String address;
    private char sex;
    private Date joinDate;
    private Date lastLoginDate;
    private Date exitDate;
    private int warningCount;
    private String photo;
    private int rentalCount;
}
