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
public class Spot {
    private int id;
    private int lotId;
    private SpotType type;
    private SpotStatus status;
    private int pricePerHour;
}
