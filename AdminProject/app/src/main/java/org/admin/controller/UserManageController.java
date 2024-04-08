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
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class UserManageController {

    private static final Log log = LogFactory.getLog(ReportManageController.class);
    private final MemberService memberService;
    @GetMapping("user/list")
    public String userManage(@RequestParam("menu") int menu,
                             HttpSession session,
                             Model model) {
        if (session.getAttribute("loginUser") == null) {
            return "redirect:/";
        }
        switch (menu) {
            case 1:
                log.debug(memberService.getAll());
                model.addAttribute("memberList", memberService.getAll());
                model.addAttribute("menuName", "회원 목록");
                model.addAttribute("menu", menu);
                break;
            case 2:
                log.debug(memberService.getAllHosts());
                model.addAttribute("memberList", memberService.getAllHosts());
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
        System.out.println("abcdef" + memberNo);
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
}
