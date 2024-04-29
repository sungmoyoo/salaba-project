package org.admin.service.impl;

import lombok.RequiredArgsConstructor;
import org.admin.domain.User;
import org.admin.repository.UserDao;
import org.admin.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class DefaultUserService implements UserService {

    private final UserDao userDao;
    @Override
    @Transactional
    public void add(User user) {
        userDao.add(user);
        userDao.addRole(user.getUserNo());
    }

    @Override
    public User login(String email, String password) {
       return userDao.findByEmailAndPassword(email, password);
    }

    @Override
    public User get(String email) {
        return userDao.findByEmail(email);
    }
}
