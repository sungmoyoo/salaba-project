package salaba.service;

import salaba.vo.Payment;
import salaba.vo.Reservation2;

public interface PaymentService {
    int getReservationKey();
    void addReservation(Reservation2 reservation2);
    void addPayment(Payment payment);

    int cancelPayment(int paymentNo);
}
