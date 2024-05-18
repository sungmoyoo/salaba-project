package salaba.service;

import salaba.vo.Payment;
import salaba.vo.Reservation;

public interface PaymentService {
    int getReservationKey();
    void addReservation(Reservation reservation2);
    void addPayment(Payment payment);

    int cancelPayment(int paymentNo);
}
