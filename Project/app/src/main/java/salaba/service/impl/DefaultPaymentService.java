package salaba.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import salaba.dao.PaymentDao;
import salaba.service.PaymentService;
import salaba.vo.Payment;
import salaba.vo.Reservation;

@Service
@RequiredArgsConstructor
public class DefaultPaymentService implements PaymentService {
    private final PaymentDao paymentDao;

    @Override
    public int getReservationKey() {
        return paymentDao.getReservationKey();
    }
    @Override
    public void addReservation(Reservation reservation) {
        paymentDao.addReservation(reservation);
    }
    @Override
    public void addPayment(Payment payment) {
        paymentDao.addPayment(payment);
    }

    @Override
    public int cancelPayment(int paymentNo) {
       return paymentDao.cancelPayment(paymentNo);
    }

}
