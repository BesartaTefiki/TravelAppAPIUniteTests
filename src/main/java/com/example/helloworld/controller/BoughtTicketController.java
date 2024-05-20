package com.example.helloworld.controller;

import com.example.helloworld.FlightNotFoundException;
import com.example.helloworld.TripNotFoundException;
import com.example.helloworld.pojo.BoughtTicket;
import com.example.helloworld.pojo.Flight;
import com.example.helloworld.service.BoughtTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/tickets")
public class BoughtTicketController {

    @Autowired
    private BoughtTicketService boughtTicketService;

    @PostMapping("/buyTicket")
    public ResponseEntity<?> buyTicket(@RequestParam String userEmail,
                                       @RequestParam String flightNumber,
                                       @RequestParam String flightDateString) {
        try {
            BoughtTicket newTicket = boughtTicketService.buyTicket(userEmail, flightNumber, flightDateString);
            return new ResponseEntity<>(newTicket, HttpStatus.CREATED);
        } catch (FlightNotFoundException | ParseException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while buying a ticket.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/cancelTicket")
    public ResponseEntity<String> cancelTicket(@RequestParam String id) {
        boolean isCancelled = boughtTicketService.cancelTicket(id);
        if (isCancelled) {
            return ResponseEntity.ok("Ticket cancelled successfully");
        } else {
            return ResponseEntity.badRequest().body("Ticket cancellation failed");
        }
    }


}

