package jdbcDemo.example.demo.dao;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class PaymentDao {
    private final JdbcTemplate jdbcTemplate;

    public PaymentDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public void savePayment(Map<String, Object> paymentDetails, int reservationId){
        String query = "INSERT INTO Payment (ReservationID, Amount, PaymentStatus) VALUES (?, ?, ?)";

        Object totalPriceObject = paymentDetails.get("totalPrice");
        double amount = 0.0;

        if(totalPriceObject instanceof Integer){
            amount = ((Integer) totalPriceObject).doubleValue();
        } else if (totalPriceObject instanceof Double) {
            amount = (Double) totalPriceObject;
        }

        String paymentStatus = (String) paymentDetails.get("paymentStatus");

        jdbcTemplate.update(query, reservationId, amount, paymentStatus);
    }
}
