package org.admin.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    private long memberNo;
    private int nationNo;
    private String email;
    private String password;
    private String name;
    private String nickname;
    private Date birthDay;
    private String tel;
    private int gradeNo;
    private String gradeName;
    private String state;
    private String stateStr;
    private String address;
    private String sex;
    private LocalDateTime joinDate;
    private LocalDateTime lastLoginDate;
    private LocalDateTime exitDate;
    private int warningCount;
    private String photo;
    private int rentalCount;
    private List<Rental> rentals;
    private List<Role> roles;
}
