package com.example.helloworld.controller;

import com.example.helloworld.pojo.Booking;
import com.example.helloworld.service.BookingService;
import com.example.helloworld.TripNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/bookTrip")
    public ResponseEntity<?> bookTrip(@RequestParam String userEmail, @RequestParam String tripName) {
        try {
            Booking booking = bookingService.bookTrip(userEmail, tripName);
            return new ResponseEntity<>(booking, HttpStatus.CREATED);
        } catch (TripNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while booking the trip.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/cancelTrip")
    public ResponseEntity<?> cancelTrip(@RequestParam String tripName, @RequestParam String userEmail) {
        try {
            boolean success = bookingService.cancelTrip(tripName,userEmail);
            if (success) {
                return new ResponseEntity<>("Booking cancelled successfully.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Booking not found.", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while cancelling the booking.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
