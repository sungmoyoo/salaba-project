package org.admin.service;

import org.admin.domain.RentalReport;

import java.util.List;

public interface RentalReportService {
    public List<RentalReport> getAll();
    public RentalReport get(int rentalNo, int memberNo);
}
