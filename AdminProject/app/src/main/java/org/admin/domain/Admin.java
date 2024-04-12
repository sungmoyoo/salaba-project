package org.admin.domain;

import lombok.Data;

import java.sql.Date;

@Data
public class Admin {
    private int no;
    private String id;
    private String password;
    private String authority;
    private Date regDate;
}
