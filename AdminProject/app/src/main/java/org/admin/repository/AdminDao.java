package org.admin.repository;

import org.admin.domain.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AdminDao {
    public Admin findByIdAndPassword(@Param("id") String id,
                                     @Param("password") String password);

    public void add(Admin admin);

}
