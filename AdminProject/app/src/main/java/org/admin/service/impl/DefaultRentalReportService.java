package org.admin.service.impl;

import lombok.RequiredArgsConstructor;
import org.admin.domain.RentalReport;
import org.admin.repository.RentalReportDao;
import org.admin.service.RentalReportService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultRentalReportService implements RentalReportService {
    private final RentalReportDao rentalReportDao;
    @Override
    public List<RentalReport> getAll() {
        return rentalReportDao.findAll();
    }

    @Override
    public RentalReport get(int rentalNo, int memberNo) {
        return rentalReportDao.findBy(rentalNo, memberNo);
    }
}
