package org.example.taskmanager.validation;

import org.example.taskmanager.exceptions.TaskValidationErrorException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
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
    private String dueDate;

    @Test
    void whenATaskTitleIsEmptyAnErrorIsReturned() {
        validation = new TaskValidation(statusValidation);
        title = "";
        description = "description";
        status = "open status";
        dueDate = "2025-05-05 17:00";
        List<String> statusErrors = new ArrayList<>();
        List<String> expectedErrors = new ArrayList<>();
        expectedErrors.add("Task title is empty");
        when(statusValidation.statusCheck(status)).thenReturn(statusErrors);

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
        List<String> statusErrors = new ArrayList<>();
        when(statusValidation.statusCheck(status)).thenReturn(statusErrors);

        TaskValidationErrorException thrown = assertThrows(TaskValidationErrorException.class, () -> validation.verifyTask(title, description, status, dueDate));
        assertEquals(expectedErrors.toString(), thrown.getMessage());

    }

    @Test
    void whenATaskTitleContainsNumbersNoErrorIsReturned() {
        title = "case 23";
        description = "description";
        status = "open status";
        dueDate = "2025-05-05 17:00";

        List<String> statusErrors = new ArrayList<>();
        when(statusValidation.statusCheck(status)).thenReturn(statusErrors);

        assertDoesNotThrow(() -> validation.verifyTask(title, description, status, dueDate));

    }


    @Test
    void whenDescriptionDoesNotMatchTheFormatAnErrorIsReturned() {
        title = "Task title";
        description = "||";
        status = "open status";
        dueDate = "2025-05-05 17:00";
        List<String> expectedErrors = new ArrayList<>();
        expectedErrors.add("Task description does not match the pattern a-zA-Z0-9");
        List<String> statusErrors = new ArrayList<>();
        when(statusValidation.statusCheck(status)).thenReturn(statusErrors);

        TaskValidationErrorException thrown = assertThrows(TaskValidationErrorException.class, () -> validation.verifyTask(title, description, status, dueDate));
        assertEquals(expectedErrors.toString(), thrown.getMessage());

    }

    @Test
    void whenADescriptionIsEmptyNoErrorIsReturned() {
        title = "Task 23";
        description = "";
        status = "open status";
        dueDate = "2025-05-05 17:00";

        List<String> statusErrors = new ArrayList<>();
        when(statusValidation.statusCheck(status)).thenReturn(statusErrors);

        assertDoesNotThrow(() -> validation.verifyTask(title, description, status, dueDate));

    }

    @Test
    void whenADescriptionContainsNumbersNoErrorIsReturned() {
        title = "Task 23";
        description = "Awaiting 3 new parts for hard drive";
        status = "open status";
        dueDate = "2025-05-05 17:00";

        List<String> statusErrors = new ArrayList<>();
        when(statusValidation.statusCheck(status)).thenReturn(statusErrors);

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

        when(statusValidation.statusCheck(status)).thenReturn(expectedErrors);

        TaskValidationErrorException thrown = assertThrows(TaskValidationErrorException.class, () -> validation.verifyTask(title, description, status, dueDate));
        assertEquals(expectedErrors.toString(), thrown.getMessage());

    }

    @Test
    void whenStatusDoesNotMatchTheCorrectFormatAnErrorIsReturned() {
        title = "Task 23";
        description = "Awaiting 3 new parts for hard drive";
        status = "||";
        dueDate = "2025-05-05 17:00";
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
        dueDate = "";
        List<String> expectedErrors = new ArrayList<>();
        expectedErrors.add("Task due date is empty");

        List<String> statusErrors = new ArrayList<>();
        when(statusValidation.statusCheck(status)).thenReturn(statusErrors);

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

        List<String> statusErrors = new ArrayList<>();
        when(statusValidation.statusCheck(status)).thenReturn(statusErrors);

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

        List<String> statusErrors = new ArrayList<>();
        when(statusValidation.statusCheck(status)).thenReturn(statusErrors);

        TaskValidationErrorException thrown = assertThrows( TaskValidationErrorException.class, () -> validation.verifyTask(title, description, status, dueDate));
        assertEquals(expectedErrors.toString(), thrown.getMessage());

    }

}
