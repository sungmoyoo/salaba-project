package org.admin.service;

import org.admin.domain.Admin;
import org.springframework.stereotype.Service;

public interface AdminService {
    void add(Admin admin);

    Admin login(String id, String password);


}
