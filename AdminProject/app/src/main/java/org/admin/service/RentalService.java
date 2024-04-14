package org.admin.service;

import org.admin.domain.Rental;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RentalService {
    //등록된 숙소 목록
    List<Rental> getAll();

    //등록 신청한 숙소 목록
    List<Rental> getAppliedRentals();

    Rental getBy(int rentalNo);

    int updateState(int rentalNo, String rentalState);

    List<Rental> getAllByName(String keyword);
    List<Rental> getAllByHostName(String keyword);



}
