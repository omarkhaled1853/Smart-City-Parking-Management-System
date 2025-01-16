package com.ParkingSystem.Parking.System.service;

import com.ParkingSystem.Parking.System.dao.PaymentDao;
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
