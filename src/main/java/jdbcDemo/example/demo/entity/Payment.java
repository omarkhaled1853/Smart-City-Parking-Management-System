package jdbcDemo.example.demo.entity;

import jdbcDemo.example.demo.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Payment {
    private int id;
    private int reservationId;
    private float amount;
    private LocalDateTime paymentTime;
    private PaymentStatus status;
}
