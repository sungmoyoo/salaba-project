package org.admin.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EnterController {
    Log log = LogFactory.getLog(HomeController.class);
    
    @GetMapping("/admin")
    public String enter() {
        log.debug("관리자 페이지 진입");
        return "/admin";
    }
}
