package org.example.taskmanager.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.Objects;

@WebAppConfiguration
@SpringBootTest
public class RetrieveTaskControllerTests {
    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    private RetrieveTaskController retrieveTaskController;

    @Test
    void aSuccessMessageIsReceivedWhenDetailsAreProvided() {

        retrieveTaskController = new RetrieveTaskController();
        ResponseEntity<Task> output = retrieveTaskController.getTask("1");
        assert output != null;
        assert Objects.equals(output.getBody().getId(), "1");

    }
}
