package jdbcDemo.example.demo.entity;

import jdbcDemo.example.demo.enums.SpotStatus;
import jdbcDemo.example.demo.enums.SpotType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParkingSpot {
    private int SpotID;
    private int ParkingLotID;
    private SpotType SpotType;
    private SpotStatus Status;
    private int PricePerHour;
}
