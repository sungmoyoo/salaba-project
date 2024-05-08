package org.admin.controller;

import lombok.RequiredArgsConstructor;
import org.admin.domain.Member;
import org.admin.service.MemberService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberManageController {

    private static final Log log = LogFactory.getLog(ReportManageController.class);
    private final MemberService memberService;
    @GetMapping("/list/{menu}")
    public RestResult memberList(@PathVariable int menu) {
        switch (menu) {
            case 1:

                return RestResult.builder()
                        .status(RestResult.SUCCESS)
                        .data(memberService.getAll())
                        .build();
            case 2:
                return RestResult.builder()
                        .status(RestResult.SUCCESS)
                        .data(memberService.getAllHosts())
                        .build();
        }

        return RestResult.builder()
                .status(RestResult.FAILURE)
                .error("Bad Request")
                .build();
    }


    @GetMapping("/view/{memberNo}/{menu}")
    public RestResult memberView(@PathVariable int memberNo,
                             @PathVariable int menu) {

        switch (menu) {
            case 1:
                return RestResult.builder()
                        .status(RestResult.SUCCESS)
                        .data(memberService.getMemberBy(memberNo))
                        .build();
            case 2:
                return RestResult.builder()
                        .status(RestResult.SUCCESS)
                        .data(memberService.getHostBy(memberNo))
                        .build();
        }

        return RestResult.builder()
                .status(RestResult.FAILURE)
                .error("Bad Request")
                .build();
    }

    @GetMapping("/search/{keyword}/{filter}/{menu}")
    public RestResult searchMember(@PathVariable String keyword,
                             @PathVariable String filter,
                             @PathVariable int menu) {

        //일반 회원 목룍
        if (menu == 1) {

            if (filter.equals("0")) {
                //이름으로 검색
                return RestResult.builder()
                        .status(RestResult.SUCCESS)
                        .data(memberService.getMemberByName(keyword))
                        .build();
            } else {
                return RestResult.builder()
                        .status(RestResult.SUCCESS)
                        .data(memberService.getMemberByEmail(keyword))
                        .build();
            }
        // 호스트 목록
        } else {
            if (filter.equals("0")) {
                return RestResult.builder()
                        .status(RestResult.SUCCESS)
                        .data(memberService.getHostByName(keyword))
                        .build();
            } else {
                // 이메일로 검색
                return RestResult.builder()
                        .status(RestResult.SUCCESS)
                        .data(memberService.getHostByEmail(keyword))
                        .build();
            }
        }
    }

    @PutMapping("/update")
    public RestResult updateGrade(@RequestBody Member member) {

        return RestResult.builder()
                .status(RestResult.SUCCESS)
                .data(memberService.updateGrade(member.getGradeNo(), member.getMemberNo()))
                .build();
    }
}
