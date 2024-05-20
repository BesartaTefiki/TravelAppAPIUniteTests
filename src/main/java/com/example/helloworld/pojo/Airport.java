package com.example.helloworld.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Airport {
    @Id
    private String airportId;
    private String name;
    private String city;
    private String country;
    private String timezone;

    public Airport() {
    }

    public Airport(String airportId, String name, String city, String country, String timezone) {
        this.airportId = airportId;
        this.name = name;
        this.city = city;
        this.country = country;
        this.timezone = timezone;
    }


    public String getAirportId() {
        return airportId;
    }

    public void setAirportId(String airportId) {
        this.airportId = airportId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    @Override
    public String toString() {
        return "Airport{" +
                "airportId='" + airportId + '\'' +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", timezone='" + timezone + '\'' +
                '}';
    }
}
