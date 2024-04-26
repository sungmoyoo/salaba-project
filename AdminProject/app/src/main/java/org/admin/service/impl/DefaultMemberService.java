package org.admin.service.impl;

import lombok.RequiredArgsConstructor;
import org.admin.domain.Member;
import org.admin.domain.Rental;
import org.admin.repository.MemberDao;
import org.admin.service.MemberService;
import org.admin.util.Translator;
import org.springframework.stereo.Service;

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

    @Override
    public Member getMemberBy(int memberNo) {
        Member member = memberDao.findMemberBy(memberNo);
        member.setStateStr(Translator.memberState.get(member.getState()));
        return member;
    }

    @Override
    public Member getHostBy(int memberNo) {
        Member member = memberDao.findHostBy(memberNo);
        member.setStateStr(Translator.memberState.get(member.getState()));
        for (Rental rental : member.getRentals()) {
            rental.setStateStr(Translator.rentalState.get(rental.getState()));
        }
        return member;
    }

    @Override
    public int updateWarningCount(int writerNo) {
        return memberDao.updateWarningCount(writerNo);
    }

    @Override
    public int updateWarningCountBy(int reportNo) {
        return memberDao.updateWarningCount(reportNo);
    }

    @Override
    public List<Member> getMemberByName(String keyword) {
        List<Member> members = memberDao.findMemberByName(keyword);
        for (Member member : members) {
            member.setStateStr(Translator.memberState.get(member.getState()));
        }
        return members;
    }

    @Override
    public List<Member> getMemberByEmail(String keyword) {
        List<Member> members = memberDao.findMemberByEmail(keyword);
        for (Member member : members) {
            member.setStateStr(Translator.memberState.get(member.getState()));
        }
        return members;
    }

    @Override
    public List<Member> getHostByName(String keyword) {
        List<Member> members = memberDao.findHostByName(keyword);
        for (Member member : members) {
            member.setStateStr(Translator.memberState.get(member.getState()));
        }
        return members;
    }

    @Override
    public List<Member> getHostByEmail(String keyword) {
        List<Member> members = memberDao.findHostByEmail(keyword);
        for (Member member : members) {
            member.setStateStr(Translator.memberState.get(member.getState()));
        }
        return members;
    }

    @Override
    public int updateGrade(String grade, int memberNo) {
        return memberDao.updateGrade(grade, memberNo);
    }
}
