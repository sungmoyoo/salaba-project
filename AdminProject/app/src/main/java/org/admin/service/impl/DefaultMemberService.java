package org.admin.service.impl;

import lombok.RequiredArgsConstructor;
import org.admin.domain.Member;
import org.admin.repository.MemberDao;
import org.admin.service.MemberService;
import org.admin.util.Translator;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class DefaultMemberService implements MemberService {

    private final MemberDao memberDao;
    @Override
    public List<Member> getAll() {
        List<Member> members = memberDao.findAll();
        for (Member member : members) {
            member.setStateStr(Translator.memberState.get(member.getState()));
        }
        return members;
    }

    @Override
    public List<Member> getAllHosts() {
        List<Member> members = memberDao.findAllHosts();
        for (Member member : members) {
            member.setStateStr(Translator.memberState.get(member.getState()));
        }
        return members;
    }
}
