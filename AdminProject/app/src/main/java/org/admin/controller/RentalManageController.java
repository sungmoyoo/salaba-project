package org.admin.controller;

import lombok.RequiredArgsConstructor;
import org.admin.service.RentalService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class RentalManageController {
    private final RentalService rentalService;
    private static final Log log = LogFactory.getLog(RentalManageController.class);
    @GetMapping("rental/list")
    public String rentalList(@RequestParam("menu") int menu,
                             HttpSession session,
                             Model model) {
        if (session.getAttribute("loginUser") == null) {
            return "redirect:/";
        }

        switch (menu) {
            case 1:
                log.debug(rentalService.getAll());
                model.addAttribute("rentalList", rentalService.getAll());
                model.addAttribute("menuName", "등록된 숙소 목록");
                break;
            case 2:
                log.debug(rentalService.getAppliedRentals());
                model.addAttribute("rentalList", rentalService.getAppliedRentals());
                model.addAttribute("menuName", "숙소 등록 심사");
                break;
        }
        return "rental/list";

    }
}
