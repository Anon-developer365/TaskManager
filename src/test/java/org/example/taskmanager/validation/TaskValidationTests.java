package org.example.taskmanager.validation;

import org.example.taskmanager.exceptions.TaskValidationErrorException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.format.annotation.DateTimeFormat;

import static org.mockito.Mockito.when;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
    private Date dueDate;

    @Test
    void whenATaskTitleIsEmptyAnErrorIsReturned() throws ParseException {
        validation = new TaskValidation(statusValidation);
        title = "";
        description = "description";
        status = "open status";
        final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        dueDate = (dateFormat.parse("2025-05-05 17:00"));
        List<String> statusErrors = new ArrayList<>();
        List<String> expectedErrors = new ArrayList<>();
        expectedErrors.add("Task title is empty");
        when(statusValidation.statusCheck(status)).thenReturn(statusErrors);
        System.out.println(dueDate.toString());

        TaskValidationErrorException thrown = assertThrows(TaskValidationErrorException.class, () -> validation.verifyTask(title, description, status, dueDate));
        assertEquals(expectedErrors.toString(), thrown.getMessage());

    }

    @Test
    void whenATaskTitleIsInTheIncorrectFormatAnErrorIsReturned() throws ParseException {
        title = "||";
        description = "description";
        status = "open status";
        final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");

        dueDate = (dateFormat.parse("2025-05-05 17:00"));
        List<String> expectedErrors = new ArrayList<>();
        expectedErrors.add("Task title does not match the pattern a-zA-Z0-9");
        List<String> statusErrors = new ArrayList<>();
        when(statusValidation.statusCheck(status)).thenReturn(statusErrors);

        TaskValidationErrorException thrown = assertThrows(TaskValidationErrorException.class, () -> validation.verifyTask(title, description, status, dueDate));
        assertEquals(expectedErrors.toString(), thrown.getMessage());

    }

    @Test
    void whenATaskTitleContainsNumbersNoErrorIsReturned() throws ParseException {
        title = "case 23";
        description = "description";
        status = "open status";
        final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");

        dueDate = (dateFormat.parse("2025-05-05 17:00"));
        List<String> statusErrors = new ArrayList<>();
        when(statusValidation.statusCheck(status)).thenReturn(statusErrors);

        assertDoesNotThrow(() -> validation.verifyTask(title, description, status, dueDate));

    }


    @Test
    void whenDescriptionDoesNotMatchTheFormatAnErrorIsReturned() throws ParseException {
        title = "Task title";
        description = "||";
        status = "open status";
        final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");

        dueDate = (dateFormat.parse("2025-05-05 17:00"));
        List<String> expectedErrors = new ArrayList<>();
        expectedErrors.add("Task description does not match the pattern a-zA-Z0-9");
        List<String> statusErrors = new ArrayList<>();
        when(statusValidation.statusCheck(status)).thenReturn(statusErrors);

        TaskValidationErrorException thrown = assertThrows(TaskValidationErrorException.class, () -> validation.verifyTask(title, description, status, dueDate));
        assertEquals(expectedErrors.toString(), thrown.getMessage());

    }

    @Test
    void whenADescriptionIsEmptyNoErrorIsReturned() throws ParseException {
        title = "Task 23";
        description = "";
        status = "open status";
        final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");

        dueDate = (dateFormat.parse("2025-05-05 17:00"));

        List<String> statusErrors = new ArrayList<>();
        when(statusValidation.statusCheck(status)).thenReturn(statusErrors);

        assertDoesNotThrow(() -> validation.verifyTask(title, description, status, dueDate));

    }

    @Test
    void whenADescriptionContainsNumbersNoErrorIsReturned() throws ParseException {
        title = "Task 23";
        description = "Awaiting 3 new parts for hard drive";
        status = "open status";
        final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");

        dueDate = (dateFormat.parse("2025-05-05 17:00"));

        List<String> statusErrors = new ArrayList<>();
        when(statusValidation.statusCheck(status)).thenReturn(statusErrors);

        assertDoesNotThrow(() -> validation.verifyTask(title, description, status, dueDate));

    }

    @Test
    void whenStatusIsEmptyAnErrorIsReturned() throws ParseException {
        title = "Task 23";
        description = "Awaiting 3 new parts for hard drive";
        status = "";
        final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");

        dueDate = (dateFormat.parse("2025-05-05 17:00"));
        List<String> expectedErrors = new ArrayList<>();
        expectedErrors.add("Task status is empty");

        when(statusValidation.statusCheck(status)).thenReturn(expectedErrors);

        TaskValidationErrorException thrown = assertThrows(TaskValidationErrorException.class, () -> validation.verifyTask(title, description, status, dueDate));
        assertEquals(expectedErrors.toString(), thrown.getMessage());

    }

    @Test
    void whenStatusDoesNotMatchTheCorrectFormatAnErrorIsReturned() throws ParseException {
        title = "Task 23";
        description = "Awaiting 3 new parts for hard drive";
        status = "||";
        final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");

        dueDate = (dateFormat.parse("2025-05-05 17:00"));
        List<String> expectedErrors = new ArrayList<>();
        expectedErrors.add("Task status does not match the pattern a-zA-Z0-9");

        when(statusValidation.statusCheck(status)).thenReturn(expectedErrors);

        TaskValidationErrorException thrown = assertThrows(TaskValidationErrorException.class, () -> validation.verifyTask(title, description, status, dueDate));
        assertEquals(expectedErrors.toString(), thrown.getMessage());

    }

    @Test
    void whenDueDateIsEmptyAnErrorIsReturned() {
        title = "Task 23";
        description = "Awaiting 3 new parts for hard drive";
        status = "open status";
        List<String> expectedErrors = new ArrayList<>();
        expectedErrors.add("Task due date is empty");

        List<String> statusErrors = new ArrayList<>();
        when(statusValidation.statusCheck(status)).thenReturn(statusErrors);

        TaskValidationErrorException thrown = assertThrows(TaskValidationErrorException.class, () -> validation.verifyTask(title, description, status, dueDate));
        assertEquals(expectedErrors.toString(), thrown.getMessage());

    }


    @Test
    void whenThereIsMoreThanOneErrorAllErrorsAreReturned() throws ParseException {
        title = "";
        description = "||";
        status = "#";
        final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        dueDate = (dateFormat.parse("2025-05-05 17:00"));
        List<String> expectedErrors = new ArrayList<>();
        expectedErrors.add("Task title is empty");
        expectedErrors.add("Task description does not match the pattern a-zA-Z0-9");
        expectedErrors.add("Task status does not match the pattern a-zA-Z0-9");

        List<String> statusErrors = new ArrayList<>();
        statusErrors.add("Task status does not match the pattern a-zA-Z0-9");
        when(statusValidation.statusCheck(status)).thenReturn(statusErrors);

        TaskValidationErrorException thrown = assertThrows( TaskValidationErrorException.class, () -> validation.verifyTask(title, description, status, dueDate));
        assertEquals(expectedErrors.toString(), thrown.getMessage());

    }
}
