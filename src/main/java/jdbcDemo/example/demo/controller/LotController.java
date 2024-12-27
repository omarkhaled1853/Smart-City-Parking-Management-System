package jdbcDemo.example.demo.controller;

import jdbcDemo.example.demo.service.LotService;
import jdbcDemo.example.demo.entity.Lot;
import jdbcDemo.example.demo.entity.Spot;
import jdbcDemo.example.demo.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/garages")
public class LotController {
    private final LotService lotService;
    private final NotificationService notificationService;

    @Autowired
    public LotController(LotService lotService, NotificationService notificationService){
        this.lotService = lotService;
        this.notificationService = notificationService;
    }

    @GetMapping
    public List<Lot> getAllLots(){
        return lotService.getAllLots();
    }

    @GetMapping("/{lotId}")
    public List<Spot> getAllSpotsByLotId(@PathVariable int lotId){
        return lotService.getAllSpotsByLotId(lotId);
    }
    @PostMapping("/update")
    public Boolean updateSpotStatus(@RequestBody Map<String, Object> request){
        int spotId = (int)request.get("id");
        String status = (String) request.get("status");
        int userId = (int) request.get("userId");

        boolean updated = lotService.updateSpotStatus(spotId, status);

        if(updated){
            String message = String.format("Spot #%d has been %s", spotId, status);
            notificationService.sendNotificationToDriver(userId, message);
        }

        return updated;
    }
}
