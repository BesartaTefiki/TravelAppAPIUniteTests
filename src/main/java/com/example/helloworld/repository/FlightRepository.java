package com.example.helloworld.repository;

import com.example.helloworld.pojo.Flight;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FlightRepository extends MongoRepository <Flight,String> {
    Optional<Flight> findByFlightNumber (String flightNumber);
}
