package com.example.helloworld.service;

import com.example.helloworld.pojo.TripFeedback;
import com.example.helloworld.repository.TripFeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class TripFeedbackService {

    private final TripFeedbackRepository tripFeedbackRepository;

    @Autowired
    public TripFeedbackService(TripFeedbackRepository tripFeedbackRepository) {
        this.tripFeedbackRepository = tripFeedbackRepository;
    }

    @Transactional
    public TripFeedback addTripFeedback(TripFeedback tripFeedback) {


        tripFeedback.setReviewDate(new Date());
        try {
            return tripFeedbackRepository.save(tripFeedback);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save trip feedback", e);
        }
    }

    public boolean deleteTripFeedback(String id) {
        if (!tripFeedbackRepository.existsById(id)) {
            return false;
        }

        try {
            tripFeedbackRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete trip feedback", e);
        }
    }


}
