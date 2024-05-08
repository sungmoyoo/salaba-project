package org.admin.service;

import org.admin.domain.chart.*;

import java.util.List;

public interface ChartService {
    public List<BoardStatistic> getBoardCountInMonth();

    public List<MemberStatistic> getJoinCountInMonth();
    public List<MemberGradeStatistic> getUserCountByGrade();
    public List<RentalStatistic> getRentalCountByRegion();
    public UnProcessedWorks getUnprocessedWorks();
}
