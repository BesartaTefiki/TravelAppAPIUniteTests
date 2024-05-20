package com.example.helloworld.ControllerTests;

import com.example.helloworld.controller.TripController;
import com.example.helloworld.pojo.Trip;
import com.example.helloworld.service.TripService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TripController.class)
public class TripControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TripService tripService;

    private ObjectMapper objectMapper;
    private SimpleDateFormat dateFormat;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    @Test
    public void createTripSuccess() throws Exception {
        Trip trip = new Trip("Trip1", "Location1", "7 days", 500.0, dateFormat.parse("2023-05-10T00:00:00.000Z"));
        when(tripService.createTrip(Mockito.any(Trip.class))).thenReturn(trip);

        mockMvc.perform(post("/trip/createTrip")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(trip)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Trip1"))
                .andExpect(jsonPath("$.location").value("Location1"))
                .andExpect(jsonPath("$.duration").value("7 days"))
                .andExpect(jsonPath("$.cost").value(500.0))
                .andExpect(jsonPath("$.startDate").value("2023-05-10T00:00:00.000+00:00"));

        verify(tripService, times(1)).createTrip(Mockito.any(Trip.class));
    }

    @Test
    public void deleteTripSuccess() throws Exception {
        String tripData = new JSONObject().put("name", "Trip1").toString();
        when(tripService.deleteByName("Trip1")).thenReturn(Optional.of(true));

        mockMvc.perform(delete("/trip/deleteTrip")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tripData))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        verify(tripService, times(1)).deleteByName("Trip1");
    }

    @Test
    public void deleteTripNotFound() throws Exception {
        String tripData = new JSONObject().put("name", "Trip1").toString();
        when(tripService.deleteByName("Trip1")).thenReturn(Optional.of(false));

        mockMvc.perform(delete("/trip/deleteTrip")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tripData))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));

        verify(tripService, times(1)).deleteByName("Trip1");
    }

    @Test
    public void deleteTripServerError() throws Exception {
        String tripData = new JSONObject().put("name", "Trip1").toString();
        when(tripService.deleteByName("Trip1")).thenReturn(Optional.empty());

        mockMvc.perform(delete("/trip/deleteTrip")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tripData))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Trip not found"));

        verify(tripService, times(1)).deleteByName("Trip1");
    }

    @Test
    public void updateTripSuccess() throws Exception {
        Trip trip = new Trip("Trip1", "Location1", "7 days", 500.0, dateFormat.parse("2023-05-10T00:00:00.000Z"));
        String tripData = new JSONObject()
                .put("name", "Trip1")
                .put("location", "Location1")
                .put("duration", "7 days")
                .put("cost", 500.0)
                .put("startDate", "2023-05-10T00:00:00.000Z")
                .toString();

        when(tripService.updateByName(eq("Trip1"), Mockito.any(Trip.class))).thenReturn(Optional.of(trip));

        mockMvc.perform(post("/trip/updateTrip")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tripData))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Trip1"))
                .andExpect(jsonPath("$.location").value("Location1"))
                .andExpect(jsonPath("$.duration").value("7 days"))
                .andExpect(jsonPath("$.cost").value(500.0))
                .andExpect(jsonPath("$.startDate").value("2023-05-10T00:00:00.000+00:00"));

        verify(tripService, times(1)).updateByName(eq("Trip1"), Mockito.any(Trip.class));
    }

    @Test
    public void updateTripNotFound() throws Exception {
        String tripData = new JSONObject()
                .put("name", "Trip1")
                .put("location", "Location1")
                .put("duration", "7 days")
                .put("cost", 500.0)
                .put("startDate", "2023-05-10T00:00:00.000Z")
                .toString();

        when(tripService.updateByName(eq("Trip1"), Mockito.any(Trip.class))).thenReturn(Optional.empty());

        mockMvc.perform(post("/trip/updateTrip")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tripData))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Trip not found"));

        verify(tripService, times(1)).updateByName(eq("Trip1"), Mockito.any(Trip.class));
    }

    @Test
    public void updateTripInvalidDate() throws Exception {
        String tripData = new JSONObject()
                .put("name", "Trip1")
                .put("location", "Location1")
                .put("duration", "7 days")
                .put("cost", 500.0)
                .put("startDate", "invalid-date")
                .toString();

        mockMvc.perform(post("/trip/updateTrip")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tripData))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid date format"));
    }

    @Test
    public void findTripSuccess() throws Exception {
        Trip trip = new Trip("Trip1", "Location1", "7 days", 500.0, dateFormat.parse("2023-05-10T00:00:00.000Z"));
        when(tripService.findByName("Trip1")).thenReturn(Optional.of(trip));

        mockMvc.perform(post("/trip/findTrip")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Collections.singletonMap("name", "Trip1"))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Trip1"))
                .andExpect(jsonPath("$.location").value("Location1"))
                .andExpect(jsonPath("$.duration").value("7 days"))
                .andExpect(jsonPath("$.cost").value(500.0))
                .andExpect(jsonPath("$.startDate").value("2023-05-10T00:00:00.000+00:00"));

        verify(tripService, times(1)).findByName("Trip1");
    }

    @Test
    public void findTripNotFound() throws Exception {
        when(tripService.findByName("Trip1")).thenReturn(Optional.empty());

        mockMvc.perform(post("/trip/findTrip")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Collections.singletonMap("name", "Trip1"))))
                .andExpect(status().isNotFound());

        verify(tripService, times(1)).findByName("Trip1");
    }

    @Test
    public void findAllTripsSuccess() throws Exception {
        Trip trip1 = new Trip("Trip1", "Location1", "7 days", 500.0, dateFormat.parse("2023-05-10T00:00:00.000Z"));
        Trip trip2 = new Trip("Trip2", "Location2", "10 days", 700.0, dateFormat.parse("2023-06-15T00:00:00.000Z"));
        List<Trip> trips = Arrays.asList(trip1, trip2);

        when(tripService.findAllTrips()).thenReturn(trips);

        mockMvc.perform(get("/trip/findAllTrips"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Trip1"))
                .andExpect(jsonPath("$[0].location").value("Location1"))
                .andExpect(jsonPath("$[0].duration").value("7 days"))
                .andExpect(jsonPath("$[0].cost").value(500.0))
                .andExpect(jsonPath("$[0].startDate").value("2023-05-10T00:00:00.000+00:00"))
                .andExpect(jsonPath("$[1].name").value("Trip2"))
                .andExpect(jsonPath("$[1].location").value("Location2"))
                .andExpect(jsonPath("$[1].duration").value("10 days"))
                .andExpect(jsonPath("$[1].cost").value(700.0))
                .andExpect(jsonPath("$[1].startDate").value("2023-06-15T00:00:00.000+00:00"));

        verify(tripService, times(1)).findAllTrips();
    }

    @Test
    public void findAllTripsNotFound() throws Exception {
        when(tripService.findAllTrips()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/trip/findAllTrips"))
                .andExpect(status().isNotFound());

        verify(tripService, times(1)).findAllTrips();
    }
}
