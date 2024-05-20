package com.example.helloworld.ServiceTests;

import com.example.helloworld.TripNotFoundException;
import com.example.helloworld.controller.BookingController;
import com.example.helloworld.pojo.Booking;
import com.example.helloworld.pojo.Trip;
import com.example.helloworld.repository.BookingRepository;
import com.example.helloworld.repository.TripRepository;
import com.example.helloworld.service.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Date;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class BookingServiceTest {

    private MockMvc mockMvc;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private TripRepository tripRepository;

    @InjectMocks
    private BookingService bookingService;

    @InjectMocks
    private BookingController bookingController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bookingController).build();
    }

    @Test
    void testBookTrip() throws TripNotFoundException {
        String userEmail = "besartatefiki@gmail.com";
        String tripName = "Skiing Adventure";


        Trip trip = new Trip();
        trip.setName(tripName);
        trip.setLocation("Aspen, Colorado");
        trip.setDuration("5 days");
        trip.setCost(2000.0);
        trip.setStartDate(new Date());

        Booking newBooking = new Booking();
        newBooking.setUserEmail(userEmail);
        newBooking.setTripName(tripName);
        newBooking.setTripData(trip);

        when(tripRepository.findByName(tripName)).thenReturn(Optional.of(trip));
        when(bookingRepository.save(any(Booking.class))).thenReturn(newBooking);

        Booking result = bookingService.bookTrip(userEmail, tripName);

        assertNotNull(result);
        assertEquals(userEmail, result.getUserEmail());
        assertEquals(tripName, result.getTripName());
        assertEquals("Aspen, Colorado", result.getTripData().getLocation());
    }

    @Test
    void testBookTripNotFound() {
        String userEmail = "user@example.com";
        String tripName = "Nonexistent Trip";

        when(tripRepository.findByName(tripName)).thenReturn(Optional.empty());

        Exception exception = assertThrows(TripNotFoundException.class, () -> bookingService.bookTrip(userEmail, tripName));

        String expectedMessage = "Trip not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(bookingRepository, never()).save(any(Booking.class));
    }

    @Test
    void testCancelTrip() {
        String userEmail = "alice.smith@example.com";
        String tripName = "Adventure in the Wild";

        Booking booking = new Booking();
        booking.setUserEmail(userEmail);
        booking.setTripName(tripName);

        when(bookingRepository.findByTripNameAndUserEmail(tripName, userEmail)).thenReturn(Optional.of(booking));
        doNothing().when(bookingRepository).delete(booking);

        boolean result = bookingService.cancelTrip(tripName, userEmail);

        assertTrue(result);
    }

    @Test
    void testCancelTripNotFound() {
        String userEmail = "user@example.com";
        String tripName = "Nonexistent Trip";

        when(bookingRepository.findByTripNameAndUserEmail(tripName, userEmail)).thenReturn(Optional.empty());

        boolean result = bookingService.cancelTrip(tripName, userEmail);

        assertFalse(result);
    }
}
