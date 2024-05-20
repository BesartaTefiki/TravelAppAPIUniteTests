package com.example.helloworld.repository;

import com.example.helloworld.pojo.Trip;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TripRepository extends MongoRepository<Trip, String> {

    Optional<Trip> findByName(String name);

}

