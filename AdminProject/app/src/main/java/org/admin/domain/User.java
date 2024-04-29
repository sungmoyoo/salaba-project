package org.admin.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Role role;
    private long userNo;
    private String email;
    private String name;
    private String password;
    private LocalDateTime regDate;
    private int enable;

}
