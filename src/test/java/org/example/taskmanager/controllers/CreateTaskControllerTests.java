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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@SpringBootTest
public class CreateTaskControllerTests {

    private CreateTaskController createTaskController;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;


    @Test
    void aSuccessMessageIsReceivedWhenTheEndPointIsHit() {
        createTaskController = new CreateTaskController();
        ResponseEntity<String> output = createTaskController.createTask();
        assert output != null;
        assert Objects.equals(output.getBody(), "Task Created");

    }

    @Test
    void whenAPostRequestIsSentWithTheCorrectMappingAResponseIsReceived() throws Exception {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
        mvc.perform(post("/createTask")).andExpect(status().isOk());

    }

    @Test
    void whenAGetRequestIsSentAnErrorIsReceived() throws Exception {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
        mvc.perform(get("/createTask")).andExpect(status().is4xxClientError());
    }
}
