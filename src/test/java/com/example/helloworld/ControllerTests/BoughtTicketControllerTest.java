package com.example.helloworld.ControllerTests;

import com.example.helloworld.controller.BoughtTicketController;
import com.example.helloworld.pojo.BoughtTicket;
import com.example.helloworld.service.BoughtTicketService;
import com.example.helloworld.FlightNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BoughtTicketController.class)
public class BoughtTicketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BoughtTicketService boughtTicketService;

    private SimpleDateFormat dateFormat;

    @BeforeEach
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        // No need to initialize mockMvc here as it's already auto-configured by @WebMvcTest
    }

    @Test
    public void buyTicketSuccess() throws Exception {
        Date flightDate = dateFormat.parse("2023-05-10");
        BoughtTicket newTicket = new BoughtTicket("test@example.com", "FL123", flightDate);
        newTicket.setId("1");

        when(boughtTicketService.buyTicket("test@example.com", "FL123", "2023-05-10")).thenReturn(newTicket);

        mockMvc.perform(post("/tickets/buyTicket")
                        .param("userEmail", "test@example.com")
                        .param("flightNumber", "FL123")
                        .param("flightDateString", "2023-05-10"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.userEmail").value("test@example.com"))
                .andExpect(jsonPath("$.flightNumber").value("FL123"))
                .andExpect(jsonPath("$.flightDate").value("2023-05-10T00:00:00.000+00:00"));

        verify(boughtTicketService, times(1)).buyTicket("test@example.com", "FL123", "2023-05-10");
    }

    @Test
    public void buyTicketFlightNotFound() throws Exception {
        when(boughtTicketService.buyTicket("test@example.com", "FL123", "2023-05-10")).thenThrow(new FlightNotFoundException("Flight not found"));

        mockMvc.perform(post("/tickets/buyTicket")
                        .param("userEmail", "test@example.com")
                        .param("flightNumber", "FL123")
                        .param("flightDateString", "2023-05-10"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Flight not found"));

        verify(boughtTicketService, times(1)).buyTicket("test@example.com", "FL123", "2023-05-10");
    }

    @Test
    public void buyTicketParseException() throws Exception {
        when(boughtTicketService.buyTicket("test@example.com", "FL123", "invalid-date")).thenThrow(new ParseException("Invalid date format", 0));

        mockMvc.perform(post("/tickets/buyTicket")
                        .param("userEmail", "test@example.com")
                        .param("flightNumber", "FL123")
                        .param("flightDateString", "invalid-date"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid date format"));

        verify(boughtTicketService, times(1)).buyTicket("test@example.com", "FL123", "invalid-date");
    }

    @Test
    public void buyTicketServerError() throws Exception {
        when(boughtTicketService.buyTicket("test@example.com", "FL123", "2023-05-10")).thenThrow(new RuntimeException());

        mockMvc.perform(post("/tickets/buyTicket")
                        .param("userEmail", "test@example.com")
                        .param("flightNumber", "FL123")
                        .param("flightDateString", "2023-05-10"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("An error occurred while buying a ticket."));

        verify(boughtTicketService, times(1)).buyTicket("test@example.com", "FL123", "2023-05-10");
    }

    @Test
    public void cancelTicketSuccess() throws Exception {
        when(boughtTicketService.cancelTicket("1")).thenReturn(true);

        mockMvc.perform(post("/tickets/cancelTicket")
                        .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Ticket cancelled successfully"));

        verify(boughtTicketService, times(1)).cancelTicket("1");
    }

    @Test
    public void cancelTicketFailure() throws Exception {
        when(boughtTicketService.cancelTicket("1")).thenReturn(false);

        mockMvc.perform(post("/tickets/cancelTicket")
                        .param("id", "1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Ticket cancellation failed"));

        verify(boughtTicketService, times(1)).cancelTicket("1");
    }
}
