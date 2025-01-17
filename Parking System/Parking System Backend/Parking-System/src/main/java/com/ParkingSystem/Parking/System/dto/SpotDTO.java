package com.ParkingSystem.Parking.System.dto;

import java.math.BigDecimal;

public class SpotDTO {
    private int spotID;
    private int parkingLotID;
    private SpotType spotType;
    private Status status;
    private BigDecimal pricePerHour;

    public enum SpotType {
        REGULAR, DISABLED, EVCHARGING
    }

    public enum Status {
        AVAILABLE, RESERVED, OCCUPIED
    }

    // Getters and Setters
    public int getSpotID() {
        return spotID;
    }

    public void setSpotID(int spotID) {
        this.spotID = spotID;
    }

    public int getParkingLotID() {
        return parkingLotID;
    }

    public void setParkingLotID(int parkingLotID) {
        this.parkingLotID = parkingLotID;
    }

    public SpotType getSpotType() {
        return spotType;
    }

    public void setSpotType(SpotType spotType) {
        this.spotType = spotType;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public BigDecimal getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(BigDecimal pricePerHour) {
        this.pricePerHour = pricePerHour;
    }
}
