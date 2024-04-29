package org.admin.security.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserSignupResponseDto {

    private Long userNo;
    private String email;
    private String name;
    private LocalDateTime regdate;

}
