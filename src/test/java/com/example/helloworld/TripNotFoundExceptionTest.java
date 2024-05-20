package com.example.helloworld;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class TripNotFoundExceptionTests {

    @Test
    void testExceptionMessage() {
        String errorMessage = "Trip not found";
        TripNotFoundException exception = new TripNotFoundException(errorMessage);

        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void testThrowingException() {
        String errorMessage = "Trip not found";

        Exception exception = assertThrows(TripNotFoundException.class, () -> {
            throw new TripNotFoundException(errorMessage);
        });

        assertEquals(errorMessage, exception.getMessage());
    }
}
