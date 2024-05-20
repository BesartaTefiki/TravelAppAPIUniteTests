package com.example.helloworld.service;

import com.example.helloworld.pojo.Flight;
import com.example.helloworld.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FlightService {
    private final FlightRepository flightRepository;

    @Autowired
    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public Flight createFlight(Flight flight) {
        return flightRepository.save(flight);
    }

    public List<Flight> findAllFlights() {
        return flightRepository.findAll();
    }

    public Optional<Flight> findByFlightNumber (String flightNumber) {
        return flightRepository.findByFlightNumber(flightNumber);
    }

    public Optional deleteByFlightNumber (String flightNumber) {
        Optional<Flight>  existingFlightOpt = flightRepository.findByFlightNumber(flightNumber);
        if (existingFlightOpt.isPresent()) {
            Flight existingFlight = existingFlightOpt.get();
            flightRepository.delete(existingFlight);
            return Optional.of(true);
        }
        else {
            return Optional.empty();
        }
    }

    public Optional<Flight> updateByFlightNumber(String flightNumber, Flight updatedFlight) {
        Optional<Flight> existingFlight = flightRepository.findByFlightNumber(flightNumber);

        if (existingFlight.isPresent()) {
            Flight flightToUpdate = existingFlight.get();

            flightToUpdate.setOriginAirport(updatedFlight.getOriginAirport());
            flightToUpdate.setDestinationAirport(updatedFlight.getDestinationAirport());
            flightToUpdate.setPrice(updatedFlight.getPrice());
            flightToUpdate.setFlightDate(updatedFlight.getFlightDate());


            return Optional.of(flightRepository.save(flightToUpdate));
        } else {
            return Optional.empty();
        }
    }



}
