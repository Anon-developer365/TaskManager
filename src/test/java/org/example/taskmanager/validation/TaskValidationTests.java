package org.example.taskmanager.validation;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TaskValidationTests {
    private TaskValidation validation;
    private String title;
    private String description;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime dueDate;

    @Test
    void whenATaskTitleIsEmptyAnErrorIsReturned() {
        validation = new TaskValidation();
        title = "";
        description = "description";

        String stringDate = "2025-05-05 17:00";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.UK);
        dueDate = LocalDateTime.parse(stringDate, dateTimeFormatter);

        List<String> expectedErrors = new ArrayList<>();
        expectedErrors.add("Task title is blank");

        List<String> actualOutput = validation.verifyTask(title, description, dueDate);
        assertEquals(expectedErrors, actualOutput);

    }

    @Test
    void whenATaskTitleIsInTheIncorrectFormatAnErrorIsReturned() {
        validation = new TaskValidation();
        title = "||";
        description = "description";

        String stringDate = "2025-05-05 17:00";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.UK);
        dueDate = LocalDateTime.parse(stringDate, dateTimeFormatter);

        List<String> expectedErrors = new ArrayList<>();
        expectedErrors.add("Task title does not match the pattern a-zA-Z0-9");

        List<String> actualOutput = validation.verifyTask(title, description, dueDate);
        assertEquals(expectedErrors, actualOutput);

    }

    @Test
    void whenATaskTitleContainsNumbersNoErrorIsReturned() {
        validation = new TaskValidation();
        title = "case 23";
        description = "description";

        String stringDate = "2025-05-05 17:00";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.UK);
        dueDate = LocalDateTime.parse(stringDate, dateTimeFormatter);

        List<String> expectedErrors = new ArrayList<>();
        List<String> actualOutput = validation.verifyTask(title, description, dueDate);
        assertEquals(expectedErrors, actualOutput);

    }


    @Test
    void whenDescriptionDoesNotMatchTheFormatAnErrorIsReturned() {
        validation = new TaskValidation();
        title = "Task title";
        description = "||";

        String stringDate = "2025-05-05 17:00";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.UK);
        dueDate = LocalDateTime.parse(stringDate, dateTimeFormatter);

        List<String> expectedErrors = new ArrayList<>();
        expectedErrors.add("Task description does not match the pattern a-zA-Z0-9");

        List<String> actualOutput = validation.verifyTask(title, description, dueDate);
        assertEquals(expectedErrors, actualOutput);

    }

    @Test
    void whenADescriptionIsEmptyNoErrorIsReturned() {
        validation = new TaskValidation();
        title = "Task 23";
        description = "";

        String stringDate = "2025-05-05 17:00";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.UK);
        dueDate = LocalDateTime.parse(stringDate, dateTimeFormatter);

        List<String> expectedErrors = new ArrayList<>();
        List<String> actualOutput = validation.verifyTask(title, description, dueDate);
        assertEquals(expectedErrors, actualOutput);

    }

    @Test
    void whenADescriptionContainsNumbersNoErrorIsReturned() {
        validation = new TaskValidation();
        title = "Task 23";
        description = "Awaiting 3 new parts for hard drive";

        String stringDate = "2025-05-05 17:00";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.UK);
        dueDate = LocalDateTime.parse(stringDate, dateTimeFormatter);

        List<String> expectedErrors = new ArrayList<>();
        List<String> actualOutput = validation.verifyTask(title, description, dueDate);
        assertEquals(expectedErrors, actualOutput);

    }


    @Test
    void whenDueDateIsEmptyAnErrorIsReturned() {
        validation = new TaskValidation();
        title = "Task 23";
        description = "Awaiting 3 new parts for hard drive";

        List<String> expectedErrors = new ArrayList<>();
        expectedErrors.add("Task due date is blank");

        List<String> actualOutput = validation.verifyTask(title, description, dueDate);
        assertEquals(expectedErrors, actualOutput);

    }


    @Test
    void whenThereIsMoreThanOneErrorAllErrorsAreReturned() {
        validation = new TaskValidation();
        title = "";
        description = "||";

        String stringDate = "2025-05-05 17:00";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.UK);
        dueDate = LocalDateTime.parse(stringDate, dateTimeFormatter);

        List<String> expectedErrors = new ArrayList<>();
        expectedErrors.add("Task title is blank");
        expectedErrors.add("Task description does not match the pattern a-zA-Z0-9");

        List<String> actualOutput = validation.verifyTask(title, description, dueDate);
        assertEquals(expectedErrors, actualOutput);

    }

}
