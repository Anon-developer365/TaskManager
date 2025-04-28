package org.example.taskmanager.controllers;

import org.example.taskmanager.exceptions.EmptyTaskException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UpdateStatusValidationTests {

    private final UpdateStatusValidation validation = new UpdateStatusValidation();

    private String status;
    private String id;

    @Test
    void whenStatusIsEmptyAnErrorIsReturned() {
        status = "";
        id = "1";
        List<String> expectedErrors = new ArrayList<>();
        expectedErrors.add("Task status is empty");

        EmptyTaskException thrown = assertThrows(EmptyTaskException.class, () -> validation.verifyStatus(id, status));
        assertEquals(expectedErrors.toString(), thrown.getMessage());

    }

    @Test
    void whenStatusDoesntMatchTheCorrectFormatAnErrorIsReturned() {
        status = "||";
        id = "1";
        List<String> expectedErrors = new ArrayList<>();
        expectedErrors.add("Task status doesnt match pattern a-zA-Z0-9");

        EmptyTaskException thrown = assertThrows(EmptyTaskException.class, () -> validation.verifyStatus(id, status));
        assertEquals(expectedErrors.toString(), thrown.getMessage());

    }

}
