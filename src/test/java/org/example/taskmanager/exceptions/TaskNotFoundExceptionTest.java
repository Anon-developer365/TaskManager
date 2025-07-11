package org.example.taskmanager.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskNotFoundExceptionTest {

    @Test
    void testCustomerVerificationNoRetryExceptionMessage() {
        String errorMessage = "This is an error message";
        TaskNotFoundException taskNotFoundException = new TaskNotFoundException(
                errorMessage);
        assertEquals(errorMessage, taskNotFoundException.getMessage());
    }
}
