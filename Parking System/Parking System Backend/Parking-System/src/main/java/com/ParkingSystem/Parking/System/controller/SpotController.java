package com.ParkingSystem.Parking.System.controller;

import com.ParkingSystem.Parking.System.dto.HomeParkingSpotDTO;
import com.ParkingSystem.Parking.System.service.NotificationService;
import com.ParkingSystem.Parking.System.service.PaymentService;
import com.ParkingSystem.Parking.System.service.ReservationService;
import com.ParkingSystem.Parking.System.service.SpotService;
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
    public List<HomeParkingSpotDTO> getAllSpotsByLotId(@PathVariable int lotId){

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
            notificationService.sendNotification(userId, message);
        }

        return updated;
    }
}
