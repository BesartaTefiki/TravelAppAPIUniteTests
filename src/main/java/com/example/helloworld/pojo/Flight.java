package com.example.helloworld.pojo;

import org.springframework.data.annotation.Id;

import java.util.Date;

public class Flight {

    @Id
    private String id;
    private String flightNumber;
    private Date flightDate;
    private double price;
    private String originAirport;
    private String destinationAirport;

    public Flight() {
    }

    public Flight(String flightNumber, Date flightDate, double price, String originAirport, String destinationAirport) {
        this.flightNumber = flightNumber;
        this.flightDate = flightDate;
        this.price = price;
        this.originAirport = originAirport;
        this.destinationAirport = destinationAirport;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getOriginAirport() {
        return originAirport;
    }

    public void setOriginAirport(String originAirport) {
        this.originAirport = originAirport;
    }

    public String getDestinationAirport() {
        return destinationAirport;
    }

    public void setDestinationAirport(String destinationAirport) {
        this.destinationAirport = destinationAirport;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "flightNumber='" + flightNumber + '\'' +
                ", flightDate=" + flightDate +
                ", price=" + price +
                ", originAirport='" + originAirport + '\'' +
                ", destinationAirport='" + destinationAirport + '\'' +
                '}';
    }
}
