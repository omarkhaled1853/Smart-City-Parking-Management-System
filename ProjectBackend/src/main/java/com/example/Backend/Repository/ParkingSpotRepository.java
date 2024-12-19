package com.example.Backend.Repository;

import com.example.Backend.DTO.ParkingSpotDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ParkingSpotRepository {
    @Autowired
    private DataSource dataSource;

    public void addParkingSpot(ParkingSpotDTO parkingSpot) throws SQLException {
        String query = "INSERT INTO parkingspot (ParkingLotID, SpotType, Status, PricePerHour) VALUES (?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, parkingSpot.getParkingLotID());
            preparedStatement.setString(2, parkingSpot.getSpotType().name());
            preparedStatement.setString(3, parkingSpot.getStatus().name());
            preparedStatement.setBigDecimal(4, parkingSpot.getPricePerHour());

            preparedStatement.executeUpdate();
        }
    }

    public List<ParkingSpotDTO> getAllParkingSpots() throws SQLException {
        String query = "SELECT * FROM parkingspot";
        List<ParkingSpotDTO> parkingSpots = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                ParkingSpotDTO parkingSpot = new ParkingSpotDTO();
                parkingSpot.setSpotID(resultSet.getInt("SpotID"));
                parkingSpot.setParkingLotID(resultSet.getInt("ParkingLotID"));
                parkingSpot.setSpotType(ParkingSpotDTO.SpotType.valueOf(resultSet.getString("SpotType")));
                parkingSpot.setStatus(ParkingSpotDTO.Status.valueOf(resultSet.getString("Status")));
                parkingSpot.setPricePerHour(resultSet.getBigDecimal("PricePerHour"));

                parkingSpots.add(parkingSpot);
            }
        }

        return parkingSpots;
    }

    public void updateParkingSpotStatus(int spotID, ParkingSpotDTO.Status status) throws SQLException {
        String query = "UPDATE parkingspot SET Status = ? WHERE SpotID = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, status.name());
            preparedStatement.setInt(2, spotID);
            preparedStatement.executeUpdate();
        }
    }

    public void deleteParkingSpot(int spotID) throws SQLException {
        String query = "DELETE FROM parkingspot WHERE SpotID = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, spotID);
            preparedStatement.executeUpdate();
        }
    }
}
