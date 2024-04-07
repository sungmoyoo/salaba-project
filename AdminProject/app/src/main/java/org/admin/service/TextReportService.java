package org.admin.service;

import org.admin.domain.Report;

import java.util.List;

public interface TextReportService {
    public List<Report> getAllBy(char type);

    public Report getBy(char type, int no, int memberNo);
}
