package jdbcDemo.example.demo.entity;

import jdbcDemo.example.demo.enums.PricingModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParkingLot {
    private int ParkingLotID;
    private String Name;
    private String Location;
    private int Capacity;
    private PricingModel pricingModel;
    private LocalDateTime CreatedAt;
}
