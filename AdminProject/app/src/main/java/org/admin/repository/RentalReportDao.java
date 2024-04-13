package org.admin.repository;

import org.admin.domain.Report;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RentalReportDao {
    public List<Report> findAll();

    public Report findBy(@Param("rentalNo") int rentalNo,
                         @Param("memberNo") int memberNo);

    public int updateState(@Param("rentalNo") int rentalNo,
                         @Param("memberNo") int memberNo);

}
