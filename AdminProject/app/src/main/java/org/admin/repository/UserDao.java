package org.admin.repository;

import org.admin.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserDao {
    public User findByEmailAndPassword(@Param("email") String email,
                                    @Param("password") String password);

    public void add(User user);

    User findByEmail(@Param("email") String username);

    void addRole(@Param("userNo") long userNo);
}
