package org.admin.service;

import org.admin.domain.User;

public interface UserService {
    void add(User user);

    User login(String email, String password);

    User get(String email);
}
