package salaba.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    private static final long serialVersionUID = 100L;
    private int reservationNo;
    private String paymentNo;
    private Date paymentDate;
    private int amount;
    private String state;
    private String payMethod;
}
