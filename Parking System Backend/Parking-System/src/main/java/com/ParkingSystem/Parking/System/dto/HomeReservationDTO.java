package com.ParkingSystem.Parking.System.dto;

import com.ParkingSystem.Parking.System.enums.ReservationStatus;
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
public class HomeReservationDTO {
    private int reservationId;
    private int spotId;
    private int userId;

    private Timestamp startTime;
    private Timestamp endTime;

    private ReservationStatus status;
    private LocalDateTime createdAt;
}
