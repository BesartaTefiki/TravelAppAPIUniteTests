package com.example.helloworld.ControllerTests;

import com.example.helloworld.controller.BookingController;
import com.example.helloworld.pojo.Booking;
import com.example.helloworld.pojo.Trip;
import com.example.helloworld.service.BookingService;
import com.example.helloworld.TripNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookingController.class)
public class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingService bookingService;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new BookingController(bookingService)).build();
    }

    @Test
    public void bookTripSuccess() throws Exception {
        Trip trip = new Trip(); // Assuming Trip has a no-arg constructor
        Booking booking = new Booking("test@example.com", "Test Trip", trip);

        when(bookingService.bookTrip("test@example.com", "Test Trip")).thenReturn(booking);

        mockMvc.perform(post("/bookings/bookTrip")
                        .param("userEmail", "test@example.com")
                        .param("tripName", "Test Trip"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.userEmail").value("test@example.com"))
                .andExpect(jsonPath("$.tripName").value("Test Trip"))
                .andExpect(jsonPath("$.tripData").isNotEmpty());

        verify(bookingService, times(1)).bookTrip("test@example.com", "Test Trip");
    }

    @Test
    public void bookTripNotFound() throws Exception {
        when(bookingService.bookTrip("test@example.com", "Test Trip")).thenThrow(new TripNotFoundException("Trip not found"));

        mockMvc.perform(post("/bookings/bookTrip")
                        .param("userEmail", "test@example.com")
                        .param("tripName", "Test Trip"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Trip not found"));

        verify(bookingService, times(1)).bookTrip("test@example.com", "Test Trip");
    }

    @Test
    public void bookTripServerError() throws Exception {
        when(bookingService.bookTrip("test@example.com", "Test Trip")).thenThrow(new RuntimeException());

        mockMvc.perform(post("/bookings/bookTrip")
                        .param("userEmail", "test@example.com")
                        .param("tripName", "Test Trip"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("An error occurred while booking the trip."));

        verify(bookingService, times(1)).bookTrip("test@example.com", "Test Trip");
    }

    @Test
    public void cancelTripSuccess() throws Exception {
        when(bookingService.cancelTrip("Test Trip", "test@example.com")).thenReturn(true);

        mockMvc.perform(post("/bookings/cancelTrip")
                        .param("tripName", "Test Trip")
                        .param("userEmail", "test@example.com"))
                .andExpect(status().isOk())
                .andExpect(content().string("Booking cancelled successfully."));

        verify(bookingService, times(1)).cancelTrip("Test Trip", "test@example.com");
    }

    @Test
    public void cancelTripNotFound() throws Exception {
        when(bookingService.cancelTrip("Test Trip", "test@example.com")).thenReturn(false);

        mockMvc.perform(post("/bookings/cancelTrip")
                        .param("tripName", "Test Trip")
                        .param("userEmail", "test@example.com"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Booking not found."));

        verify(bookingService, times(1)).cancelTrip("Test Trip", "test@example.com");
    }

    @Test
    public void cancelTripServerError() throws Exception {
        when(bookingService.cancelTrip("Test Trip", "test@example.com")).thenThrow(new RuntimeException());

        mockMvc.perform(post("/bookings/cancelTrip")
                        .param("tripName", "Test Trip")
                        .param("userEmail", "test@example.com"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("An error occurred while cancelling the booking."));

        verify(bookingService, times(1)).cancelTrip("Test Trip", "test@example.com");
    }
}
