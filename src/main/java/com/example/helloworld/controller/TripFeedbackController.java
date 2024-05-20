package com.example.helloworld.controller;

import com.example.helloworld.pojo.TripFeedback;
import com.example.helloworld.service.TripFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tripFeedback")
public class TripFeedbackController {

    private final TripFeedbackService tripFeedbackService;

    @Autowired
    public TripFeedbackController(TripFeedbackService tripFeedbackService) {
        this.tripFeedbackService = tripFeedbackService;
    }

    @PostMapping("/add")
    public ResponseEntity<TripFeedback> addTripFeedback(@RequestBody TripFeedback tripFeedback) {
        try {
            TripFeedback savedFeedback = tripFeedbackService.addTripFeedback(tripFeedback);
            return new ResponseEntity<>(savedFeedback, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTripFeedback(@PathVariable String id) {
        boolean isDeleted = tripFeedbackService.deleteTripFeedback(id);

        if (isDeleted) {
            return new ResponseEntity<>("Trip feedback deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Trip feedback not found", HttpStatus.NOT_FOUND);
        }
    }

}
