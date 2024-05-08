package org.admin.repository;

import org.admin.domain.chart.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChartDao {
    public List<BoardStatistic> findBoardCountInMonth();

    public List<MemberStatistic> findJoinCountInMonth();
    public List<MemberGradeStatistic> findUserCountByGrade();
    public List<RentalStatistic> findRentalCountByRegion();

    public UnProcessedWorks findUnprocessedWorks();
}
