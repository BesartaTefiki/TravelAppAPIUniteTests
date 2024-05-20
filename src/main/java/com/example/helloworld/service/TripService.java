package com.example.helloworld.service;

import com.example.helloworld.pojo.Trip;
import com.example.helloworld.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TripService {

    private final TripRepository tripRepository;

    @Autowired
    public TripService(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    public Trip createTrip(Trip trip) {
        return tripRepository.save(trip);
    }

    public List<Trip> findAllTrips() {
        return tripRepository.findAll();
    }

    public Optional<Trip> findByName(String name) {
        return tripRepository.findByName(name);
    }

    public Optional deleteByName(String name) {
        Optional<Trip> existingTripOpt = tripRepository.findByName(name);
        if (existingTripOpt.isPresent()) {
            Trip existingTrip = existingTripOpt.get();
            tripRepository.delete(existingTrip);
            return Optional.of(true);
        } else {
            return Optional.empty();
        }
    }

    public Optional<Trip> updateByName(String name, Trip updatedTrip) {
        Optional<Trip> existingTripOpt = tripRepository.findByName(name);

        if (existingTripOpt.isPresent()) {
            Trip existingTrip = existingTripOpt.get();

            existingTrip.setLocation(updatedTrip.getLocation());
            existingTrip.setDuration(updatedTrip.getDuration());
            existingTrip.setCost(updatedTrip.getCost());
            existingTrip.setStartDate(updatedTrip.getStartDate());

            return Optional.of(tripRepository.save(existingTrip));
        } else {
            return Optional.empty();
        }
    }




}
