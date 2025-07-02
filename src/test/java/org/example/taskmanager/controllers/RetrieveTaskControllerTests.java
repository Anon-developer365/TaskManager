package org.example.taskmanager.controllers;

import org.example.taskmanager.pojo.Task;
import org.example.taskmanager.service.GetATask;
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

import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@SpringBootTest
public class RetrieveTaskControllerTests {
    @Autowired
    private final WebApplicationContext context;

    @Mock
    private GetATask getATask;

    @InjectMocks
    private RetrieveTaskController retrieveTaskController;

    public RetrieveTaskControllerTests(WebApplicationContext context) {
        this.context = context;
    }


    @Test
    void aSuccessMessageIsReceivedWhenDetailsAreProvided() {
        retrieveTaskController = new RetrieveTaskController(getATask);
        final UUID uuid = UUID.randomUUID();
        String dueDate = "20-05-2025 09:00";
        Task task = new Task(uuid.toString(), "develop database", "", "open status", dueDate);
        when(getATask.getATask(uuid.toString())).thenReturn(task);
        ResponseEntity<Task> output = retrieveTaskController.getTask(uuid.toString());
        assert output != null;
        assert Objects.equals(Objects.requireNonNull(output.getBody()).getId(), uuid.toString());

    }

    @Test
    void whenAPostRequestIsSentAnErrorIsReceived() throws Exception {
        MockMvc mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
        mvc.perform(post("/getTask")).andExpect(status().is4xxClientError());
    }

    @Test
    void ifTheIdDoesNotExistAMessageIsReturnedToTheConsumer() {
        retrieveTaskController = new RetrieveTaskController(getATask);
        final UUID uuid = UUID.randomUUID();
        String dueDate = "20-05-2025 09:00";
        when(getATask.getATask(uuid.toString())).thenThrow(new RuntimeException("Task with ID " + uuid + " not found"));

        String expectedError = "Task with ID " + uuid + " not found";
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> retrieveTaskController.getTask(uuid.toString()));
        assertEquals(expectedError, thrown.getMessage());
    }
}
