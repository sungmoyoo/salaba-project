package salaba.service;

import java.sql.Date;
import java.util.List;
import salaba.vo.rental_home.RentalHome;
import salaba.vo.rental_home.RentalHomeReport;
import salaba.vo.rental_home.RentalHomeReview;
import salaba.vo.rental_home.Theme;

public interface RentalHomeService {

  RentalHome getRentalHomeMain();

  RentalHome getRentalHomeMainForLoginUser( List<Theme> themes );

  RentalHome getRentalHomeDetailView( int rentalHomeNo );

  RentalHome getRentalHomeConditionSearch(
      String regionName, Date checkInDate, Date checkOutDate, int capacity);

  RentalHome getRentalHomeThemeSearch( String themeName );

  void addRentalHomeReview( RentalHomeReview rentalHomeReview);

  void addRentalHomeReport( RentalHomeReport rentalHomeReport);

  List<RentalHomeReview> getRentalHomeReviewList( int rentalHomeNo );
}
