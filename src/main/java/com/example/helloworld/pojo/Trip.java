package com.example.helloworld.pojo;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Document
public class Trip {
    @Id
    private String id;
    private String name;
    private String location;
    private String duration;
    private Double cost;
    private Date startDate;

    public Trip() {}

    public Trip(String name, String location, String duration, Double cost, Date startDate) {
        this.name = name;
        this.location = location;
        this.duration = duration;
        this.cost = cost;
        this.startDate = startDate;
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

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

}
