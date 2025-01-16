package com.ParkingSystem.Parking.System.service;

import com.ParkingSystem.Parking.System.dao.ReservationDao;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ReservationService {
    private final ReservationDao reservationDao;
    public ReservationService(ReservationDao reservationDao){
        this.reservationDao = reservationDao;
    }
    public int saveReservation(Map<String, Object> reservationDetails) {
        return reservationDao.saveReservation(reservationDetails);
    }
}
