package org.admin.controller;

import lombok.RequiredArgsConstructor;
import org.admin.domain.Photo;
import org.admin.domain.Rental;
import org.admin.service.RentalService;
import org.admin.service.StorageService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class RentalManageController {
    private final RentalService rentalService;
    private final StorageService storageService;
    private final String rentalImgPath = "static/img/rental";
    @Value("${ncp.ss.bucketname}")
    private String bucketName;
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
                model.addAttribute("menu", menu);
                break;
            case 2:
                log.debug(rentalService.getAppliedRentals());
                model.addAttribute("rentalList", rentalService.getAppliedRentals());
                model.addAttribute("menuName", "숙소 등록 심사");
                model.addAttribute("menu", menu);
                break;
        }
        return "rental/list";
    }

    @GetMapping("rental/detail")
    public String rentalDetail(@RequestParam("menu") int menu,
                               @RequestParam("rno") int rentalNo,
                               HttpSession session,
                               Model model) {
        if (session.getAttribute("loginUser") == null) {
            return "redirect:/";
        }

        Rental rental = rentalService.getBy(rentalNo);
        // 이미지가 있는경로의 파일들의 이름을 전부 가져온다.
//        List<String> rentalImageList = Arrays.stream(new File(rentalImgPath).list()).toList();
        // db에서 가져온 uuid_name과 이미지디렉토리를 비교하여 해당 이름의 파일이 없으면 다운로드 한다.
//        for (Photo photo : rental.getPhotos()) {
//            String uuid = photo.getUuidName();
//            if (!rentalImageList.contains(uuid)) {
//                try {
//                    storageService.download(bucketName, uuid, rentalImgPath);
//                } catch (Exception e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        }

        log.debug(rental);
        model.addAttribute("rental", rental);
        switch (menu) {
            case 1:
                model.addAttribute("menuName", "숙소 상세");
                model.addAttribute("menu", menu);
                break;
            case 2:
                model.addAttribute("menuName", "숙소 등록 심사");
                model.addAttribute("menu", menu);
                break;
        }
        return "rental/detail";
    }
}
