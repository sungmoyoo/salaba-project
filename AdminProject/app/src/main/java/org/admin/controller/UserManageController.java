package org.admin.controller;

import lombok.RequiredArgsConstructor;
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
                break;
            case 2:
                log.debug(memberService.getAllHosts());
                model.addAttribute("memberList", memberService.getAllHosts());
                model.addAttribute("menuName", "호스트 목록");
                break;
            case 3:
                model.addAttribute("menuName", "호스트 등록심사");
                break;
        }
        return "member/list";
    }
}
