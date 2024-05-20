package com.example.helloworld.controller;

import com.example.helloworld.pojo.Trip;
import com.example.helloworld.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/trip")
public class TripController {

    private final TripService tripService;

    @Autowired
    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @PostMapping("/createTrip")
    public ResponseEntity<Trip> createTrip(@RequestBody Trip tripData) {
        Trip trip = tripService.createTrip(tripData);
        return new ResponseEntity<>(trip, HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteTrip")
    public ResponseEntity<String> deleteTrip(@RequestBody String tripData) {
        JSONObject jsonObj = new JSONObject(tripData);
                String tripName = jsonObj.getString("name");
        Optional<Boolean> deletionResult = tripService.deleteByName(tripName);

        if (deletionResult.isPresent()) {
            Boolean wasDeleted = deletionResult.get();

            String trip = wasDeleted.toString();
            return new ResponseEntity<>(trip, HttpStatus.OK);

        } else {

            String trip = "Trip not found";
            return new ResponseEntity<>(trip, HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @PostMapping("/updateTrip")
    public ResponseEntity<?> updateTrip(@RequestBody String tripData) {
        JSONObject jsonObj = new JSONObject(tripData);
        String tripName = jsonObj.getString("name");

        Trip updatedTrip = new Trip();
        updatedTrip.setName(tripName);
        updatedTrip.setLocation(jsonObj.getString("location"));
        updatedTrip.setDuration(jsonObj.getString("duration"));
        updatedTrip.setCost(jsonObj.getDouble("cost"));

        String startDateStr = jsonObj.getString("startDate");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date startDate = dateFormat.parse(startDateStr);
            updatedTrip.setStartDate(startDate);
        } catch (ParseException e) {
            return new ResponseEntity<>("Invalid date format", HttpStatus.BAD_REQUEST);
        }

        Optional<Trip> result = tripService.updateByName(tripName, updatedTrip);
        if (result.isPresent()) {
            return new ResponseEntity<>(result.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Trip not found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/findTrip")
    public ResponseEntity<Trip> findTrip(@RequestBody Map<String, String> requestBody) {
        String name = requestBody.get("name");
        Optional<Trip> trip = tripService.findByName(name);

        if (trip.isPresent()) {
            return new ResponseEntity<>(trip.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/findAllTrips")
    public ResponseEntity<List<Trip>> findAllTrips() {
        List<Trip> trips = tripService.findAllTrips();

        if (!trips.isEmpty()) {
            return new ResponseEntity<>(trips, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }










}