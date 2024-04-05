package org.admin.repository;

import org.admin.domain.Rental;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RentalDao {
    List<Rental> findAll();
    List<Rental> findApplied();
}
