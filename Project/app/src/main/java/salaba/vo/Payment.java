package salaba.vo.rental_home;

import lombok.Data;

import java.sql.Date;

@Data
public class Payment {
    private int reservationNo;
    private int paymentNo;
    private Date paymentDate;
    private int amount;
    private String cardNo;
    private Date vailidityDate;
    private String state;
}
