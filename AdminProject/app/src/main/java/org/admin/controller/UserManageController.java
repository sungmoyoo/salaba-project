package org.admin.controller;

import lombok.RequiredArgsConstructor;
import org.admin.domain.Member;
import org.admin.service.MemberService;
import org.admin.service.RentalReportService;
import org.admin.service.TextReportService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserManageController {

    private static final Log log = LogFactory.getLog(ReportManageController.class);
    private final MemberService memberService;
    @GetMapping("user/list")
    public String userList(@RequestParam("menu") int menu,
                             HttpSession session,
                             Model model) {
        if (session.getAttribute("loginUser") == null) {
            return "redirect:/";
        }
        switch (menu) {
            case 1:
                List<Member> memberList = memberService.getAll();
                log.debug(memberList);
                model.addAttribute("memberList", memberList);
                model.addAttribute("menuName", "회원 목록");
                model.addAttribute("menu", menu);
                break;
            case 2:
                List<Member> memberList2 = memberService.getAllHosts();
                log.debug(memberList2);
                model.addAttribute("memberList", memberList2);
                model.addAttribute("menuName", "호스트 목록");
                model.addAttribute("menu", menu);
                break;
        }
        return "member/list";
    }


    @GetMapping("user/detail")
    public String userDetail(@RequestParam("mno") int memberNo,
                             @RequestParam("menu") int menu,
                             HttpSession session,
                             Model model) {
        if (session.getAttribute("loginUser") == null) {
            return "redirect:/";
        }
        switch (menu) {
            case 1:
                log.debug(memberService.getMemberBy(memberNo));
                model.addAttribute("member", memberService.getMemberBy(memberNo));
                model.addAttribute("menuName", "회원 상세");
                model.addAttribute("menu", menu);
                break;
            case 2:
                Member host = memberService.getHostBy(memberNo);
                log.debug("abcdef" + host);
                model.addAttribute("member", host);
                model.addAttribute("menuName", "호스트 상세");
                model.addAttribute("menu", menu);
                break;
        }
        return "member/detail";
    }

    @PostMapping("user/list/search")
    public String searchUser(@RequestParam("keyword") String keyword,
                             @RequestParam("filter") String filter,
                             @RequestParam("menu") int menu,
                             HttpSession session,
                             Model model) {
        if (session.getAttribute("loginUser") == null) {
            return "redirect:/";
        }
        System.out.println(keyword);
        System.out.println(filter);
        System.out.println(menu);
        //일반 회원 목룍
        if (menu == 1) {
            model.addAttribute("menu", menu);
            model.addAttribute("menuName", "회원 목록");
            if (filter.equals("0")) {
                // 이름으로 검색
                List<Member> memberList = memberService.getMemberByName(keyword);
                log.debug(memberList);
                model.addAttribute("memberList", memberList);
            } else {
                // 이메일로 검색
                List<Member> memberList = memberService.getMemberByEmail(keyword);
                log.debug(memberList);
                model.addAttribute("memberList", memberList);

            }
        // 호스트 목록
        } else {
            model.addAttribute("menu", menu);
            model.addAttribute("menuName", "호스트 목록");
            if (filter.equals("0")) {
                // 이름으로 검색
                List<Member> memberList = memberService.getHostByName(keyword);
                log.debug(memberList);
                model.addAttribute("memberList", memberList);
            } else {
                // 이메일로 검색
                List<Member> memberList = memberService.getHostByEmail(keyword);
                log.debug(memberList);
                model.addAttribute("memberList", memberList);
            }
            // 호스트 목록
        }
        return "member/list";
    }

    @PostMapping("user/update")
    public String updateGrade(@RequestParam("grade") String grade,
                              @RequestParam("memberNo") int memberNo,
                              HttpSession session) {
        if (session.getAttribute("loginUser") == null) {
            return "redirect:/";
        }
        memberService.updateGrade(grade, memberNo);
        return "redirect:list?menu=1";
    }
}
