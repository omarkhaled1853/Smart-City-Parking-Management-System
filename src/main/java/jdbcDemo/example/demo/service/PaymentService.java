package jdbcDemo.example.demo.service;

import jdbcDemo.example.demo.dao.PaymentDao;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PaymentService {
    private final PaymentDao paymentDao;

    public PaymentService(PaymentDao paymentDao){
        this.paymentDao = paymentDao;
    }
    public void savePayment(Map<String, Object> paymentDetails, int reservationId) {
        paymentDao.savePayment(paymentDetails, reservationId);
    }

}
