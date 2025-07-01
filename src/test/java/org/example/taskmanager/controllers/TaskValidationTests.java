package org.example.taskmanager.controllers;

import org.example.taskmanager.exceptions.TaskValidationErrorException;
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
        dueDate = "2025-05-05 17:00";
        List<String> expectedErrors = new ArrayList<>();
        expectedErrors.add("Task title is empty");

        TaskValidationErrorException thrown = assertThrows(TaskValidationErrorException.class, () -> validation.verifyTask(title, description, status, dueDate));
        assertEquals(expectedErrors.toString(), thrown.getMessage());

    }

    @Test
    void whenATaskTitleIsInTheIncorrectFormatAnErrorIsReturned() {
        title = "||";
        description = "description";
        status = "open status";
        dueDate = "2025-05-05 17:00";
        List<String> expectedErrors = new ArrayList<>();
        expectedErrors.add("Task title does not match the pattern a-zA-Z0-9");

        TaskValidationErrorException thrown = assertThrows(TaskValidationErrorException.class, () -> validation.verifyTask(title, description, status, dueDate));
        assertEquals(expectedErrors.toString(), thrown.getMessage());

    }

    @Test
    void whenATaskTitleContainsNumbersNoErrorIsReturned() {
        title = "case 23";
        description = "description";
        status = "open status";
        dueDate = "2025-05-05 17:00";

        assertDoesNotThrow(() -> validation.verifyTask(title, description, status, dueDate));

    }


    @Test
    void whenDescriptionDoesntMatchTheFormatAnErrorIsReturned() {
        title = "Task title";
        description = "||";
        status = "open status";
        dueDate = "2025-05-05 17:00";
        List<String> expectedErrors = new ArrayList<>();
        expectedErrors.add("Task description does not match the pattern a-zA-Z0-9");

        TaskValidationErrorException thrown = assertThrows(TaskValidationErrorException.class, () -> validation.verifyTask(title, description, status, dueDate));
        assertEquals(expectedErrors.toString(), thrown.getMessage());

    }

    @Test
    void whenADescriptionIsEmptyNoErrorIsReturned() {
        title = "Task 23";
        description = "";
        status = "open status";
        dueDate = "2025-05-05 17:00";

        assertDoesNotThrow(() -> validation.verifyTask(title, description, status, dueDate));

    }

    @Test
    void whenADescriptionContainsNumbersNoErrorIsReturned() {
        title = "Task 23";
        description = "Awaiting 3 new parts for hard drive";
        status = "open status";
        dueDate = "2025-05-05 17:00";

        assertDoesNotThrow(() -> validation.verifyTask(title, description, status, dueDate));

    }

    @Test
    void whenStatusIsEmptyAnErrorIsReturned() {
        title = "Task 23";
        description = "Awaiting 3 new parts for hard drive";
        status = "";
        dueDate = "2025-05-05 17:00";
        List<String> expectedErrors = new ArrayList<>();
        expectedErrors.add("Task status is empty");

        TaskValidationErrorException thrown = assertThrows(TaskValidationErrorException.class, () -> validation.verifyTask(title, description, status, dueDate));
        assertEquals(expectedErrors.toString(), thrown.getMessage());

    }

    @Test
    void whenStatusDoesntMatchTheCorrectFormatAnErrorIsReturned() {
        title = "Task 23";
        description = "Awaiting 3 new parts for hard drive";
        status = "||";
        dueDate = "2025-05-05 17:00";
        List<String> expectedErrors = new ArrayList<>();
        expectedErrors.add("Task status does not match the pattern a-zA-Z0-9");

        TaskValidationErrorException thrown = assertThrows(TaskValidationErrorException.class, () -> validation.verifyTask(title, description, status, dueDate));
        assertEquals(expectedErrors.toString(), thrown.getMessage());

    }

    @Test
    void whenDueDateIsEmptyAnErrorIsReturned() {
        title = "Task 23";
        description = "Awaiting 3 new parts for hard drive";
        status = "open status";
        dueDate = "";
        List<String> expectedErrors = new ArrayList<>();
        expectedErrors.add("Task due date is empty");

        TaskValidationErrorException thrown = assertThrows(TaskValidationErrorException.class, () -> validation.verifyTask(title, description, status, dueDate));
        assertEquals(expectedErrors.toString(), thrown.getMessage());

    }

    @Test
    void whenDueDateInTheIncorrectFormatAnErrorIsReturned() {
        title = "Task 23";
        description = "Awaiting 3 new parts for hard drive";
        status = "open status";
        dueDate = "2025-05-05";
        List<String> expectedErrors = new ArrayList<>();
        expectedErrors.add("Task due date does not match the pattern yyyy-dd-mm hh:mm:ss");

        TaskValidationErrorException thrown = assertThrows(TaskValidationErrorException.class, () -> validation.verifyTask(title, description, status, dueDate));
        assertEquals(expectedErrors.toString(), thrown.getMessage());

    }

    @Test
    void whenThereIsMoreThanOneErrorAllErrorsAreReturned() {
        title = "";
        description = "||";
        status = "open status";
        dueDate = "2025-05-05";
        List<String> expectedErrors = new ArrayList<>();
        expectedErrors.add("Task title is empty");
        expectedErrors.add("Task description does not match the pattern a-zA-Z0-9");
        expectedErrors.add("Task due date does not match the pattern yyyy-dd-mm hh:mm:ss");

        TaskValidationErrorException thrown = assertThrows( TaskValidationErrorException.class, () -> validation.verifyTask(title, description, status, dueDate));
        assertEquals(expectedErrors.toString(), thrown.getMessage());

    }

}
