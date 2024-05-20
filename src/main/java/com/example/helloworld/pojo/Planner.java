package com.example.helloworld.pojo;

import org.springframework.data.annotation.Id;

import java.util.Date;

public class Planner {

    @Id
    private String id;
    private String plannedDestination;
    private Date plannedStartDate;
    private Date plannedEndDate;
    private double plannedBudget;
    private String plannedStops;

    public Planner() {
    }

    public Planner(String id, String plannedDestination, Date plannedStartDate, Date plannedEndDate, double plannedBudget, String plannedStops) {
        this.id = id;
        this.plannedDestination = plannedDestination;
        this.plannedStartDate = plannedStartDate;
        this.plannedEndDate = plannedEndDate;
        this.plannedBudget = plannedBudget;
        this.plannedStops = plannedStops;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlannedDestination() {
        return plannedDestination;
    }

    public void setPlannedDestination(String plannedDestination) {
        this.plannedDestination = plannedDestination;
    }

    public Date getPlannedStartDate() {
        return plannedStartDate;
    }

    public void setPlannedStartDate(Date plannedStartDate) {
        this.plannedStartDate = plannedStartDate;
    }

    public Date getPlannedEndDate() {
        return plannedEndDate;
    }

    public void setPlannedEndDate(Date plannedEndDate) {
        this.plannedEndDate = plannedEndDate;
    }

    public double getPlannedBudget() {
        return plannedBudget;
    }

    public void setPlannedBudget(double plannedBudget) {
        this.plannedBudget = plannedBudget;
    }

    public String getPlannedStops() {
        return plannedStops;
    }

    public void setPlannedStops(String plannedStops) {
        this.plannedStops = plannedStops;
    }

    @Override
    public String toString() {
        return "Planner{" +
                "id='" + id + '\'' +
                ", plannedDestination='" + plannedDestination + '\'' +
                ", plannedStartDate=" + plannedStartDate +
                ", plannedEndDate=" + plannedEndDate +
                ", plannedBudget=" + plannedBudget +
                ", plannedStops='" + plannedStops + '\'' +
                '}';
    }
}
