package org.admin.service.impl;

import lombok.RequiredArgsConstructor;
import org.admin.domain.Rental;
import org.admin.repository.RentalDao;
import org.admin.service.RentalService;
import org.admin.util.Translator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultRentalService implements RentalService {
    private final RentalDao rentalDao;


    @Override
    public List<Rental> getAll() {
        List<Rental> rentals = rentalDao.findAll();
        for(Rental rental : rentals) {
            rental.setStateStr(Translator.rentalState.get(rental.getState()));
        }
        return rentals;
    }

    @Override
    public List<Rental> getAppliedRentals() {
        List<Rental> rentals = rentalDao.findApplied();
        for(Rental rental : rentals) {
            rental.setStateStr(Translator.rentalState.get(rental.getState()));
        }
        return rentals;
    }

    @Override
    public Rental getBy(int rentalNo) {
        Rental rental = rentalDao.findBy(rentalNo);
        rental.setStateStr(Translator.rentalState.get(rental.getState()));
        return rental;
    }

    @Override
    public int updateState(int rentalNo, String rentalState) {
        return rentalDao.updateState(rentalNo, rentalState);
    }

    @Override
    public List<Rental> getAllByName(String keyword) {
        List<Rental> rentals = rentalDao.findAllByName(keyword);
        for(Rental rental : rentals) {
            rental.setStateStr(Translator.rentalState.get(rental.getState()));
        }
        return rentals;
    }

    @Override
    public List<Rental> getAllByHostName(String keyword) {
        List<Rental> rentals = rentalDao.findAllByHostName(keyword);
        for(Rental rental : rentals) {
            rental.setStateStr(Translator.rentalState.get(rental.getState()));
        }
        return rentals;
    }
}
