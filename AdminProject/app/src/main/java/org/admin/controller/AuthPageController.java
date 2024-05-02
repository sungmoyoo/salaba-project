package org.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AuthPageController {

    @GetMapping("/")
    public String toLoginForm() {
        return "redirect:/auth/form";
    }

    @GetMapping("/auth/form")
    public void loginForm() {

    }
}
