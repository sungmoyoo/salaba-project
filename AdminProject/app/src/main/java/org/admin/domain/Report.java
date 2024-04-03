package org.admin.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Report {
    private int reportNo;
    private int memberNo;
    private int category;
    private String categoryName;
    private String content;
    private char state;
    private Date reportDate;
    private int targetNo;
    private char targetType;

    private List<ReportFile> reportFiles;
}
