package org.admin.service.impl;

import lombok.RequiredArgsConstructor;
import org.admin.domain.Report;
import org.admin.repository.RentalReportDao;
import org.admin.service.RentalReportService;
import org.admin.util.Translator;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultRentalReportService implements RentalReportService {
    private final RentalReportDao rentalReportDao;
    @Override
    public List<Report> getAll() {
        List<Report> reports = rentalReportDao.findAll();
        for (Report report : reports) {
            //하나의 객체에 숙소신고와 게시글,댓글,답글을 다루기 때문에 숙소 신고에대해 type을 소스에서 지정해준다.
            report.setTargetType("3");
            report.setStateStr(Translator.dealState.get(report.getState()));
        }
        return reports;
    }

    @Override
    public Report get(int rentalNo, int memberNo) {
        Report report = rentalReportDao.findBy(rentalNo, memberNo);
        report.setTargetType("3");
        report.setStateStr(Translator.dealState.get(report.getState()));
        return report;
    }

    @Override
    public int updateState(int rentalNo, int memberNo) {
        return rentalReportDao.updateState(rentalNo, memberNo);
    }
}
