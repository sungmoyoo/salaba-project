package salaba.service;

import java.util.List;
import salaba.vo.rental_home.RentalHome;
import salaba.vo.rental_home.RentalHomeReview;
import salaba.vo.rental_home.Theme;

public interface RentalHomeService {

  RentalHome getRentalHomeMain();

  RentalHome getRentalHomeMainForLoginUser( List<Theme> themes );

  RentalHome getRentalHomeDetailView( int rentalHomeNo );

  void addRentalHomeReview( RentalHomeReview rentalHomeReview);
}
