package org.admin.controller;

import lombok.RequiredArgsConstructor;
import org.admin.domain.Member;
import org.admin.domain.Photo;
import org.admin.domain.Rental;
import org.admin.service.RentalService;
import org.admin.service.StorageService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rental")
public class RentalManageController {
    private final RentalService rentalService;
    private static final Log log = LogFactory.getLog(RentalManageController.class);

    @GetMapping("/list/{menu}")
    public RestResult rentalList(@PathVariable int menu) {
        switch (menu) {
            case 1:
                return RestResult.builder()
                        .status(RestResult.SUCCESS)
                        .data(rentalService.getAll())
                        .build();
            case 2:
                return RestResult.builder()
                        .status(RestResult.SUCCESS)
                        .data(rentalService.getAppliedRentals())
                        .build();
        }
        
        return RestResult.builder()
                .status(RestResult.FAILURE)
                .data("Bad Request")
                .build();
    }

    @GetMapping("/view/{menu}/{rentalNo}")
    public RestResult rentalView(@PathVariable int menu,
                               @PathVariable int rentalNo) {

        return RestResult.builder()
                .status(RestResult.SUCCESS)
                .data(rentalService.getBy(rentalNo))
                .build();
    }

    @PutMapping("/update")
    public RestResult rentalUpdate(@RequestBody Rental rental) {
        
        rentalService.updateState(rental.getRentalNo(), rental.getState());
        return RestResult.builder()
                .status(RestResult.SUCCESS)
                .build();
    }

    @GetMapping("/search/{keyword}/{filter}")
    public RestResult searchRental(@PathVariable String keyword,
                               @PathVariable String filter) {
        
        if (filter.equals("0")) {
            // 숙소명으로 검색
            return RestResult.builder()
                    .status(RestResult.SUCCESS)
                    .data(rentalService.getAllByName(keyword))
                    .build();
        } else {
            // 호스트명로 검색
            return RestResult.builder()
                    .status(RestResult.SUCCESS)
                    .data(rentalService.getAllByHostName(keyword))
                    .build();

        }
    }
}
