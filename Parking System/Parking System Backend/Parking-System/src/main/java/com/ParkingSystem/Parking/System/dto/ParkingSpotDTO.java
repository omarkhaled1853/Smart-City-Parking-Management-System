package com.ParkingSystem.Parking.System.dto;

import com.ParkingSystem.Parking.System.enums.ParkingSpotStatus;
import com.ParkingSystem.Parking.System.enums.ParkingSpotType;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ParkingSpotDTO {
    private int spotId;
    private ParkingSpotType spotType;
    private ParkingSpotStatus status;
}
