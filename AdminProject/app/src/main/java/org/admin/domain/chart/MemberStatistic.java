package org.admin.domain.chart;

import lombok.Data;

import java.sql.Date;

@Data
public class MemberStatistic {
    private int joinYear;
    private int joinMonth;
    private int memberCount;
}
