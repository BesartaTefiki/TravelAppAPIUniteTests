package com.example.helloworld.repository;

import com.example.helloworld.pojo.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends MongoRepository<Booking, String> {


    Optional<Booking> findByTripNameAndUserEmail(String tripName, String userEmail);


}
