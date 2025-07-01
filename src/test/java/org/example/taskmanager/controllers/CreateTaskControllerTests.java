package org.example.taskmanager.controllers;

import org.example.taskmanager.exceptions.TaskValidationErrorException;
import org.example.taskmanager.pojo.Task;
import org.example.taskmanager.service.CreateTask;
import org.example.taskmanager.service.SaveTask;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@SpringBootTest
public class CreateTaskControllerTests {

    @Mock
    private CreateTask createTask;

    @Mock
    private SaveTask saveTask;

    @Mock
    TaskValidation taskValidation;

    @InjectMocks
    private CreateTaskController createTaskController;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;


    @Test
    void aSuccessMessageIsReceivedWhenTheEndPointIsHit() {
        UUID uuid = UUID.randomUUID();
        String casetitle = "case title";
        String description = "description";
        String status = "open status";
        String dueDate = "05-05-2025 17:00";

        Task task = new Task(uuid.toString(), casetitle, description, status, dueDate);
        createTaskController = new CreateTaskController(createTask, saveTask, taskValidation);
        doNothing().when(taskValidation).verifyTask(casetitle, description, status, dueDate);
        when(createTask.createNewTask(casetitle, description, status, dueDate)).thenReturn(task);
        when(saveTask.saveData(task)).thenReturn(uuid.toString());
        ResponseEntity<String> output = createTaskController.createTask(casetitle, description, status, dueDate);
        assert output != null;
        assert Objects.equals(output.getBody(), uuid + " Task Created");

    }

    @Test
    void whenAGetRequestIsSentAnErrorIsReceived() throws Exception {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
        mvc.perform(get("/createTask")).andExpect(status().is4xxClientError());
    }

    @Test
    void whenThereIsAValidationErrorThisIsReturnedToTheConsumer() {
        UUID uuid = UUID.randomUUID();
        String casetitle = "case title";
        String description = "description";
        String status = "open status";
        String dueDate = "05-05-2025 17:00";

        Task task = new Task(uuid.toString(), casetitle, description, status, dueDate);
        createTaskController = new CreateTaskController(createTask, saveTask, taskValidation);
        when(createTask.createNewTask(casetitle, description, status, dueDate)).thenReturn(task);
        when(saveTask.saveData(task)).thenReturn(uuid.toString());

        String expectedError = "validation error";

        TaskValidationErrorException thrown = assertThrows(TaskValidationErrorException.class, () -> {
            doThrow(new TaskValidationErrorException("validation error")).when(taskValidation).verifyTask(casetitle, description, status, dueDate);
            createTaskController.createTask(casetitle,description,status,dueDate);
        });
        assertEquals(expectedError, thrown.getMessage());

    }
}
