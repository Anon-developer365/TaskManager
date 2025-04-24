package org.example.taskmanager.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Objects;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @Test
    void whenAGetRequestIsSentWithTheCorrectMappingAResponseIsReceived() throws Exception {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
        mvc.perform(get("/getTask")).andExpect(status().isOk());

    }

    @Test
    void whenAGetRequestIsSentAnErrorIsReceived() throws Exception {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
        mvc.perform(post("/getTask")).andExpect(status().is4xxClientError());
    }
}
