package org.admin.repository;

import org.admin.domain.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface AdminDao {
    public Admin findByIdAndPassword(@Param("id") String id,
                                     @Param("password") String password);

    public void add(Admin admin);

    Optional<Admin> findById(@Param("id") String username);
}
