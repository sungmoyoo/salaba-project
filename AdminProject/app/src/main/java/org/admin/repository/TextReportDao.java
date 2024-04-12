package org.admin.repository;

import org.admin.domain.Report;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TextReportDao {
    public List<Report> findAllBy(String type);

    public Report findBy(@Param("type") String type,
                         @Param("no") int no,
                         @Param("memberNo") int memberNo);

    public int updateState(@Param("reportNo") int reportNo);

    public int updateBoardState(@Param("report") Report report, @Param("boardState") String boardState);
}
