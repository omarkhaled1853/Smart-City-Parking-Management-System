package com.example.Backend.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.example.Backend.DTO.ParkingLotDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;


@Repository
public class ParkingLotRepository {
    @Autowired
    private DataSource dataSource;

    public void addParkingLot(ParkingLotDTO parkingLot) throws SQLException {
        String query = "INSERT INTO parkinglot (Name, UserID, Location, Capacity, PricingModel) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, parkingLot.getName());
            preparedStatement.setInt(2, parkingLot.getUserID());
            preparedStatement.setString(3, parkingLot.getLocation());
            preparedStatement.setInt(4, parkingLot.getCapacity());
            preparedStatement.setString(5, parkingLot.getPricingModel().name());

            preparedStatement.executeUpdate();
        }
    }

    public List<ParkingLotDTO> getAllParkingLots() throws SQLException {
        String query = "SELECT * FROM ParkingLot";
        List<ParkingLotDTO> parkingLots = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                ParkingLotDTO parkingLot = new ParkingLotDTO();
                parkingLot.setParkingLotID(resultSet.getInt("ParkingLotID"));
                parkingLot.setName(resultSet.getString("Name"));
                parkingLot.setLocation(resultSet.getString("Location"));
                parkingLot.setCapacity(resultSet.getInt("Capacity"));
                parkingLot.setPricingModel(ParkingLotDTO.PricingModel.valueOf(resultSet.getString("PricingModel")));

                parkingLots.add(parkingLot);
            }
        }

        return parkingLots;
    }

    public List<ParkingLotDTO> getSpesficParkingLots(int userID) throws SQLException {
        String query = "SELECT * FROM ParkingLot WHERE UserID = ?";
        List<ParkingLotDTO> parkingLots = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, userID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    ParkingLotDTO parkingLot = new ParkingLotDTO();
                    parkingLot.setUserID(resultSet.getInt("UserID"));
                    parkingLot.setParkingLotID(resultSet.getInt("ParkingLotID"));
                    parkingLot.setName(resultSet.getString("Name"));
                    parkingLot.setLocation(resultSet.getString("Location"));
                    parkingLot.setCapacity(resultSet.getInt("Capacity"));
                    parkingLot.setPricingModel(ParkingLotDTO.PricingModel.valueOf(resultSet.getString("PricingModel")));

                    parkingLots.add(parkingLot);
                }
            }
        }

        return parkingLots;
    }

    public void deleteParkingLot(int parkingLotID) throws SQLException {
        String query = "DELETE FROM parkinglot WHERE ParkingLotID = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, parkingLotID);
            preparedStatement.executeUpdate();
        }
    }
}
