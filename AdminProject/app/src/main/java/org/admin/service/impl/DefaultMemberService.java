package org.admin.service.impl;

import lombok.RequiredArgsConstructor;
import org.admin.domain.Member;
import org.admin.repository.MemberDao;
import org.admin.service.MemberService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class DefaultMemberService implements MemberService {

    private final MemberDao memberDao;
    @Override
    public List<Member> getAll() {
        return memberDao.findAll();
    }

    @Override
    public List<Member> getAllHosts() {
        return memberDao.findAllHosts();
    }
}
