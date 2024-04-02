package org.admin.service.impl;

import lombok.RequiredArgsConstructor;
import org.admin.domain.Admin;
import org.admin.repository.AdminDao;
import org.admin.service.AdminService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DefaultAdminService implements AdminService {

    private final AdminDao adminDao;
    @Override
    public void add(Admin admin) {
        adminDao.add(admin);
    }

    @Override
    public Admin login(String id, String password) {
       return adminDao.findByIdAndPassword(id, password);
    }
}
