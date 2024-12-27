package com.ParkingSystem.Parking.System.service.impl;

import com.ParkingSystem.Parking.System.dto.NearExpireReservationDTO;
import com.ParkingSystem.Parking.System.dto.NotificationDTO;
import com.ParkingSystem.Parking.System.dto.ReservationDTO;
import com.ParkingSystem.Parking.System.repository.NotificationRepository;
import com.ParkingSystem.Parking.System.repository.ReservationRepository;
import com.ParkingSystem.Parking.System.service.interfaces.DriverProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

;

@Service
public class DriverProfileServiceImpl implements DriverProfileService {
    @Autowired
    private final ReservationRepository reservationRepository;
    private final NotificationRepository notificationRepository;

    public DriverProfileServiceImpl(ReservationRepository reservationRepository,
                                    NotificationRepository notificationRepository) {
        this.reservationRepository = reservationRepository;
        this.notificationRepository = notificationRepository;
    }

    @Override
    public List<ReservationDTO> getAllReservation(int userID)  {
        return reservationRepository.getAllReservations(userID);
    }

    @Override
    public List<ReservationDTO> getAllReservationsByLocation(int userId, String location) {
        return reservationRepository.getAllReservationsByLocation(userId, location);
    }

    @Override
    public List<NearExpireReservationDTO> getNearExpireReservations() {
        return reservationRepository.getNearExpireReservations();
    }

    @Override
    public List<NotificationDTO> getAllNotifications(int userId) {
        return notificationRepository.getAllNotifications(userId);
    }
}
