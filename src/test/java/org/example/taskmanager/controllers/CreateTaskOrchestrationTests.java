package org.example.taskmanager.controllers;

import org.example.taskmanager.exceptions.TaskValidationErrorException;
import org.example.taskmanager.pojo.Task;
import org.example.taskmanager.service.CreateTask;
import org.example.taskmanager.service.SaveTask;
import org.example.taskmanager.validation.TaskValidation;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import uk.gov.hmcts.taskmanager.domain.CreateTaskRequest;
import uk.gov.hmcts.taskmanager.domain.SuccessResponse;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@SpringBootTest
public class CreateTaskOrchestrationTests {

    @Mock
    private CreateTask createTask;

    @Mock
    private SaveTask saveTask;

    @Mock
    TaskValidation taskValidation;

    @InjectMocks
    private CreateTaskOrchestration createTaskOrchestration;

    @Autowired
    private final WebApplicationContext context;

    private CreateTaskRequest createTaskRequest;

    public CreateTaskOrchestrationTests(WebApplicationContext context) {
        this.context = context;
    }


    @Test
    void aSuccessMessageIsReceivedWhenTheEndPointIsHit() throws ParseException {
        UUID uuid = UUID.randomUUID();
        String transactionId = "1";
        final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        Date date = (dateFormat.parse("2025-05-05 17:00"));
        createTaskRequest = new CreateTaskRequest();
        createTaskRequest.setTitle("case title");
        createTaskRequest.setTaskDescription("description");
        createTaskRequest.setStatus("open status");
        createTaskRequest.setDueDate(date);

        LocalDateTime dueDate = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        Task task = new Task(uuid.toString(), createTaskRequest.getTitle(), createTaskRequest.getTaskDescription(), createTaskRequest.getStatus(), dueDate);
        createTaskOrchestration = new CreateTaskOrchestration(createTask, saveTask, taskValidation);
        doNothing().when(taskValidation).verifyTask(createTaskRequest.getTitle(), createTaskRequest.getTaskDescription(), createTaskRequest.getStatus(), createTaskRequest.getDueDate());
        when(createTask.createNewTask(createTaskRequest.getTitle(), createTaskRequest.getTaskDescription(), createTaskRequest.getStatus(), createTaskRequest.getDueDate())).thenReturn(task);
        when(saveTask.saveData(task)).thenReturn(uuid.toString());
        SuccessResponse output = createTaskOrchestration.createTask(transactionId, createTaskRequest);
        assertEquals("Task Created successfully", output.getMessage());
        assertEquals(uuid.toString(), output.getId());

    }

    @Test
    void whenAGetRequestIsSentAnErrorIsReceived() throws Exception {
        MockMvc mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
        mvc.perform(get("/createTask")).andExpect(status().is4xxClientError());
    }

    @Test
    void whenThereIsAValidationErrorThisIsReturnedToTheConsumer() throws ParseException {
        UUID uuid = UUID.randomUUID();

        final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = (dateFormat.parse("2025-05-05 17:00"));

        createTaskRequest = new CreateTaskRequest();
        createTaskRequest.setTaskDescription("description");
        createTaskRequest.setStatus("open status");
        createTaskRequest.setTitle("");
        createTaskRequest.setDueDate(date);

        LocalDateTime dueDate = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        Task task = new Task(uuid.toString(), createTaskRequest.getTitle(),
                createTaskRequest.getTaskDescription(), createTaskRequest.getStatus(), dueDate);
        createTaskOrchestration = new CreateTaskOrchestration(createTask, saveTask, taskValidation);
        when(createTask.createNewTask(createTaskRequest.getTitle(), createTaskRequest.getTaskDescription(), createTaskRequest.getStatus(), date)).thenReturn(task);
        when(saveTask.saveData(task)).thenReturn(uuid.toString());

        String expectedError = "Task title is empty";

        TaskValidationErrorException thrown = assertThrows(TaskValidationErrorException.class, () -> {
            doThrow(new TaskValidationErrorException("Task title is empty")).when(taskValidation).verifyTask(createTaskRequest.getTitle(),
                    createTaskRequest.getTaskDescription(), createTaskRequest.getStatus(), date);
            createTaskOrchestration.createTask("1", createTaskRequest);
        });
        assertEquals(expectedError, thrown.getMessage());

    }

    @Test
    void whenThereIsAnErrorSavingTheTaskThisIsReturnedToTheConsumer() throws ParseException {
        UUID uuid = UUID.randomUUID();
        final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = (dateFormat.parse("2025-05-05 17:00"));

        createTaskRequest = new CreateTaskRequest();
        createTaskRequest.setTaskDescription("description");
        createTaskRequest.setStatus("open status");
        createTaskRequest.setTitle("");
        createTaskRequest.setDueDate(date);

        LocalDateTime dueDate = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        Task task = new Task(uuid.toString(), createTaskRequest.getTitle(),
                createTaskRequest.getTaskDescription(), createTaskRequest.getStatus(), dueDate);
        createTaskOrchestration = new CreateTaskOrchestration(createTask, saveTask, taskValidation);
        when(createTask.createNewTask(createTaskRequest.getTitle(),
                createTaskRequest.getTaskDescription(), createTaskRequest.getStatus(), date)).thenReturn(task);
        when(saveTask.saveData(task)).thenThrow(new RuntimeException("An error occurred saving the task"));

        String expectedError = "An error occurred saving the task";

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> createTaskOrchestration.createTask("1", createTaskRequest));
        assertEquals(expectedError, thrown.getMessage());

    }

}
