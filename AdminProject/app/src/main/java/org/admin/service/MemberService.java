package org.admin.service;

import org.admin.domain.Member;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MemberService {
    List<Member> getAll();

    List<Member> getAllHosts();

    Member getMemberBy(int memberNo);

    Member getHostBy(int memberNo);
}
