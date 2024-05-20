package org.admin.controller;

import lombok.RequiredArgsConstructor;
import org.admin.service.MemberService;
import org.admin.service.RentalService;
import org.admin.util.ReportType;
import org.admin.domain.Report;
import org.admin.service.RentalReportService;
import org.admin.service.TextReportService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/report")
public class ReportManageController {
    private static final Log log = LogFactory.getLog(ReportManageController.class);
    private final RentalReportService rentalReportService;
    private final TextReportService textReportService;
    private final MemberService memberService;
    private final RentalService rentalService;

    @GetMapping("/list/{menu}")
    public RestResult reportList(@PathVariable("menu") int menu) {
        log.debug("관리자 - report/list");
        switch (menu) {
            case 1:
                return RestResult.builder()
                        .status(RestResult.SUCCESS)
                        .data(rentalReportService.getAll())
                        .build();
            case 2:
                return RestResult.builder()
                        .status(RestResult.SUCCESS)
                        .data(textReportService.getAllBy(ReportType.BOARD.getValue()))
                        .build();
            case 3:
                // 댓글과 답글리스트를 합쳐서 model에 담는다.
                List<Report> comments = textReportService.getAllBy(ReportType.COMMENT.getValue());
                List<Report> replies = textReportService.getAllBy(ReportType.REPLY.getValue());
                comments.addAll(replies);
                return RestResult.builder()
                        .status(RestResult.SUCCESS)
                        .data(comments)
                        .build();
        }
        return RestResult.builder()
                .status(RestResult.FAILURE)
                .error("No Content")
                .build();
    }


    @GetMapping("/view/{targetNo}/{type}/{mno}")
    public RestResult textReportView(@PathVariable int targetNo,
                                   @PathVariable String type,
                                   @PathVariable int mno) {

        if (type.equals("0")) {
            return RestResult.builder()
                    .status(RestResult.SUCCESS)
                    .data(textReportService.getBy(type, targetNo, mno))
                    .build();
        }
        if (type.equals("1") || type.equals("2")) {
            return RestResult.builder()
                    .status(RestResult.SUCCESS)
                    .data(textReportService.getBy(type, targetNo, mno))
                    .build();
        }
        return RestResult.builder()
                .status(RestResult.FAILURE)
                .error("Bad Request")
                .build();
    }

    @GetMapping("/view/{rentalNo}/{memberNo}")
    public RestResult rentalReportView(@PathVariable int rentalNo,
                                   @PathVariable int memberNo) {

        return RestResult.builder()
                .status(RestResult.SUCCESS)
                .data(rentalReportService.get(rentalNo, memberNo))
                .build();
    }

    @Transactional
    @PutMapping("/update/{selection1}/{selection2}/{writerNo}")
    public RestResult updateReport(@PathVariable String selection1, // 0-무시, 1-영구정지, 2-경고조치
                                   @PathVariable String selection2,
                                   @PathVariable int writerNo,
                                   @RequestBody Report report) {
        //게시물, 댓글, 대댓글 신고
        if (selection2.equals("0")) {
            textReportService.updateState(report.getReportNo());
            memberService.updateWarningCountBy(report.getReportNo());
            textReportService.updateBoardState(report, selection1);
            return RestResult.builder()
                    .status(RestResult.SUCCESS)
                    .build();
        //숙소 신고
        } else {
            rentalReportService.updateState(report.getTargetNo(), writerNo);
            memberService.updateWarningCount(writerNo);
            rentalService.updateState(report.getTargetNo(), selection2);
            return RestResult.builder()
                    .status(RestResult.SUCCESS)
                    .build();
        }
    }


}
