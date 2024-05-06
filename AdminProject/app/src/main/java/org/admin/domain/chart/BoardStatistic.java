package org.admin.domain.chart;

import lombok.Data;

import java.sql.Date;

@Data
public class BoardStatistic {
    private Date createdDate;
    private int boardCount;
}
