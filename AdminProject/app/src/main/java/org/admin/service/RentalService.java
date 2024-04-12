package org.admin.service;

import org.admin.domain.Rental;

import java.util.List;

public interface RentalService {
    //등록된 숙소 목록
    List<Rental> getAll();

    //등록 신청한 숙소 목록
    List<Rental> getAppliedRentals();

    Rental getBy(int rentalNo);


}
