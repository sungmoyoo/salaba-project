package org.admin.repository;

import org.admin.domain.Rental;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RentalDao {
    List<Rental> findAll();
    List<Rental> findApplied();

    Rental findBy(@Param("rentalNo") int rentalNo);
}
