package org.admin.service.impl;

import lombok.RequiredArgsConstructor;
import org.admin.domain.Report;
import org.admin.repository.RentalReportDao;
import org.admin.service.RentalReportService;
import org.admin.util.Translator;
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
            report.setStateStr(Translator.dealState.get(report.getState()));
        }
        return reports;
    }

    @Override
    public Report get(int rentalNo, int memberNo) {
        Report report = rentalReportDao.findBy(rentalNo, memberNo);
        report.setStateStr(Translator.dealState.get(report.getState()));
        return report;
    }
}
