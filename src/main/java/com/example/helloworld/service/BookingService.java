package com.example.helloworld.service;

import com.example.helloworld.TripNotFoundException;
import com.example.helloworld.pojo.Booking;
import com.example.helloworld.pojo.Trip;
import com.example.helloworld.repository.BookingRepository;
import com.example.helloworld.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private TripRepository tripRepository;

    @Transactional
    public Booking bookTrip(String userEmail, String tripName) throws TripNotFoundException {
        Optional<Trip> optionalTrip = tripRepository.findByName(tripName);
        if (!optionalTrip.isPresent()) {
            throw new TripNotFoundException("Trip with name " + tripName + " not found");
        }

        Trip trip = optionalTrip.get();
        Booking newBooking = new Booking();
        newBooking.setUserEmail(userEmail);
        newBooking.setTripName(tripName);
        newBooking.setTripData(trip);

        return bookingRepository.save(newBooking);
    }

    @Transactional
    public boolean cancelTrip(String tripName, String userEmail) {
        Optional<Booking> bookingOpt = bookingRepository.findByTripNameAndUserEmail(tripName, userEmail);

        if (bookingOpt.isPresent()) {
            Booking existingBooking = bookingOpt.get();
            bookingRepository.delete(existingBooking);
            return true;
        } else {
            return false;
        }
    }
}
