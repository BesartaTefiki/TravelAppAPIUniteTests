package com.example.helloworld.pojo;

import org.springframework.data.annotation.Id;

import java.util.Date;

public class BoughtTicket {
    @Id
    private String id;
    private String userEmail;
    private String flightNumber;
    private Date flightDate;

    public BoughtTicket(String userEmail, String flightNumber, Date flightDate) {
        this.userEmail = userEmail;
        this.flightNumber = flightNumber;
        this.flightDate = flightDate;
    }

    public BoughtTicket() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public Date getFlightDate() {
        return flightDate;
    }

    public void setFlightDate(Date flightDate) {
        this.flightDate = flightDate;
    }

    @Override
    public String toString() {
        return "BoughtTicket{" +
                "id='" + id + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", flightNumber='" + flightNumber + '\'' +
                ", flightDate=" + flightDate +
                '}';
    }
}
