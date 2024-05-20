package com.example.helloworld;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class FlightNotFoundExceptionTests {

    @Test
    void testExceptionMessage() {
        String errorMessage = "Flight not found";
        FlightNotFoundException exception = new FlightNotFoundException(errorMessage);

        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void testThrowingException() {
        String errorMessage = "Flight not found";

        Exception exception = assertThrows(FlightNotFoundException.class, () -> {
            throw new FlightNotFoundException(errorMessage);
        });

        assertEquals(errorMessage, exception.getMessage());
    }
}
