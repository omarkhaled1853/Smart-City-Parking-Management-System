package jdbcDemo.example.demo.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Repository
public class ReservationDao {
    private final JdbcTemplate jdbcTemplate;
    public ReservationDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public int saveReservation(Map<String, Object> reservationDetails){
        String query = "INSERT INTO Reservation (SpotID, UserID, StartTime, EndTime) " +
                "VALUES (?, ?, ?, ?)";

        int spotId = (int)reservationDetails.get("id");
        int userId = (int) reservationDetails.get("userId");

        String startTime = (String) reservationDetails.get("startDate");
        String endTime = (String) reservationDetails.get("endDate");

        Timestamp startTimeStamp = this.convertToTimeStamp(startTime);
        Timestamp endTimeStamp = this.convertToTimeStamp(endTime);


        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, spotId);
            ps.setInt(2, userId);
            ps.setTimestamp(3, startTimeStamp);
            ps.setTimestamp(4, endTimeStamp);

            return ps;

        }, keyHolder);

        return keyHolder.getKey().intValue();
    }

    private Timestamp convertToTimeStamp(String dateString){
        OffsetDateTime offsetDateTime = OffsetDateTime.parse(dateString, DateTimeFormatter.ISO_OFFSET_DATE_TIME);

        return Timestamp.from(offsetDateTime.toInstant());
    }

}