package org.admin.service;

import org.admin.domain.Report;

import java.util.List;

public interface RentalReportService {
    public List<Report> getAll();
    public Report get(int rentalNo, int memberNo);
}
