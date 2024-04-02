package org.admin.domain;

import lombok.Data;

import java.sql.Date;

@Data
public class RentalReport {
    private int rentalNo;
    private int memberNo;
    private int category;
    private String categoryName;
    private String content;
    private char state;
    private Date createdDate;

}
