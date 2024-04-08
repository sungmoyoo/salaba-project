package org.admin.domain;

import lombok.Data;

import java.sql.Date;

@Data
public class Rental {
    private int rentalNo;
    private int regionNo;
    private String name;
    private String explanation;
    private String address;
    private int price;
    private int cleanFee;
    private int capacity;
    private char state;
    private String stateStr;
    private Date regDate;
    private Member host;
    private Member reporter;

}
