package com.ParkingSystem.Parking.System.classes;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
public class RequestDTO {
    private int userID;
    private int technicianID;
    private int ID;
    private List<Byte> Attachment;
    private String state;
    private Date startDate;
    private Date endDate;

    public RequestDTO(int userID, int technicianID, int ID, List<Byte> attachment, String state, Date startDate, Date endDate) {
        this.userID = userID;
        this.technicianID = technicianID;
        this.ID = ID;
        Attachment = attachment;
        this.state = state;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
