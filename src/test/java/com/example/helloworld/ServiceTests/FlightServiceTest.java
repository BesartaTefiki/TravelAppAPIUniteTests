package com.example.helloworld.ServiceTests;

import com.example.helloworld.pojo.Flight;
import com.example.helloworld.repository.FlightRepository;
import com.example.helloworld.service.FlightService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class FlightServiceTest {

    @Mock
    private FlightRepository flightRepository;

    @InjectMocks
    private FlightService flightService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateFlight() {
        Flight newFlight = new Flight();
        newFlight.setFlightNumber("FL123");

        when(flightRepository.save(any(Flight.class))).thenReturn(newFlight);

        Flight result = flightService.createFlight(newFlight);

        assertNotNull(result);
        assertEquals("FL123", result.getFlightNumber());
        verify(flightRepository, times(1)).save(any(Flight.class));
    }

    @Test
    void testFindAllFlights() {
        when(flightRepository.findAll()).thenReturn(Arrays.asList(new Flight(), new Flight()));

        List<Flight> flights = flightService.findAllFlights();

        assertNotNull(flights);
        assertEquals(2, flights.size());
        verify(flightRepository, times(1)).findAll();
    }

    @Test
    void testFindAllFlightsEmpty() {
        when(flightRepository.findAll()).thenReturn(Arrays.asList());

        List<Flight> flights = flightService.findAllFlights();

        assertNotNull(flights);
        assertTrue(flights.isEmpty());
        verify(flightRepository, times(1)).findAll();
    }

    @Test
    void testFindByFlightNumber() {
        Flight flight = new Flight();
        flight.setFlightNumber("FL123");

        when(flightRepository.findByFlightNumber("FL123")).thenReturn(Optional.of(flight));

        Optional<Flight> result = flightService.findByFlightNumber("FL123");

        assertTrue(result.isPresent());
        assertEquals("FL123", result.get().getFlightNumber());
        verify(flightRepository, times(1)).findByFlightNumber("FL123");
    }

    @Test
    void testFindByFlightNumberNotFound() {
        when(flightRepository.findByFlightNumber("FL123")).thenReturn(Optional.empty());

        Optional<Flight> result = flightService.findByFlightNumber("FL123");

        assertFalse(result.isPresent());
        verify(flightRepository, times(1)).findByFlightNumber("FL123");
    }

    @Test
    void testDeleteByFlightNumber() {
        String flightNumber = "FL123";
        Flight flight = new Flight();
        flight.setFlightNumber(flightNumber);

        when(flightRepository.findByFlightNumber(flightNumber)).thenReturn(Optional.of(flight));
        doNothing().when(flightRepository).delete(flight);

        Optional<Boolean> result = flightService.deleteByFlightNumber(flightNumber);

        assertTrue(result.isPresent());
        assertTrue(result.get());
        verify(flightRepository, times(1)).findByFlightNumber(flightNumber);
        verify(flightRepository, times(1)).delete(flight);
    }

    @Test
    void testDeleteByFlightNumberNotFound() {
        String flightNumber = "FL123";

        when(flightRepository.findByFlightNumber(flightNumber)).thenReturn(Optional.empty());

        Optional<Boolean> result = flightService.deleteByFlightNumber(flightNumber);

        assertFalse(result.isPresent());
        verify(flightRepository, times(1)).findByFlightNumber(flightNumber);
        verify(flightRepository, times(0)).delete(any(Flight.class));
    }

    @Test
    void testUpdateByFlightNumber() {
        String flightNumber = "FL123";
        Flight existingFlight = new Flight();
        Flight updatedFlight = new Flight();
        updatedFlight.setFlightNumber(flightNumber);
        updatedFlight.setDestinationAirport("New Airport");

        when(flightRepository.findByFlightNumber(flightNumber)).thenReturn(Optional.of(existingFlight));
        when(flightRepository.save(any(Flight.class))).thenReturn(updatedFlight);

        Optional<Flight> result = flightService.updateByFlightNumber(flightNumber, updatedFlight);

        assertTrue(result.isPresent());
        assertEquals("New Airport", result.get().getDestinationAirport());
        verify(flightRepository, times(1)).findByFlightNumber(flightNumber);
        verify(flightRepository, times(1)).save(any(Flight.class));
    }

    @Test
    void testUpdateByFlightNumberNotFound() {
        String flightNumber = "FL123";
        Flight updatedFlight = new Flight();
        updatedFlight.setFlightNumber(flightNumber);

        when(flightRepository.findByFlightNumber(flightNumber)).thenReturn(Optional.empty());

        Optional<Flight> result = flightService.updateByFlightNumber(flightNumber, updatedFlight);

        assertFalse(result.isPresent());
        verify(flightRepository, times(1)).findByFlightNumber(flightNumber);
        verify(flightRepository, times(0)).save(any(Flight.class));
    }
}
