package org.example.taskmanager.validation;

import org.example.taskmanager.exceptions.TaskValidationErrorException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StatusValidationTests {

    private final StatusValidation validation = new StatusValidation();

    private String status;

    @Test
    void whenStatusIsEmptyAnErrorIsReturned() {
        status = "";
        List<String> expectedErrors = new ArrayList<>();
        expectedErrors.add("Task status is empty");

        TaskValidationErrorException thrown = assertThrows(TaskValidationErrorException.class, () -> validation.statusCheck(status));
        assertEquals(expectedErrors.toString(), thrown.getMessage());

    }

    @Test
    void whenStatusDoesNotMatchTheCorrectFormatAnErrorIsReturned() {
        status = "||";
        List<String> expectedErrors = new ArrayList<>();
        expectedErrors.add("Task status does not match the pattern a-zA-Z0-9");

        TaskValidationErrorException thrown = assertThrows(TaskValidationErrorException.class, () -> validation.statusCheck(status));
        assertEquals(expectedErrors.toString(), thrown.getMessage());

    }

    @Test
    void whenStatusIsValidNoErrorIsThrown() {
        status = "status";

        assertDoesNotThrow(() -> validation.statusCheck(status));

    }
}
