package org.admin.service;

import org.admin.domain.chart.BoardStatistic;
import org.admin.domain.chart.MemberGradeStatistic;
import org.admin.domain.chart.MemberStatistic;
import org.admin.domain.chart.RentalStatistic;

import java.util.List;

public interface ChartService {
    public List<BoardStatistic> getBoardCountInMonth();

    public List<MemberStatistic> getJoinCountInMonth();
    public List<MemberGradeStatistic> getUserCountByGrade();
    public List<RentalStatistic> getRentalCountByRegion();
}
