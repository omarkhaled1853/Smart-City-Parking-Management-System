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
public class Reservation {
    private int ReservationID;
    private int SpotID;
    private int UserID;

    private Timestamp StartTime;
    private Timestamp EndTime;

    private ReservationStatus Status;
    private LocalDateTime CreatedAt;
}
