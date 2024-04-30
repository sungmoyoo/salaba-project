package org.admin.security.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class MemberSignupResponseDto {

    private Long memberNo;
    private String email;
    private String name;
    private LocalDateTime regdate;

}
