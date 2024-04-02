package salaba.vo;

import java.util.Date;
import lombok.Data;

@Data
public class HostReservation {
  String rentalHomeName;
  String memberName;
  int reservationNo;
  int memberNo;
  int hostNo;
  int rentalHomeNo;
  Date startDate;
  Date endDate;
  int state;
  Date paymentDate;
}
