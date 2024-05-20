package com.example.helloworld.pojo;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Booking {

    @Id
    private String id;
    private String userEmail;
    private String tripName;
    private Trip tripData;

    public Booking() {

    }
    public Booking(String userEmail, String tripName, Trip tripData) {
        this.userEmail = userEmail;
        this.tripName = tripName;
        this.tripData = tripData;
    }


    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public Trip getTripData() {
        return tripData;
    }

    public void setTripData(Trip tripData) {
        this.tripData = tripData;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id='" + id + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", tripName='" + tripName + '\'' +
                ", tripData=" + tripData +
                '}';
    }
}
