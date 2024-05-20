package com.example.helloworld.ServiceTests;

import com.example.helloworld.pojo.TripFeedback;
import com.example.helloworld.repository.TripFeedbackRepository;
import com.example.helloworld.service.TripFeedbackService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TripFeedbackServiceTest {

    @Mock
    private TripFeedbackRepository tripFeedbackRepository;

    @InjectMocks
    private TripFeedbackService tripFeedbackService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddTripFeedbackSuccess() {
        TripFeedback newFeedback = new TripFeedback();
        newFeedback.setComment("Great trip!");

        when(tripFeedbackRepository.save(any(TripFeedback.class))).thenReturn(newFeedback);

        TripFeedback result = tripFeedbackService.addTripFeedback(newFeedback);

        assertNotNull(result);
        assertNotNull(result.getReviewDate());
        assertEquals("Great trip!", result.getComment());
        verify(tripFeedbackRepository, times(1)).save(any(TripFeedback.class));
    }

    @Test
    void testAddTripFeedbackFailure() {
        TripFeedback newFeedback = new TripFeedback();
        newFeedback.setComment("Great trip!");

        when(tripFeedbackRepository.save(any(TripFeedback.class))).thenThrow(new RuntimeException("Failed to save trip feedback"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            tripFeedbackService.addTripFeedback(newFeedback);
        });

        assertEquals("Failed to save trip feedback", exception.getMessage());
        verify(tripFeedbackRepository, times(1)).save(any(TripFeedback.class));
    }

    @Test
    void testDeleteTripFeedbackSuccess() {
        String feedbackId = "feedback123";

        when(tripFeedbackRepository.existsById(feedbackId)).thenReturn(true);
        doNothing().when(tripFeedbackRepository).deleteById(feedbackId);

        boolean result = tripFeedbackService.deleteTripFeedback(feedbackId);

        assertTrue(result);
        verify(tripFeedbackRepository, times(1)).existsById(feedbackId);
        verify(tripFeedbackRepository, times(1)).deleteById(feedbackId);
    }

    @Test
    void testDeleteTripFeedbackNotFound() {
        String feedbackId = "feedback123";

        when(tripFeedbackRepository.existsById(feedbackId)).thenReturn(false);

        boolean result = tripFeedbackService.deleteTripFeedback(feedbackId);

        assertFalse(result);
        verify(tripFeedbackRepository, times(1)).existsById(feedbackId);
        verify(tripFeedbackRepository, times(0)).deleteById(anyString());
    }

    @Test
    void testDeleteTripFeedbackFailure() {
        String feedbackId = "feedback123";

        when(tripFeedbackRepository.existsById(feedbackId)).thenReturn(true);
        doThrow(new RuntimeException("Failed to delete trip feedback")).when(tripFeedbackRepository).deleteById(feedbackId);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            tripFeedbackService.deleteTripFeedback(feedbackId);
        });

        assertEquals("Failed to delete trip feedback", exception.getMessage());
        verify(tripFeedbackRepository, times(1)).existsById(feedbackId);
        verify(tripFeedbackRepository, times(1)).deleteById(feedbackId);
    }
}
