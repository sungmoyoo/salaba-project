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

    List<Member> findMemberByName(@Param("keyword") String keyword);
    List<Member> findMemberByEmail(@Param("keyword") String keyword);

    List<Member> findHostByName(@Param("keyword") String keyword);
    List<Member> findHostByEmail(@Param("keyword") String keyword);

    int updateGrade(@Param("grade") String grade,
                    @Param("memberNo") int memberNo);
}
