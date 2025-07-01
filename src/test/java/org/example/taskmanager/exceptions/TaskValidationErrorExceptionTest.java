package org.example.taskmanager.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskValidationErrorExceptionTest {

    @Test
    void testCustomerVerificationNoRetryExceptionMessage() {
        String errorMessage = "This is an error message";
        TaskValidationErrorException emptyTaskException = new TaskValidationErrorException(
                errorMessage);
        assertEquals(errorMessage, emptyTaskException.getMessage());
    }

}
