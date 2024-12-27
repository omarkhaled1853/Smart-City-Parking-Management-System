package jdbcDemo.example.demo.entity;

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
    private String type;
    private String status;
    private int pricePerHour;
}
