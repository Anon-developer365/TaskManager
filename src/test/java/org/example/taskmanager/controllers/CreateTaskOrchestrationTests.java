package org.example.taskmanager.controllers;

import org.example.taskmanager.exceptions.TaskValidationErrorException;
import org.example.taskmanager.pojo.Task;
import org.example.taskmanager.service.CreateTask;
import org.example.taskmanager.service.SaveTask;
import org.example.taskmanager.validation.ValidationOrchestration;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.gov.hmcts.taskmanager.domain.CreateTaskRequest;
import uk.gov.hmcts.taskmanager.domain.SuccessResponse;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@WebAppConfiguration
@SpringBootTest
public class CreateTaskOrchestrationTests {

    @Mock
    private CreateTask createTask;

    @Mock
    private SaveTask saveTask;

    @Mock
    private ValidationOrchestration validationOrchestration;

    @InjectMocks
    private CreateTaskOrchestration createTaskOrchestration;


    private CreateTaskRequest createTaskRequest;


    @Test
    void aSuccessMessageIsReceivedWhenTheEndPointIsHit() {
        String taskId = "1";
        String transactionId = "1";

        String stringDate = "2025-05-05 17:00";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.UK);
        LocalDateTime dueDate = LocalDateTime.parse(stringDate, dateTimeFormatter);

        createTaskRequest = new CreateTaskRequest();
        createTaskRequest.setTitle("case title");
        createTaskRequest.setTaskDescription("description");
        createTaskRequest.setStatus("open status");
        createTaskRequest.setDueDate(dueDate);

        Task task = new Task(taskId, createTaskRequest.getTitle(), createTaskRequest.getTaskDescription(), createTaskRequest.getStatus(), dueDate);
        createTaskOrchestration = new CreateTaskOrchestration(createTask, saveTask, validationOrchestration);
        doNothing().when(validationOrchestration).createTaskValidation(transactionId, createTaskRequest);
        when(createTask.createNewTask(createTaskRequest.getTitle(), createTaskRequest.getTaskDescription(), createTaskRequest.getStatus(), createTaskRequest.getDueDate())).thenReturn(task);
        when(saveTask.saveData(task)).thenReturn(taskId);
        SuccessResponse output = createTaskOrchestration.createATask(transactionId, createTaskRequest);
        assertEquals("Task Created Successfully", output.getMessage());
        assertEquals(taskId, output.getId());

    }

    @Test
    void whenThereIsAValidationErrorThisIsReturnedToTheConsumer() {
        UUID uuid = UUID.randomUUID();

        String stringDate = "2025-05-05 17:00";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.UK);
        LocalDateTime dueDate = LocalDateTime.parse(stringDate, dateTimeFormatter);

        createTaskRequest = new CreateTaskRequest();
        createTaskRequest.setTaskDescription("description");
        createTaskRequest.setStatus("open status");
        createTaskRequest.setTitle("");
        createTaskRequest.setDueDate(dueDate);

        Task task = new Task(uuid.toString(), createTaskRequest.getTitle(),
                createTaskRequest.getTaskDescription(), createTaskRequest.getStatus(), dueDate);
        createTaskOrchestration = new CreateTaskOrchestration(createTask, saveTask, validationOrchestration);
        when(createTask.createNewTask(createTaskRequest.getTitle(), createTaskRequest.getTaskDescription(), createTaskRequest.getStatus(), dueDate)).thenReturn(task);
        when(saveTask.saveData(task)).thenReturn(uuid.toString());

        String expectedError = "Task title is empty";

        TaskValidationErrorException thrown = assertThrows(TaskValidationErrorException.class, () -> {
            doThrow(new TaskValidationErrorException("Task title is empty")).when(validationOrchestration).createTaskValidation("1", createTaskRequest);
            createTaskOrchestration.createATask("1", createTaskRequest);
        });
        assertEquals(expectedError, thrown.getMessage());

    }

    @Test
    void whenThereIsAnErrorSavingTheTaskThisIsReturnedToTheConsumer() {
        UUID uuid = UUID.randomUUID();

        String stringDate = "2025-05-05 17:00";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.UK);
        LocalDateTime dueDate = LocalDateTime.parse(stringDate, dateTimeFormatter);

        createTaskRequest = new CreateTaskRequest();
        createTaskRequest.setTaskDescription("description");
        createTaskRequest.setStatus("open status");
        createTaskRequest.setTitle("");
        createTaskRequest.setDueDate(dueDate);

        Task task = new Task(uuid.toString(), createTaskRequest.getTitle(),
                createTaskRequest.getTaskDescription(), createTaskRequest.getStatus(), dueDate);
        createTaskOrchestration = new CreateTaskOrchestration(createTask, saveTask, validationOrchestration);
        when(createTask.createNewTask(createTaskRequest.getTitle(),
                createTaskRequest.getTaskDescription(), createTaskRequest.getStatus(), dueDate)).thenReturn(task);
        when(saveTask.saveData(task)).thenThrow(new RuntimeException("An error occurred saving the task"));

        String expectedError = "An error occurred saving the task";

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> createTaskOrchestration.createATask("1", createTaskRequest));
        assertEquals(expectedError, thrown.getMessage());

    }

}
