package org.example.taskmanager.controllers;

import org.example.taskmanager.exceptions.EmptyTaskException;
import org.example.taskmanager.pojo.Task;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TaskValidationTests {

    private final TaskValidation validation = new TaskValidation();
    LocalDateTime localDateTime = LocalDateTime.now();

    @Test
    void whenATaskIDIsEmptyAnErrorIsReturned() {
        Task task = new Task("", "case title", "description", "open status", localDateTime);
        List<String> expectedErrors = new ArrayList<>();
        expectedErrors.add("Task ID is empty");

        EmptyTaskException thrown = assertThrows(EmptyTaskException.class, () -> {
            validation.verifyTask(task);
        });
        assertEquals(expectedErrors.toString(), thrown.getMessage());

    }

    @Test
    void whenATaskTitleIsEmptyAnErrorIsReturned() {
        Task task = new Task("1", "", "description", "open status", localDateTime);
        List<String> expectedErrors = new ArrayList<>();
        expectedErrors.add("Task title is empty");

        EmptyTaskException thrown = assertThrows(EmptyTaskException.class, () -> {
            validation.verifyTask(task);
        });
        assertEquals(expectedErrors.toString(), thrown.getMessage());

    }

    @Test
    void whenATaskTitleIsInTheIncorrectFormatAnErrorIsReturned() {
        Task task = new Task("1", "||", "description", "open status", localDateTime);
        List<String> expectedErrors = new ArrayList<>();
        expectedErrors.add("Task title doesnt match pattern a-zA-Z0-9");

        EmptyTaskException thrown = assertThrows(EmptyTaskException.class, () -> {
            validation.verifyTask(task);
        });
        assertEquals(expectedErrors.toString(), thrown.getMessage());

    }
}
