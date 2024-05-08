package org.admin.service;

import org.admin.domain.Member;

import java.util.List;

public interface MemberService {
    List<Member> getAll();

    List<Member> getAllHosts();

    Member getMemberBy(long memberNo);

    Member getHostBy(long memberNo);

    int updateWarningCountBy(int reportNo);
    int updateWarningCount(int writerNo);

    List<Member> getMemberByName(String keyword);
    List<Member> getMemberByEmail(String keyword);

    List<Member> getHostByName(String keyword);
    List<Member> getHostByEmail(String keyword);

    int updateGrade(int gradeNo, long memberNo);

    void add(Member member);

    Member login(String email, String password);

    Member getByEmail(String email);
}
