package org.admin.domain;

import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
public class Rental {
    private int rentalNo;
    private int regionNo;
    private String name;
    private String explanation;
    private String address;
    private String rule;
    private int price;
    private int cleanFee;
    private int capacity;
    private String state;
    private String stateStr;
    private Date regDate;
    private Member host;
    private List<Theme> themes;
    private List<Facility> facilities;
    private List<Photo> photos;
}
