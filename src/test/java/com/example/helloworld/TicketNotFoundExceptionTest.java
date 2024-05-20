package com.example.helloworld;

import com.example.helloworld.TicketNotFoundException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TicketNotFoundExceptionTest {

    @Test
    void testExceptionMessage() {
        String errorMessage = "Ticket not found";
        TicketNotFoundException exception = new TicketNotFoundException(errorMessage);

        // Verify that the message is correctly set in the exception
        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void testExceptionIsInstanceOfException() {
        TicketNotFoundException exception = new TicketNotFoundException("Ticket not found");

        // Verify that the TicketNotFoundException is an instance of Exception
        assertTrue(exception instanceof Exception);
    }
}
