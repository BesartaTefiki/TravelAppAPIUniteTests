package com.example.helloworld.repository;

import com.example.helloworld.pojo.TripFeedback;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TripFeedbackRepository extends MongoRepository<TripFeedback,String> {

}
