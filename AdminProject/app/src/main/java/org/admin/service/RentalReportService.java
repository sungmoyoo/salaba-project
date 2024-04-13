package org.admin.service;

import org.admin.domain.Report;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RentalReportService {
    public List<Report> getAll();
    public Report get(int rentalNo, int memberNo);

    public int updateState(int rentalNo, int memberNo);
}
