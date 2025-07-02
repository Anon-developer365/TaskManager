package org.example.taskmanager.validation;

import org.example.taskmanager.validation.StatusValidation;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StatusValidationTests {

    private final StatusValidation validation = new StatusValidation();

    private String status;

    @Test
    void whenStatusIsEmptyAnErrorIsReturned() {
        status = "";
        List<String> expectedErrors = new ArrayList<>();
        expectedErrors.add("Task status is empty");

        List<String> actualOutput = validation.statusCheck(status);
        assertEquals(expectedErrors.toString(), actualOutput.toString());

    }

    @Test
    void whenStatusDoesNotMatchTheCorrectFormatAnErrorIsReturned() {
        status = "||";
        List<String> expectedErrors = new ArrayList<>();
        expectedErrors.add("Task status does not match the pattern a-zA-Z0-9");

        List<String> actualOutput = validation.statusCheck(status);
        assertEquals(expectedErrors.toString(), actualOutput.toString());

    }

    @Test
    void whenStatusIsValidNoErrorIsThrown() {
        status = "status";
        List<String> expectedErrors = new ArrayList<>();


        List<String> actualOutput = validation.statusCheck(status);
        assertEquals(expectedErrors.toString(), actualOutput.toString());

    }
}
