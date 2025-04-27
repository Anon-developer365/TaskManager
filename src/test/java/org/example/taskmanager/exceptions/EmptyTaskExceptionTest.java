package org.example.taskmanager.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmptyTaskExceptionTest {

    @Test
    void testCustomerVerificationNoRetryExceptionMessage() {
        String errorMessage = "This is an error message";
        EmptyTaskException emptyTaskException = new EmptyTaskException(
                errorMessage);
        assertEquals(errorMessage, emptyTaskException.getMessage());
    }

}
