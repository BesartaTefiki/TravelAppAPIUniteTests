package com.example.helloworld.controller;

import com.example.helloworld.pojo.Airport;
import com.example.helloworld.service.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/airports")
public class AirportController {

    private final AirportService airportService;

    @Autowired
    public AirportController(AirportService airportService) {
        this.airportService = airportService;
    }

    @GetMapping("/allAirports")
    public List<Airport> listAllAirports() {
        return airportService.listAllAirports();
    }

    @GetMapping("/search")
    public List<Airport> findAirportsByLocation(@RequestParam String city, @RequestParam String country) {
        return airportService.findAirportsByLocation(city, country);
    }

    @GetMapping("/{id}")
    public Airport getAirportDetails(@PathVariable String id) {
        return airportService.getAirportDetails(id);
    }

    @PostMapping("/addAirport")
    public Airport addAirport(@RequestBody Airport airport) {
        return airportService.addAirport(airport);
    }
}