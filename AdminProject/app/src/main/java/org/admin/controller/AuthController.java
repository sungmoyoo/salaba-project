package org.admin.controller;

import lombok.RequiredArgsConstructor;
import org.admin.domain.Admin;
import org.admin.service.AdminService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private static final Log log = LogFactory.getLog(AuthController.class);
    private final AdminService adminService;
    
    @GetMapping("/")
    public String login() {
        return "auth/form";
    }

    @PostMapping("login")
    public String login(@RequestParam("id") String id,
                        @RequestParam("password") String password,
                        HttpSession session,
                        Model model) {
        Admin loginUser = adminService.login(id, password);
        log.debug("login");
        if (loginUser != null) {
            session.setAttribute("loginUser", loginUser);
            model.addAttribute("no", 1);
            return "redirect:manage/rental_report";
        } else {
            return "redirect:/";
        }


    }
}
