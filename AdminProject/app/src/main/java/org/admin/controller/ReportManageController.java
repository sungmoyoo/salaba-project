package org.admin.controller;

import lombok.RequiredArgsConstructor;
import org.admin.constant.ReportType;
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
public class ReportManageController {
    private static final Log log = LogFactory.getLog(ReportManageController.class);
    private final RentalReportService rentalReportService;
    private final TextReportService textReportService;
    @GetMapping("manage/list")
    public String rentalRepo(@RequestParam("menu") int menu,
                             HttpSession session,
                             Model model) {
        log.debug("관리자 - manage/list");
        if (session.getAttribute("loginUser") == null) {
            return "auth/login";
        }
        switch (menu) {
            case 1:
                model.addAttribute("list", rentalReportService.getAll());
                model.addAttribute("menuName", "숙소 신고내역");
                break;
            case 2:
                model.addAttribute("list", textReportService.getAllBy(ReportType.BOARD.getValue()));
                model.addAttribute("menuName", "게시글 신고내역");
                break;
            case 3:
                model.addAttribute("commentList", textReportService.getAllBy(ReportType.COMMENT.getValue()));
                model.addAttribute("menuName", "댓글 신고내역");
                break;
//            case 4:
//                model.addAttribute("name", "1:1 문의내역");
//                break;
//            case 5:
//                model.addAttribute("name", "일반 회원목록");
//                break;
//            case 6:
//                model.addAttribute("name", "호스트 목록");
//                break;
//            case 7:
//                model.addAttribute("name", "호스트 등록심사");
//                break;
        }
        return "manage/list";
    }

}
