package org.admin.service;

import org.admin.domain.Member;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MemberService {
    List<Member> getAll();

    List<Member> getAllHosts();

    Member getMemberBy(int memberNo);

    Member getHostBy(int memberNo);

    int updateWarningCountBy(int reportNo);
    int updateWarningCount(int writerNo);

    List<Member> getMemberByName(String keyword);
    List<Member> getMemberByEmail(String keyword);

    List<Member> getHostByName(String keyword);
    List<Member> getHostByEmail(String keyword);

    int updateGrade(String grade, int memberNo);
}
