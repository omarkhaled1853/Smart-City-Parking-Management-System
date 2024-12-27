package com.ParkingSystem.Parking.System.repository;

import com.ParkingSystem.Parking.System.dto.NearExpireReservationDTO;
import com.ParkingSystem.Parking.System.dto.ReservationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.ParkingSystem.Parking.System.utils.DatabaseHandler.*;
import static com.ParkingSystem.Parking.System.utils.Mapper.toNearExpireReservationDto;
import static com.ParkingSystem.Parking.System.utils.Mapper.toReservationDto;

@Repository
public class ReservationRepository {
    @Autowired
    private DataSource dataSource;

    public List<ReservationDTO> getAllReservations(int userId) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;
        List<ReservationDTO> reservationDTOList = new ArrayList<>();

        try {
            connection = getConnection(dataSource);
            String sql = "{CALL GetAllReservations(?)}";
            callableStatement = connection.prepareCall(sql);
            callableStatement.setInt("user_id", userId);
            resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                reservationDTOList.add(toReservationDto(resultSet));
            }
        } catch (SQLException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        } finally {
            closeResultSet(resultSet);
            closeCallableStatement(callableStatement);
            closeConnection(connection);
        }

        return reservationDTOList;
    }

    public List<ReservationDTO> getAllReservationsByLocation(int userId, String location) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;
        List<ReservationDTO> reservationDTOList = new ArrayList<>();

        try {
            connection = getConnection(dataSource);
            String sql = "{CALL GetAllReservationsByLocation(?, ?)}";
            callableStatement = connection.prepareCall(sql);
            callableStatement.setInt("user_id", userId);
            callableStatement.setString("location", location);
            resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                reservationDTOList.add(toReservationDto(resultSet));
            }
        } catch (SQLException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        } finally {
            closeResultSet(resultSet);
            closeCallableStatement(callableStatement);
            closeConnection(connection);
        }

        return reservationDTOList;
    }

    public List<NearExpireReservationDTO> getNearExpireReservations() {
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;
        List<NearExpireReservationDTO> nearExpireReservationDTOList = new ArrayList<>();

        try {
            connection = getConnection(dataSource);
            String sql = "{CALL GetNearExpireReservations()}";
            callableStatement = connection.prepareCall(sql);
            resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                nearExpireReservationDTOList.add(toNearExpireReservationDto(resultSet));
            }
        } catch (SQLException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        } finally {
            closeResultSet(resultSet);
            closeCallableStatement(callableStatement);
            closeConnection(connection);
        }

        return nearExpireReservationDTOList;
    }
}
