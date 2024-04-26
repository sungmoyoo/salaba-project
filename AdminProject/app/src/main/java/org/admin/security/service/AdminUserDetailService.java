package org.admin.security.service;

import lombok.RequiredArgsConstructor;
import org.admin.domain.Admin;
import org.admin.repository.AdminDao;
import org.admin.security.dto.AdminAuthDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminUserDetailService implements UserDetailsService {
    private static final Log log = LogFactory.getLog(AdminUserDetailService.class);

    private final AdminDao adminDao;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("AdminUserDetailService loadUserByUsername " + username);
        Optional<Admin> result = adminDao.findById(username);
        if (result.isPresent()) {
           throw new UsernameNotFoundException("Check id");
        }
        Admin admin = result.get();
        AdminAuthDto adminAuthDto = new AdminAuthDto(admin.getId(), admin.getPassword())

        return null;
    }
}
