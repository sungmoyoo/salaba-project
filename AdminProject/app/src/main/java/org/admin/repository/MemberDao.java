package org.admin.repository;

import org.admin.domain.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MemberDao {
    List<Member> findAll();

    List<Member> findAllHosts();

    Member findMemberBy(@Param("memberNo") long memberNo);

    Member findHostBy(@Param("memberNo") long memberNo);

    int updateWarningCountBy(@Param("reportNo") int reportNo);

    int updateWarningCount(@Param("memberNo") long memberNo);

    List<Member> findMemberByName(@Param("keyword") String keyword);
    List<Member> findMemberByEmail(@Param("keyword") String keyword);

    List<Member> findHostByName(@Param("keyword") String keyword);
    List<Member> findHostByEmail(@Param("keyword") String keyword);

    int updateGrade(@Param("gradeNo") int gradeNo,
                    @Param("memberNo") long memberNo);

    public Member findByEmailAndPassword(@Param("email") String email,
                                       @Param("password") String password);

    public void add(Member member);

    Member findByEmail(@Param("email") String email);
}
