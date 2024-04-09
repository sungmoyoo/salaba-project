package org.admin.controller;

import lombok.RequiredArgsConstructor;
import org.admin.service.QnaService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class QnaController {
    private final QnaService qnaService;
    private static final Log log = LogFactory.getLog(QnaController.class);

    @GetMapping("qna/list")
    public String qnaList(HttpSession session,
                          Model model) {
        if (session.getAttribute("loginUser") == null) {
            return "redirect:/";
        }
        log.debug(qnaService.getAllQ());
        model.addAttribute("qnaList", qnaService.getAllQ());
        model.addAttribute("menuName", "1:1 문의 답변");
    return "qna/list";
    }

    @GetMapping("qna/detail")
    public String qnaDetail(HttpSession session,
                            @RequestParam("qno") int qnaNo,
                            Model model) {
        if (session.getAttribute("loginUser") == null) {
            return "redirect:/";
        }
        model.addAttribute("qna", qnaService.getBy());
    }
}
