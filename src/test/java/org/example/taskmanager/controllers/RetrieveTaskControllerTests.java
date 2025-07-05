package org.example.taskmanager.controllers;

import org.example.taskmanager.service.GetATask;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import uk.gov.hmcts.taskmanager.domain.Task;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    void aSuccessMessageIsReceivedWhenDetailsAreProvided() throws ParseException {
        retrieveTaskController = new RetrieveTaskController(getATask);
        final UUID uuid = UUID.randomUUID();
        final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = dateFormat.parse("2025-05-05 17:00");

        Task task = new Task();
        task.setId(uuid.toString());
        task.setTaskDescription("description");
        task.setStatus("status");
        task.setDueDate(date);
        when(getATask.getATask(uuid.toString())).thenReturn(task);
        uk.gov.hmcts.taskmanager.domain.Task output = retrieveTaskController.getTask(uuid.toString());
        assert output != null;
        assert Objects.equals(output.getId(), uuid.toString());

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
        when(getATask.getATask(uuid.toString())).thenThrow(new RuntimeException("Task with ID " + uuid + " not found"));

        String expectedError = "Task with ID " + uuid + " not found";
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> retrieveTaskController.getTask(uuid.toString()));
        assertEquals(expectedError, thrown.getMessage());
    }

}
