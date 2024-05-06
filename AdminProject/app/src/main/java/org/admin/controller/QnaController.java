package org.admin.controller;

import lombok.RequiredArgsConstructor;
import org.admin.domain.Qna;
import org.admin.service.QnaService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
@RequestMapping("/qna")
public class QnaController {
    private final QnaService qnaService;
    private static final Log log = LogFactory.getLog(QnaController.class);

    @GetMapping("/list")
    public RestResult qnaList() {
        return RestResult.builder()
                .status(RestResult.SUCCESS)
                .data(qnaService.getAllQ())
                .build();
    }

    @GetMapping("/view/{qnaNo}")
    public RestResult qnaView(@PathVariable int qnaNo) {
        return RestResult.builder()
                .status(RestResult.SUCCESS)
                .data(qnaService.getBy(qnaNo))
                .build();
    }

    @PostMapping("/update")
    public RestResult addAnswer(@RequestBody Qna qna) {
        qnaService.addAnswer(qna);
        return RestResult.builder()
                .status(RestResult.SUCCESS)
                .build();
    }
}
