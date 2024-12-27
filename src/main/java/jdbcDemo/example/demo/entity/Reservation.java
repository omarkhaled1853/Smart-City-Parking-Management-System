package jdbcDemo.example.demo.entity;

import jdbcDemo.example.demo.enums.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Reservation {
    private int id;
    private int spotId;
    private int userId;

    private Timestamp startTime;
    private Timestamp endTime;

    private ReservationStatus status;
    private LocalDateTime createdAt;
}
