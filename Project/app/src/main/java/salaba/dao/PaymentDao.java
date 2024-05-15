package salaba.dao;

import org.apache.ibatis.annotations.Mapper;
import salaba.vo.Payment;
import salaba.vo.Reservation2;

@Mapper
public interface PaymentDao {
    int getReservationKey();
    void addReservation(Reservation2 reservation);
    void addPayment(Payment payment);

    int cancelPayment(int paymentNo);


}
