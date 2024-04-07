package org.admin.domain;

import lombok.Data;

import java.sql.Date;

@Data
public class ReportTarget {
    private String content;
    private Date createdDate;
}
