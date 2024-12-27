package com.ParkingSystem.Parking.System.dto;

import com.ParkingSystem.Parking.System.enums.ParkingLotPricingModel;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ParkingLotDTO {
    private String name;
    private String location;
    private ParkingLotPricingModel pricingModel;
}
