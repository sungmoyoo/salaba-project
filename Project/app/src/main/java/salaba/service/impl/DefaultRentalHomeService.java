package salaba.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import salaba.dao.RentalHomeDao;
import salaba.service.RentalHomeService;
import salaba.vo.rental_home.RentalHome;

@RequiredArgsConstructor
@Service
public class DefaultRentalHomeService implements RentalHomeService {

  private final RentalHomeDao rentalHomeDao;

  @Override
  public RentalHome getRentalHomeMain() {
    return rentalHomeDao.rentalHomeDefaultSelect();
  }
}
