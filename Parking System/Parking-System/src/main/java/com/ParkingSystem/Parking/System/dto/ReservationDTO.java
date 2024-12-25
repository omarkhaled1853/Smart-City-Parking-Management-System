package com.ParkingSystem.Parking.System.dto;

import com.ParkingSystem.Parking.System.enums.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDTO {
    private int reservationId;
    private Date startTime;
    private Date endTime;
    private ReservationStatus reservationStatus;
    private ParkingSpotDTO parkingSpot;
    private ParkingLotDTO parkingLot;
}
