package org.example.taskmanager.validation;

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
        expectedErrors.add("Task status is blank");

        List<String> actualOutput = validation.statusCheck(status);
        assertEquals(expectedErrors, actualOutput);

    }

    @Test
    void whenStatusDoesNotMatchTheCorrectFormatAnErrorIsReturned() {
        status = "||";
        List<String> expectedErrors = new ArrayList<>();
        expectedErrors.add("Task status does not match the pattern a-zA-Z0-9");

        List<String> actualOutput = validation.statusCheck(status);
        assertEquals(expectedErrors, actualOutput);

    }

    @Test
    void whenStatusIsValidNoErrorIsThrown() {
        List<String> expectedErrors = new ArrayList<>();
        status = "status";

        List<String> actualOutput = validation.statusCheck(status);
        assertEquals(expectedErrors, actualOutput);

    }

}
