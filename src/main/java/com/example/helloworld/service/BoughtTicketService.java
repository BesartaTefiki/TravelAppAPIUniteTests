package com.example.helloworld.service;

import com.example.helloworld.FlightNotFoundException;
import com.example.helloworld.pojo.BoughtTicket;
import com.example.helloworld.pojo.Flight;
import com.example.helloworld.repository.BoughtTicketRepository;
import com.example.helloworld.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Service
public class BoughtTicketService {

    @Autowired
    private BoughtTicketRepository boughtTicketRepository;

    @Autowired
    private FlightRepository flightRepository;

    @Transactional
    public BoughtTicket buyTicket(String userEmail, String flightNumber, String flightDateString) throws FlightNotFoundException, ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date flightDate = dateFormat.parse(flightDateString);

        Optional<Flight> optionalFlight = flightRepository.findByFlightNumber(flightNumber);
        if (!optionalFlight.isPresent()) {
            throw new FlightNotFoundException("Flight with number " + flightNumber + " not found");
        }

        Flight flight = optionalFlight.get();
        if (!dateFormat.format(flight.getFlightDate()).equals(dateFormat.format(flightDate))) {
            throw new FlightNotFoundException("Flight with number " + flightNumber + " not available on date " + flightDate);
        }

        BoughtTicket newTicket = new BoughtTicket();
        newTicket.setUserEmail(userEmail);
        newTicket.setFlightNumber(flightNumber);
        newTicket.setFlightDate(flightDate);

        return boughtTicketRepository.save(newTicket);
    }

    @Transactional
    public boolean cancelTicket(String id) {
        Optional<BoughtTicket> ticketOpt = boughtTicketRepository.findById(id);

        if (ticketOpt.isPresent()) {
            boughtTicketRepository.delete(ticketOpt.get());
            return true;
        } else {
            return false;
        }
    }
}