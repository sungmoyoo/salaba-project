package org.admin.service;

import org.admin.domain.Report;

import java.util.List;

public interface TextReportService {
    public List<Report> getAllBy(String type);

    public Report getBy(String type, int targetNo, int memberNo);

    public int updateState(int reportNo);

    public int updateBoardState(Report report, String boardState);
}
