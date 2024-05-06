package org.admin.repository;

import org.admin.domain.chart.BoardStatistic;
import org.admin.domain.chart.MemberGradeStatistic;
import org.admin.domain.chart.MemberStatistic;
import org.admin.domain.chart.RentalStatistic;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChartDao {
    public List<BoardStatistic> findBoardCountInMonth();

    public List<MemberStatistic> findJoinCountInMonth();
    public List<MemberGradeStatistic> findUserCountByGrade();
    public List<RentalStatistic> findRentalCountByRegion();
}
