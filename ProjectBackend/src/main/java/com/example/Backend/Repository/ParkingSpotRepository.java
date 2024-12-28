package com.example.Backend.Repository;

import com.example.Backend.DTO.SpotDTO;
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

    public void addParkingSpot(SpotDTO parkingSpot) throws SQLException {
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

    public List<SpotDTO> getAllParkingSpots() throws SQLException {
        String query = "SELECT * FROM parkingspot";
        List<SpotDTO> parkingSpots = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                SpotDTO parkingSpot = new SpotDTO();
                parkingSpot.setSpotID(resultSet.getInt("SpotID"));
                parkingSpot.setParkingLotID(resultSet.getInt("ParkingLotID"));
                parkingSpot.setSpotType(SpotDTO.SpotType.valueOf(resultSet.getString("SpotType")));
                parkingSpot.setStatus(SpotDTO.Status.valueOf(resultSet.getString("Status")));
                parkingSpot.setPricePerHour(resultSet.getBigDecimal("PricePerHour"));

                parkingSpots.add(parkingSpot);
            }
        }

        return parkingSpots;
    }
    public List<SpotDTO> getSpecificParkingSpots(int id) throws SQLException {
        String query = "SELECT * FROM parkingspot WHERE ParkingLotID = ?";
        List<SpotDTO> parkingSpots = new ArrayList<>();

        // Use PreparedStatement instead of Statement
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set the parameter for the query
            preparedStatement.setInt(1, id);

            // Execute the query
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    // Map result set to ParkingSpotDTO
                    SpotDTO parkingSpot = new SpotDTO();
                    parkingSpot.setSpotID(resultSet.getInt("SpotID"));
                    parkingSpot.setParkingLotID(resultSet.getInt("ParkingLotID"));
                    parkingSpot.setSpotType(SpotDTO.SpotType.valueOf(resultSet.getString("SpotType")));
                    parkingSpot.setStatus(SpotDTO.Status.valueOf(resultSet.getString("Status")));
                    parkingSpot.setPricePerHour(resultSet.getBigDecimal("PricePerHour"));

                    parkingSpots.add(parkingSpot);
                }
            }
        }

        return parkingSpots;
    }

    public void updateParkingSpotStatus(int spotID, SpotDTO.Status status) throws SQLException {
        String query = "UPDATE parkingspot SET Status = ? WHERE SpotID = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, status.name());
            preparedStatement.setInt(2, spotID);
            preparedStatement.executeUpdate();
        }
    }
    public void updateSpotPricing(int spotID, double price) throws SQLException {
        String query = "UPDATE parkingspot SET PricePerHour = ? WHERE SpotID = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setDouble(1, price);
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
