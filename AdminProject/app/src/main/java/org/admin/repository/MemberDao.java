package org.admin.repository;

import org.admin.domain.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberDao {
    List<Member> findAll();

    List<Member> findAllHosts();
}
