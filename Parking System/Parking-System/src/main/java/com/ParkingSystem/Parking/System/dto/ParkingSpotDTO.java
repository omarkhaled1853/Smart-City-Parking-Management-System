package com.ParkingSystem.Parking.System.dto;

import com.ParkingSystem.Parking.System.enums.ParkingSpotStatus;
import com.ParkingSystem.Parking.System.enums.ParkingSpotType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParkingSpotDTO {
    private int spotId;
    private ParkingSpotType spotType;
    private ParkingSpotStatus status;
}
