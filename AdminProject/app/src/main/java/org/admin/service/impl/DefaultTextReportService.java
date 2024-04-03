package org.admin.service.impl;

import lombok.RequiredArgsConstructor;
import org.admin.domain.Report;
import org.admin.repository.TextReportDao;
import org.admin.service.TextReportService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultTextReportService implements TextReportService {

    private final TextReportDao textReportDao;
    @Override
    public List<Report> getAllBy(char type) {
        return textReportDao.findAllBy(type);
    }

    @Override
    public Report getBy(char type, int no) {
        return textReportDao.findBy(type, no);
    }
}
