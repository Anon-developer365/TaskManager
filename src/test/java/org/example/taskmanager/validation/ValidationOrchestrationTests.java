package org.example.taskmanager.validation;

import org.example.taskmanager.exceptions.TaskValidationErrorException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import uk.gov.hmcts.taskmanager.domain.CreateTaskRequest;
import uk.gov.hmcts.taskmanager.domain.UpdateStatusRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ValidationOrchestrationTests {

    @Mock
    private IdValidation idValidation;

    @Mock
    private StatusValidation statusValidation;

    @Mock
    private TaskValidation taskValidation;

    @InjectMocks
    private ValidationOrchestration validationOrchestration;

    @Test
    void whenGeneralTaskValidationIsUsedWithValidIdsNoErrorIsThrown() {
        List<String> emptyList = new ArrayList<>();
        when(idValidation.validateId("Transaction", "2")).thenReturn(emptyList);
        when(idValidation.validateId("Task", "1")).thenReturn(emptyList);

        assertDoesNotThrow(() -> validationOrchestration.generalTaskValidation("2", "1"));
    }

    @Test
    void whenGeneralTaskValidationIsUsedWithNoTaskIdAnErrorIsThrown() {
        List<String> errorList = new ArrayList<>();
        List<String> emptyList = new ArrayList<>();
        errorList.add("Task ID is blank");
        when(idValidation.validateId("Transaction", "2")).thenReturn(emptyList);
        when(idValidation.validateId("Task", "")).thenReturn(errorList);


        Exception exception = assertThrows(TaskValidationErrorException.class, () -> validationOrchestration.generalTaskValidation("2", ""));
        assertEquals(errorList.toString(), exception.getMessage());
    }

    @Test
    void whenGeneralTaskValidationIsUsedWithNoTransactionIdAnErrorIsThrown() {
        List<String> errorList = new ArrayList<>();
        List<String> emptyList = new ArrayList<>();
        errorList.add("Transaction ID is blank");
        when(idValidation.validateId("Transaction", "")).thenReturn(emptyList);
        when(idValidation.validateId("Task", "1")).thenReturn(errorList);


        Exception exception = assertThrows(TaskValidationErrorException.class, () -> validationOrchestration.generalTaskValidation("", "1"));
        assertEquals(errorList.toString(), exception.getMessage());
    }

    @Test
    void whenGeneralTaskValidationIsUsedWithTwoBlankIdsBothAreIncludedInTheError() {
        List<String> transactionList = new ArrayList<>();
        List<String> taskList = new ArrayList<>();
        transactionList.add("Transaction ID is blank");
        taskList.add("Task ID is blank");
        when(idValidation.validateId("Transaction", "")).thenReturn(transactionList);
        when(idValidation.validateId("Task", "")).thenReturn(taskList);

        String expectedError = "[Transaction ID is blank, Task ID is blank]";
        Exception exception = assertThrows(TaskValidationErrorException.class, () -> validationOrchestration.generalTaskValidation("", ""));
        assertEquals(expectedError, exception.getMessage());
    }

    @Test
    void whenCreateTaskValidationIsUsedWithValidDetailsNoErrorIsThrown() {
        List<String> emptyList = new ArrayList<>();

        String stringDate = "2025-05-05 17:00";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.UK);
        LocalDateTime dueDate = LocalDateTime.parse(stringDate, dateTimeFormatter);

        CreateTaskRequest createTaskRequest = new CreateTaskRequest();
        createTaskRequest.setTitle("Title");
        createTaskRequest.setTaskDescription("Description");
        createTaskRequest.setStatus("Status");
        createTaskRequest.setDueDate(dueDate);

        when(idValidation.validateId("Transaction", "2")).thenReturn(emptyList);
        when(taskValidation.verifyTask(createTaskRequest.getTitle(), createTaskRequest.getTaskDescription(),
                createTaskRequest.getDueDate())).thenReturn(emptyList);
        when(statusValidation.statusCheck(createTaskRequest.getStatus())).thenReturn(emptyList);

        assertDoesNotThrow(() -> validationOrchestration.createTaskValidation("2", createTaskRequest));
    }

    @Test
    void whenCreateTaskValidationIsUsedWithAnEmptyTitleAnErrorIsThrown() {
        List<String> emptyList = new ArrayList<>();
        List<String> errorList = new ArrayList<>();
        errorList.add("Task title is blank");
        String stringDate = "2025-05-05 17:00";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.UK);
        LocalDateTime dueDate = LocalDateTime.parse(stringDate, dateTimeFormatter);

        CreateTaskRequest createTaskRequest = new CreateTaskRequest();
        createTaskRequest.setTitle("");
        createTaskRequest.setTaskDescription("Description");
        createTaskRequest.setStatus("Status");
        createTaskRequest.setDueDate(dueDate);

        when(idValidation.validateId("Transaction", "2")).thenReturn(emptyList);
        when(taskValidation.verifyTask(createTaskRequest.getTitle(), createTaskRequest.getTaskDescription(),
                createTaskRequest.getDueDate())).thenReturn(errorList);
        when(statusValidation.statusCheck(createTaskRequest.getStatus())).thenReturn(emptyList);

        Exception exception = assertThrows(TaskValidationErrorException.class, () ->
                validationOrchestration.createTaskValidation("2", createTaskRequest));
        assertEquals(errorList.toString(), exception.getMessage());

    }

    @Test
    void whenCreateTaskValidationIsUsedWithAnEmptyDueDateAnErrorIsThrown() {
        List<String> emptyList = new ArrayList<>();
        List<String> errorList = new ArrayList<>();
        errorList.add("Task due date is blank");

        CreateTaskRequest createTaskRequest = new CreateTaskRequest();
        createTaskRequest.setTitle("Title");
        createTaskRequest.setTaskDescription("Description");
        createTaskRequest.setStatus("Status");


        when(idValidation.validateId("Transaction", "2")).thenReturn(emptyList);
        when(taskValidation.verifyTask(createTaskRequest.getTitle(), createTaskRequest.getTaskDescription(),
                createTaskRequest.getDueDate())).thenReturn(errorList);
        when(statusValidation.statusCheck(createTaskRequest.getStatus())).thenReturn(emptyList);

        Exception exception = assertThrows(TaskValidationErrorException.class, () ->
                validationOrchestration.createTaskValidation("2", createTaskRequest));
        assertEquals(errorList.toString(), exception.getMessage());

    }

    @Test
    void whenCreateTaskValidationIsUsedWithAnEmptyDescriptionNoErrorIsThrown() {
        List<String> emptyList = new ArrayList<>();

        String stringDate = "2025-05-05 17:00";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.UK);
        LocalDateTime dueDate = LocalDateTime.parse(stringDate, dateTimeFormatter);

        CreateTaskRequest createTaskRequest = new CreateTaskRequest();
        createTaskRequest.setTitle("Title");
        createTaskRequest.setTaskDescription("");
        createTaskRequest.setStatus("Status");
        createTaskRequest.setDueDate(dueDate);

        when(idValidation.validateId("Transaction", "2")).thenReturn(emptyList);
        when(taskValidation.verifyTask(createTaskRequest.getTitle(), createTaskRequest.getTaskDescription(),
                createTaskRequest.getDueDate())).thenReturn(emptyList);
        when(statusValidation.statusCheck(createTaskRequest.getStatus())).thenReturn(emptyList);

        assertDoesNotThrow(() -> validationOrchestration.createTaskValidation("2", createTaskRequest));
    }

    @Test
    void whenUpdateStatusValidationIsUsedWithValidDetailsNoErrorIsThrown() {
        List<String> emptyList = new ArrayList<>();

        UpdateStatusRequest updateStatusRequest = new UpdateStatusRequest();
        updateStatusRequest.setStatus("new status");
        updateStatusRequest.setId("1");

        when(idValidation.validateId("Transaction", "2")).thenReturn(emptyList);
        when(idValidation.validateId("Task", "2")).thenReturn(emptyList);
        when(statusValidation.statusCheck(updateStatusRequest.getStatus())).thenReturn(emptyList);

        assertDoesNotThrow(() -> validationOrchestration.updateStatusValidation("2", updateStatusRequest));
    }

    @Test
    void whenUpdateStatusValidationIsUsedWithMoreThanOneErrorAllErrorsAreIncluded() {
        List<String> transactionList = new ArrayList<>();
        transactionList.add("Transaction ID is blank");
        List<String> taskList = new ArrayList<>();
        taskList.add("Task ID is blank");
        List<String> statusList = new ArrayList<>();
        statusList.add("Task status is blank");

        UpdateStatusRequest updateStatusRequest = new UpdateStatusRequest();
        updateStatusRequest.setStatus("");
        updateStatusRequest.setId("");

        when(idValidation.validateId("Transaction", "")).thenReturn(transactionList);
        when(idValidation.validateId("Task", "")).thenReturn(taskList);
        when(statusValidation.statusCheck(updateStatusRequest.getStatus())).thenReturn(statusList);

        String expectedError = "[Transaction ID is blank, Task ID is blank, Task status is blank]";
        Exception exception = assertThrows(TaskValidationErrorException.class, () ->
                validationOrchestration.updateStatusValidation("", updateStatusRequest));
        assertEquals(expectedError, exception.getMessage());

    }

    @Test
    void whenGetAllTasksValidationIsUsedWithValidDetailsNoErrorIsThrown() {
        List<String> emptyList = new ArrayList<>();

        when(idValidation.validateId("Transaction", "2")).thenReturn(emptyList);

        assertDoesNotThrow(() -> validationOrchestration.getAllTaskValidation("2"));
    }

    @Test
    void whenGetAllTasksValidationIsUsedWithAMissingTransactionIdAnErrorIsThrown() {
        List<String> transactionList = new ArrayList<>();
        transactionList.add("Transaction ID is blank");

        when(idValidation.validateId("Transaction", "")).thenReturn(transactionList);

        Exception exception = assertThrows(TaskValidationErrorException.class, () ->
               validationOrchestration.getAllTaskValidation(""));
        assertEquals(transactionList.toString(), exception.getMessage());

    }

}
