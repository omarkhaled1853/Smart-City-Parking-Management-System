package jdbcDemo.example.demo.service;

import jdbcDemo.example.demo.dao.ReservationDao;
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
