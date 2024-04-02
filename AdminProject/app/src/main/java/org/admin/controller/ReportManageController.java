package org.admin.controller;

import lombok.RequiredArgsConstructor;
import org.admin.domain.RentalReport;
import org.admin.service.RentalReportService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ReportManageController {
    private static final Log log = LogFactory.getLog(ReportManageController.class);
    private final RentalReportService rentalReportService;
    @GetMapping("manage/list")
    public String rentalRepo(@RequestParam("no") int no,
                             HttpSession session,
                             Model model) {
        if (session.getAttribute("loginUser") == null) {
            return "auth/login";
        }
        List<RentalReport> list = rentalReportService.getAll();
        model.addAttribute("rentalReportList", list);
        model.addAttribute("name", "숙소 신고 내역");
        model.addAttribute("no");
        return "manage/list";
    }

}
