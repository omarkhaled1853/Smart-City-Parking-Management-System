package com.example.Backend.DTO;

public class ParkingLotDTO {
    private int parkingLotID;
    private int UserID;
    private String name;
    private String location;
    private int capacity;
    private PricingModel pricingModel;

    public enum PricingModel {
        Static, Dynamic
    }

    // Getters and Setters
    public int getParkingLotID() {
        return parkingLotID;
    }

    public void setUserID(int UserID) {
        this.UserID = UserID;
    }
    public int getUserID() {
        return UserID;
    }

    public void setParkingLotID(int parkingLotID) {
        this.parkingLotID = parkingLotID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public PricingModel getPricingModel() {
        return pricingModel;
    }

    public void setPricingModel(PricingModel pricingModel) {
        this.pricingModel = pricingModel;
    }
}