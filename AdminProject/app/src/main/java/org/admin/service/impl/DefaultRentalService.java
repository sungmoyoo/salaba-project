package org.admin.service.impl;

import lombok.RequiredArgsConstructor;
import org.admin.domain.Rental;
import org.admin.repository.RentalDao;
import org.admin.service.RentalService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultRentalService implements RentalService {
    private final RentalDao rentalDao;


    @Override
    public List<Rental> getAll() {
        return rentalDao.findAll();
    }

    @Override
    public List<Rental> getAppliedRentals() {
        return rentalDao.findApplied();
    }
}
