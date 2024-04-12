package org.admin.repository;

import org.admin.domain.Report;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TextReportDao {
    public List<Report> findAllBy(char type);

    public Report findBy(@Param("type") char type,
                         @Param("no") int no,
                         @Param("memberNo") int memberNo);

    public int updateState(Report report);

}
