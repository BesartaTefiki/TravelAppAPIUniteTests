package com.example.helloworld.repository;

import com.example.helloworld.pojo.Airport;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AirportRepository extends MongoRepository<Airport,String> {

    List<Airport> findByCityAndCountry(String city, String country);
}
