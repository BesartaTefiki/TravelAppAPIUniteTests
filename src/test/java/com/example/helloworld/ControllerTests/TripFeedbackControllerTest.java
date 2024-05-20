package com.example.helloworld.ControllerTests;

import com.example.helloworld.controller.TripFeedbackController;
import com.example.helloworld.pojo.TripFeedback;
import com.example.helloworld.service.TripFeedbackService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Date;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TripFeedbackController.class)
public class TripFeedbackControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TripFeedbackService tripFeedbackService;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void addTripFeedbackSuccess() throws Exception {
        TripFeedback tripFeedback = new TripFeedback("1", 1L, 1L, 5, "Great trip!", new Date());
        when(tripFeedbackService.addTripFeedback(Mockito.any(TripFeedback.class))).thenReturn(tripFeedback);

        mockMvc.perform(post("/tripFeedback/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tripFeedback)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.tripId").value(1L))
                .andExpect(jsonPath("$.userId").value(1L))
                .andExpect(jsonPath("$.rating").value(5))
                .andExpect(jsonPath("$.comment").value("Great trip!"))
                .andExpect(jsonPath("$.reviewDate").exists());

        verify(tripFeedbackService, times(1)).addTripFeedback(Mockito.any(TripFeedback.class));
    }

    @Test
    public void addTripFeedbackServerError() throws Exception {
        TripFeedback tripFeedback = new TripFeedback("1", 1L, 1L, 5, "Great trip!", new Date());
        when(tripFeedbackService.addTripFeedback(Mockito.any(TripFeedback.class))).thenThrow(new RuntimeException());

        mockMvc.perform(post("/tripFeedback/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tripFeedback)))
                .andExpect(status().isInternalServerError());

        verify(tripFeedbackService, times(1)).addTripFeedback(Mockito.any(TripFeedback.class));
    }

    @Test
    public void deleteTripFeedbackSuccess() throws Exception {
        when(tripFeedbackService.deleteTripFeedback("1")).thenReturn(true);

        mockMvc.perform(delete("/tripFeedback/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Trip feedback deleted successfully"));

        verify(tripFeedbackService, times(1)).deleteTripFeedback("1");
    }

    @Test
    public void deleteTripFeedbackNotFound() throws Exception {
        when(tripFeedbackService.deleteTripFeedback("1")).thenReturn(false);

        mockMvc.perform(delete("/tripFeedback/delete/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Trip feedback not found"));

        verify(tripFeedbackService, times(1)).deleteTripFeedback("1");
    }
}
