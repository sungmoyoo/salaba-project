package org.admin.repository;

import org.admin.domain.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MemberDao {
    List<Member> findAll();

    List<Member> findAllHosts();

    Member findMemberBy(@Param("memberNo") int memberNo);

    Member findHostBy(@Param("memberNo") int memberNo);

    int updateWarningCountBy(@Param("reportNo") int reportNo);

    int updateWarningCount(@Param("memberNo") int memberNo);
}
