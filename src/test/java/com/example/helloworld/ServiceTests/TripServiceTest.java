package com.example.helloworld.ServiceTests;

import com.example.helloworld.pojo.Trip;
import com.example.helloworld.repository.TripRepository;
import com.example.helloworld.service.TripService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TripServiceTest {

    @Mock
    private TripRepository tripRepository;

    @InjectMocks
    private TripService tripService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTrip() {
        Trip newTrip = new Trip();
        newTrip.setName("Exciting Trip");

        when(tripRepository.save(any(Trip.class))).thenReturn(newTrip);

        Trip result = tripService.createTrip(newTrip);

        assertNotNull(result);
        assertEquals("Exciting Trip", result.getName());
        verify(tripRepository, times(1)).save(any(Trip.class));
    }

    @Test
    void testFindAllTrips() {
        when(tripRepository.findAll()).thenReturn(Arrays.asList(new Trip(), new Trip()));

        List<Trip> trips = tripService.findAllTrips();

        assertNotNull(trips);
        assertEquals(2, trips.size());
        verify(tripRepository, times(1)).findAll();
    }

    @Test
    void testFindAllTripsEmpty() {
        when(tripRepository.findAll()).thenReturn(Collections.emptyList());

        List<Trip> trips = tripService.findAllTrips();

        assertNotNull(trips);
        assertTrue(trips.isEmpty());
        verify(tripRepository, times(1)).findAll();
    }

    @Test
    void testFindByName() {
        Trip trip = new Trip();
        trip.setName("Holiday Trip");

        when(tripRepository.findByName("Holiday Trip")).thenReturn(Optional.of(trip));

        Optional<Trip> result = tripService.findByName("Holiday Trip");

        assertTrue(result.isPresent());
        assertEquals("Holiday Trip", result.get().getName());
        verify(tripRepository, times(1)).findByName("Holiday Trip");
    }

    @Test
    void testFindByNameNotFound() {
        when(tripRepository.findByName("Holiday Trip")).thenReturn(Optional.empty());

        Optional<Trip> result = tripService.findByName("Holiday Trip");

        assertFalse(result.isPresent());
        verify(tripRepository, times(1)).findByName("Holiday Trip");
    }

    @Test
    void testDeleteByName() {
        Trip trip = new Trip();
        trip.setName("Delete Trip");

        when(tripRepository.findByName("Delete Trip")).thenReturn(Optional.of(trip));
        doNothing().when(tripRepository).delete(any(Trip.class));

        Optional<Boolean> result = tripService.deleteByName("Delete Trip");

        assertTrue(result.isPresent());
        assertTrue(result.get());
        verify(tripRepository, times(1)).findByName("Delete Trip");
        verify(tripRepository, times(1)).delete(any(Trip.class));
    }

    @Test
    void testDeleteByNameNotFound() {
        when(tripRepository.findByName("Delete Trip")).thenReturn(Optional.empty());

        Optional<Boolean> result = tripService.deleteByName("Delete Trip");

        assertFalse(result.isPresent());
        verify(tripRepository, times(1)).findByName("Delete Trip");
        verify(tripRepository, times(0)).delete(any(Trip.class));
    }

    @Test
    void testUpdateByName() {
        Trip existingTrip = new Trip();
        existingTrip.setName("Old Trip");

        Trip updatedTrip = new Trip();
        updatedTrip.setName("Updated Trip");
        updatedTrip.setLocation("New Location");
        updatedTrip.setDuration("10 days");
        updatedTrip.setCost(500.0);
        updatedTrip.setStartDate(new java.util.Date());

        when(tripRepository.findByName("Old Trip")).thenReturn(Optional.of(existingTrip));
        when(tripRepository.save(any(Trip.class))).thenReturn(updatedTrip);

        Optional<Trip> result = tripService.updateByName("Old Trip", updatedTrip);

        assertTrue(result.isPresent());
        assertEquals("Updated Trip", result.get().getName());
        assertEquals("New Location", result.get().getLocation());
        assertEquals("10 days", result.get().getDuration());
        assertEquals(500.0, result.get().getCost());
        assertEquals(updatedTrip.getStartDate(), result.get().getStartDate());
        verify(tripRepository, times(1)).findByName("Old Trip");
        verify(tripRepository, times(1)).save(any(Trip.class));
    }

    @Test
    void testUpdateByNameNotFound() {
        Trip updatedTrip = new Trip();
        updatedTrip.setName("Updated Trip");

        when(tripRepository.findByName("Old Trip")).thenReturn(Optional.empty());

        Optional<Trip> result = tripService.updateByName("Old Trip", updatedTrip);

        assertFalse(result.isPresent());
        verify(tripRepository, times(1)).findByName("Old Trip");
        verify(tripRepository, times(0)).save(any(Trip.class));
    }
}
