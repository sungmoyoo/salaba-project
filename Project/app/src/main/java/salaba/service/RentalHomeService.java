package salaba.service;

import java.sql.Date;
import java.util.List;
import salaba.vo.rental_home.RentalHome;
import salaba.vo.rental_home.RentalHomeFacility;
import salaba.vo.rental_home.RentalHomePhoto;
import salaba.vo.rental_home.RentalHomeReport;
import salaba.vo.rental_home.RentalHomeReview;
import salaba.vo.rental_home.Theme;

public interface RentalHomeService {

  List<RentalHome> getRentalHomeMain();

  List<RentalHome> getRentalHomeMainForLoginUser( List<Theme> themes );

  RentalHome getRentalHomeDetailView( int rentalHomeNo );

  double rentalHomeReviewAverage(int rentalHomeNo);

  List<RentalHome> getRentalHomeConditionSearch(
      String regionName, Date checkInDate, Date checkOutDate, int capacity);

  List<RentalHome> getRentalHomeThemeSearch( String themeName );

  List<RentalHomePhoto> getRentalHomePhotos( int rentalHomeNo );

  List<RentalHomeFacility> getRentalHomeFacilities( int rentalHomeNo );

  List<Theme> getAllThemes();

  List<RentalHomeFacility> getAllFacilities();

  void addRentalHomeReview( RentalHomeReview rentalHomeReview);

  void addRentalHomeReport( RentalHomeReport rentalHomeReport);

  List<RentalHomeReview> getRentalHomeReviewList( int rentalHomeNo );

  RentalHome getReservationInfo(int rentalHomeNo);
}
