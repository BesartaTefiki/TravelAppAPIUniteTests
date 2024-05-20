package com.example.helloworld.ServiceTests;

import com.example.helloworld.FlightNotFoundException;
import com.example.helloworld.pojo.BoughtTicket;
import com.example.helloworld.pojo.Flight;
import com.example.helloworld.repository.BoughtTicketRepository;
import com.example.helloworld.repository.FlightRepository;
import com.example.helloworld.service.BoughtTicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class BoughtTicketServiceTest {

    @Mock
    private BoughtTicketRepository boughtTicketRepository;

    @Mock
    private FlightRepository flightRepository;

    @InjectMocks
    private BoughtTicketService boughtTicketService;

    private SimpleDateFormat dateFormat;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }

    @Test
    void testBuyTicketSuccess() throws ParseException, FlightNotFoundException {
        String userEmail = "user@example.com";
        String flightNumber = "FL123";
        String flightDateString = "2024-01-20";
        Date flightDate = dateFormat.parse(flightDateString);

        Flight flight = new Flight();
        flight.setFlightDate(flightDate);
        flight.setFlightNumber(flightNumber);

        BoughtTicket newTicket = new BoughtTicket();
        newTicket.setUserEmail(userEmail);
        newTicket.setFlightNumber(flightNumber);
        newTicket.setFlightDate(flightDate);

        when(flightRepository.findByFlightNumber(flightNumber)).thenReturn(Optional.of(flight));
        when(boughtTicketRepository.save(any(BoughtTicket.class))).thenReturn(newTicket);

        BoughtTicket result = boughtTicketService.buyTicket(userEmail, flightNumber, flightDateString);

        assertNotNull(result);
        assertEquals(userEmail, result.getUserEmail());
        assertEquals(flightNumber, result.getFlightNumber());
        assertEquals(flightDate, result.getFlightDate());
    }

    @Test
    void testBuyTicketFlightNotFound() throws ParseException {
        String userEmail = "user@example.com";
        String flightNumber = "FL123";
        String flightDateString = "2024-01-20";

        when(flightRepository.findByFlightNumber(flightNumber)).thenReturn(Optional.empty());

        FlightNotFoundException exception = assertThrows(FlightNotFoundException.class, () -> {
            boughtTicketService.buyTicket(userEmail, flightNumber, flightDateString);
        });

        assertEquals("Flight with number FL123 not found", exception.getMessage());
    }

    @Test
    void testBuyTicketFlightDateMismatch() throws ParseException {
        String userEmail = "user@example.com";
        String flightNumber = "FL123";
        String flightDateString = "2024-01-20";
        Date flightDate = dateFormat.parse(flightDateString);

        Flight flight = new Flight();
        flight.setFlightDate(dateFormat.parse("2024-01-21"));
        flight.setFlightNumber(flightNumber);

        when(flightRepository.findByFlightNumber(flightNumber)).thenReturn(Optional.of(flight));

        FlightNotFoundException exception = assertThrows(FlightNotFoundException.class, () -> {
            boughtTicketService.buyTicket(userEmail, flightNumber, flightDateString);
        });

        assertEquals("Flight with number FL123 not available on date " + flightDate, exception.getMessage());
    }

    @Test
    void testCancelTicketSuccess() {
        String ticketId = "ticket123";
        BoughtTicket ticket = new BoughtTicket();
        ticket.setId(ticketId);

        when(boughtTicketRepository.findById(ticketId)).thenReturn(Optional.of(ticket));
        doNothing().when(boughtTicketRepository).delete(ticket);

        boolean result = boughtTicketService.cancelTicket(ticketId);

        assertTrue(result);
        verify(boughtTicketRepository, times(1)).findById(ticketId);
        verify(boughtTicketRepository, times(1)).delete(ticket);
    }

    @Test
    void testCancelTicketNotFound() {
        String ticketId = "ticket123";

        when(boughtTicketRepository.findById(ticketId)).thenReturn(Optional.empty());

        boolean result = boughtTicketService.cancelTicket(ticketId);

        assertFalse(result);
        verify(boughtTicketRepository, times(1)).findById(ticketId);
        verify(boughtTicketRepository, times(0)).delete(any(BoughtTicket.class));
    }
}
