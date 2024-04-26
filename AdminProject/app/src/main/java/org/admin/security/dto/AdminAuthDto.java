package org.admin.security.dto;

import lombok.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@Setter
@ToString
public class AdminAuthDto extends User {
    private static final Log  log = LogFactory.getLog(AdminAuthDto.class);
    private String id;
    private String password;
    private int no;

    public AdminAuthDto(String username, String password, Collection<? extends GrantedAuthority> authorities, String id, boolean fromSocial) {
        super(username, password, authorities);
        this.id = id;
        this.password = password;

    }
}
