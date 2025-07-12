package org.example.taskmanager.validation;

import org.example.taskmanager.exceptions.TaskValidationErrorException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TaskValidationTests {
    @Mock
    private StatusValidation statusValidation;

    @InjectMocks
    private TaskValidation validation;

    private String title;
    private String description;
    private String status;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime dueDate;

    @Test
    void whenATaskTitleIsEmptyAnErrorIsReturned() {
        validation = new TaskValidation(statusValidation);
        title = "";
        description = "description";
        status = "open status";

        String stringDate = "2025-05-05 17:00";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.UK);
        dueDate = LocalDateTime.parse(stringDate, dateTimeFormatter);

        List<String> expectedErrors = new ArrayList<>();
        expectedErrors.add("Task title is empty");
        doNothing().when(statusValidation).statusCheck(status);

        TaskValidationErrorException thrown = assertThrows(TaskValidationErrorException.class, () -> validation.verifyTask(title, description, status, dueDate));
        assertEquals(expectedErrors.toString(), thrown.getMessage());

    }

    @Test
    void whenATaskTitleIsInTheIncorrectFormatAnErrorIsReturned() {
        title = "||";
        description = "description";
        status = "open status";

        String stringDate = "2025-05-05 17:00";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.UK);
        dueDate = LocalDateTime.parse(stringDate, dateTimeFormatter);

        List<String> expectedErrors = new ArrayList<>();
        expectedErrors.add("Task title does not match the pattern a-zA-Z0-9");
        doNothing().when(statusValidation).statusCheck(status);

        TaskValidationErrorException thrown = assertThrows(TaskValidationErrorException.class, () -> validation.verifyTask(title, description, status, dueDate));
        assertEquals(expectedErrors.toString(), thrown.getMessage());

    }

    @Test
    void whenATaskTitleContainsNumbersNoErrorIsReturned() {
        title = "case 23";
        description = "description";
        status = "open status";

        String stringDate = "2025-05-05 17:00";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.UK);
        dueDate = LocalDateTime.parse(stringDate, dateTimeFormatter);

        doNothing().when(statusValidation).statusCheck(status);

        assertDoesNotThrow(() -> validation.verifyTask(title, description, status, dueDate));

    }


    @Test
    void whenDescriptionDoesNotMatchTheFormatAnErrorIsReturned() {
        title = "Task title";
        description = "||";
        status = "open status";

        String stringDate = "2025-05-05 17:00";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.UK);
        dueDate = LocalDateTime.parse(stringDate, dateTimeFormatter);

        List<String> expectedErrors = new ArrayList<>();
        expectedErrors.add("Task description does not match the pattern a-zA-Z0-9");
        doNothing().when(statusValidation).statusCheck(status);

        TaskValidationErrorException thrown = assertThrows(TaskValidationErrorException.class, () -> validation.verifyTask(title, description, status, dueDate));
        assertEquals(expectedErrors.toString(), thrown.getMessage());

    }

    @Test
    void whenADescriptionIsEmptyNoErrorIsReturned() {
        title = "Task 23";
        description = "";
        status = "open status";

        String stringDate = "2025-05-05 17:00";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.UK);
        dueDate = LocalDateTime.parse(stringDate, dateTimeFormatter);

        doNothing().when(statusValidation).statusCheck(status);

        assertDoesNotThrow(() -> validation.verifyTask(title, description, status, dueDate));

    }

    @Test
    void whenADescriptionContainsNumbersNoErrorIsReturned() {
        title = "Task 23";
        description = "Awaiting 3 new parts for hard drive";
        status = "open status";

        String stringDate = "2025-05-05 17:00";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.UK);
        dueDate = LocalDateTime.parse(stringDate, dateTimeFormatter);

        doNothing().when(statusValidation).statusCheck(status);

        assertDoesNotThrow(() -> validation.verifyTask(title, description, status, dueDate));

    }

    @Test
    void whenStatusIsEmptyAnErrorIsReturned() {
        title = "Task 23";
        description = "Awaiting 3 new parts for hard drive";
        status = "";

        TaskValidationErrorException exception = new TaskValidationErrorException("Task status is empty");

        String stringDate = "2025-05-05 17:00";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.UK);
        dueDate = LocalDateTime.parse(stringDate, dateTimeFormatter);

        doThrow(exception).when(statusValidation).statusCheck(status);

        TaskValidationErrorException thrown = assertThrows(TaskValidationErrorException.class, () -> validation.verifyTask(title, description, status, dueDate));
        assertEquals("Task status is empty", thrown.getMessage());

    }

    @Test
    void whenStatusDoesNotMatchTheCorrectFormatAnErrorIsReturned() {
        title = "Task 23";
        description = "Awaiting 3 new parts for hard drive";
        status = "||";

        String stringDate = "2025-05-05 17:00";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.UK);
        dueDate = LocalDateTime.parse(stringDate, dateTimeFormatter);

        TaskValidationErrorException exception = new TaskValidationErrorException("Task status does not match the pattern a-zA-Z0-9");
        String expectedError = "Task status does not match the pattern a-zA-Z0-9";
        doThrow(exception).when(statusValidation).statusCheck(status);

        TaskValidationErrorException thrown = assertThrows(TaskValidationErrorException.class, () -> validation.verifyTask(title, description, status, dueDate));
        assertEquals(expectedError, thrown.getMessage());

    }

    @Test
    void whenDueDateIsEmptyAnErrorIsReturned() {
        title = "Task 23";
        description = "Awaiting 3 new parts for hard drive";
        status = "open status";
        List<String> expectedErrors = new ArrayList<>();
        expectedErrors.add("Task due date is empty");

        doNothing().when(statusValidation).statusCheck(status);

        TaskValidationErrorException thrown = assertThrows(TaskValidationErrorException.class, () -> validation.verifyTask(title, description, status, dueDate));
        assertEquals(expectedErrors.toString(), thrown.getMessage());

    }


    @Test
    void whenThereIsMoreThanOneErrorAllErrorsAreReturned() {
        title = "";
        description = "||";
        status = "#";

        String stringDate = "2025-05-05 17:00";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.UK);
        dueDate = LocalDateTime.parse(stringDate, dateTimeFormatter);

        List<String> expectedErrors = new ArrayList<>();
        expectedErrors.add("Task title is empty");
        expectedErrors.add("Task description does not match the pattern a-zA-Z0-9");

        doNothing().when(statusValidation).statusCheck(status);

        TaskValidationErrorException thrown = assertThrows( TaskValidationErrorException.class, () -> validation.verifyTask(title, description, status, dueDate));
        assertEquals(expectedErrors.toString(), thrown.getMessage());

    }
}
