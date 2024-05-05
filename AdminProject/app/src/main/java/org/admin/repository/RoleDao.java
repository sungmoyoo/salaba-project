package org.admin.repository;

import org.admin.domain.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoleDao {
    Role findRoleName(@Param("roleName") String roleName);
    void addRole(@Param("memberNo") long memberNo, @Param("roleNo") int roleNo);

    List<Role> findRoles(@Param("memberNo") long memberNo);

}
