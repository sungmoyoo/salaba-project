package org.admin.repository;

import org.admin.domain.RentalReport;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface RentalReportDao {
    public List<RentalReport> findAll();

    public RentalReport findBy(int rentalNo, int memberNo);

}
