package org.example.taskmanager.controllers;

import org.example.taskmanager.exceptions.EmptyTaskException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TaskValidationTests {

    private final TaskValidation validation = new TaskValidation();

    private String title;
    private String description;
    private String status;
    private String dueDate;

    @Test
    void whenATaskTitleIsEmptyAnErrorIsReturned() {
        title = "";
        description = "description";
        status = "open status";
        dueDate = "05-05-2025 17:00:00";
        List<String> expectedErrors = new ArrayList<>();
        expectedErrors.add("Task title is empty");

        EmptyTaskException thrown = assertThrows(EmptyTaskException.class, () -> validation.verifyTask(title, description, status, dueDate));
        assertEquals(expectedErrors.toString(), thrown.getMessage());

    }

    @Test
    void whenATaskTitleIsInTheIncorrectFormatAnErrorIsReturned() {
        title = "||";
        description = "description";
        status = "open status";
        dueDate = "05-05-2025 17:00:00";
        List<String> expectedErrors = new ArrayList<>();
        expectedErrors.add("Task title doesnt match pattern a-zA-Z0-9");

        EmptyTaskException thrown = assertThrows(EmptyTaskException.class, () -> validation.verifyTask(title, description, status, dueDate));
        assertEquals(expectedErrors.toString(), thrown.getMessage());

    }

    @Test
    void whenATaskTitleContainsNumbersNoErrorIsReturned() {
        title = "case 23";
        description = "description";
        status = "open status";
        dueDate = "05-05-2025 17:00:00";

        assertDoesNotThrow(() -> validation.verifyTask(title, description, status, dueDate));

    }


    @Test
    void whenDescriptionDoesntMatchTheFormatAnErrorIsReturned() {
        title = "Task title";
        description = "||";
        status = "open status";
        dueDate = "05-05-2025 17:00:00";
        List<String> expectedErrors = new ArrayList<>();
        expectedErrors.add("Task description doesnt match pattern a-zA-Z0-9");

        EmptyTaskException thrown = assertThrows(EmptyTaskException.class, () -> validation.verifyTask(title, description, status, dueDate));
        assertEquals(expectedErrors.toString(), thrown.getMessage());

    }

    @Test
    void whenADescriptionIsEmptyNoErrorIsReturned() {
        title = "Task 23";
        description = "";
        status = "open status";
        dueDate = "05-05-2025 17:00:00";

        assertDoesNotThrow(() -> validation.verifyTask(title, description, status, dueDate));

    }

    @Test
    void whenADescriptionContainsNumbersNoErrorIsReturned() {
        title = "Task 23";
        description = "Awaiting 3 new parts for hard drive";
        status = "open status";
        dueDate = "05-05-2025 17:00:00";

        assertDoesNotThrow(() -> validation.verifyTask(title, description, status, dueDate));

    }

    @Test
    void whenStatusIsEmptyAnErrorIsReturned() {
        title = "Task 23";
        description = "Awaiting 3 new parts for hard drive";
        status = "";
        dueDate = "05-05-2025 17:00:00";
        List<String> expectedErrors = new ArrayList<>();
        expectedErrors.add("Task status is empty");

        EmptyTaskException thrown = assertThrows(EmptyTaskException.class, () -> validation.verifyTask(title, description, status, dueDate));
        assertEquals(expectedErrors.toString(), thrown.getMessage());

    }

}
