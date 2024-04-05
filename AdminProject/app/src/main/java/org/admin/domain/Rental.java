package org.admin.domain;

import lombok.Data;

import java.sql.Date;

@Data
public class Rental {
    private int hostNo;
    private String hostName;
    private int rentalNo;
    private int memberNo;
    private int regionNo;
    private String name;
    private String explanation;
    private String address;
    private int price;
    private int cleanFee;
    private int capacity;
    private char state;
    private Date regDate;

}
