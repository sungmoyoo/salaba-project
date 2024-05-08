package org.admin.controller;

import lombok.RequiredArgsConstructor;
import org.admin.service.ChartService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chart")
public class ChartController {

    private final ChartService chartService;
    @GetMapping("/boardCount")
    public RestResult boardCountInMonth() {
        return RestResult.builder()
                .status(RestResult.SUCCESS)
                .data(chartService.getBoardCountInMonth())
                .build();

    }

    @GetMapping("/joinCount")
    public RestResult joinCountInMonth() {
        return RestResult.builder()
                .status(RestResult.SUCCESS)
                .data(chartService.getJoinCountInMonth())
                .build();
    }

    @GetMapping("/gradeCount")
    public RestResult userCountByGrade() {
        return RestResult.builder()
                .status(RestResult.SUCCESS)
                .data(chartService.getUserCountByGrade())
                .build();
    }

    @GetMapping("/rentalCount")
    public RestResult rentalCountByRegion() {
        return RestResult.builder()
                .status(RestResult.SUCCESS)
                .data(chartService.getRentalCountByRegion())
                .build();
    }

    @GetMapping("/unprocessed")
    public RestResult unprocessedWorks() {
        return RestResult.builder()
                .status(RestResult.SUCCESS)
                .data(chartService.getUnprocessedWorks())
                .build();
    }
}
