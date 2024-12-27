package jdbcDemo.example.demo.controller;

import jdbcDemo.example.demo.entity.Spot;
import jdbcDemo.example.demo.service.NotificationService;
import jdbcDemo.example.demo.service.PaymentService;
import jdbcDemo.example.demo.service.ReservationService;
import jdbcDemo.example.demo.service.SpotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/garages")
public class SpotController {
    private final SpotService spotService;
    private final NotificationService notificationService;
    private final ReservationService reservationService;
    private final PaymentService paymentService;

    @Autowired
    public SpotController (SpotService spotService, NotificationService notificationService,
                           ReservationService reservationService, PaymentService paymentService){
        this.spotService = spotService;
        this.notificationService = notificationService;
        this.reservationService = reservationService;
        this.paymentService = paymentService;
    }

    @GetMapping("/{lotId}")
    public List<Spot> getAllSpotsByLotId(@PathVariable int lotId){

        return spotService.getAllSpotsByLotId(lotId);
    }
    @PostMapping("/update")
    public Boolean updateSpotStatus(@RequestBody Map<String, Object> request){
        int spotId = (int)request.get("id");
        String status = (String) request.get("status");
        int userId = (int) request.get("userId");

        int reservationId = reservationService.saveReservation(request);
        paymentService.savePayment(request, reservationId);

        boolean updated = spotService.updateSpotStatus(spotId, status);

        if(updated){
            String message = String.format("Spot #%d has been %s", spotId, status);
            notificationService.sendNotificationToDriver(userId, message);
        }

        return updated;
    }
}
