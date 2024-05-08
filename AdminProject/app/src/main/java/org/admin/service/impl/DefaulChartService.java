package org.admin.service.impl;

import lombok.RequiredArgsConstructor;
import org.admin.domain.chart.*;
import org.admin.repository.ChartDao;
import org.admin.service.ChartService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaulChartService implements ChartService {
    private final ChartDao chartDao;
    @Override
    public List<BoardStatistic> getBoardCountInMonth() {
        return chartDao.findBoardCountInMonth();
    }

    @Override
    public List<MemberStatistic> getJoinCountInMonth() {
        return chartDao.findJoinCountInMonth();
    }

    @Override
    public List<MemberGradeStatistic> getUserCountByGrade() {
        return chartDao.findUserCountByGrade();
    }

    @Override
    public List<RentalStatistic> getRentalCountByRegion() {
        return chartDao.findRentalCountByRegion();
    }

    @Override
    public UnProcessedWorks getUnprocessedWorks() {
        return chartDao.findUnprocessedWorks();
    }
}
