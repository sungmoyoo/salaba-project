package org.admin.service;

import org.admin.domain.Member;

import java.util.List;

public interface MemberService {
    List<Member> getAll();

    List<Member> getAllHosts();
}
