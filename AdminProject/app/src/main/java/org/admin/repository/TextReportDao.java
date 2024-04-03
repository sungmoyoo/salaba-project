package org.admin.repository;

import org.admin.domain.Report;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TextReportDao {
    public List<Report> findAllBy(char type);

    public Report findBy(char type, int no);

}
