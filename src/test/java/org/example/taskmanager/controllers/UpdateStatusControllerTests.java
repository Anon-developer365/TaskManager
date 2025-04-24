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
public class UpdateStatusControllerTests {
    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    private UpdateStatusController updateStatusController;


    @Test
    void aSuccessMessageIsReceivedWhenDetailsAreProvided() {
        String status = "This is a new status";
        String expectedResult = "status updated to " + status;
        updateStatusController = new UpdateStatusController();
        ResponseEntity<String> output = updateStatusController.updateStatus("1", status);
        assert output != null;
        assert Objects.equals(output.getBody(), expectedResult);

    }

    @Test
    void whenAPutRequestIsSentWithTheCorrectMappingAResponseIsReceived() throws Exception {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
        mvc.perform(put("/updateStatus")).andExpect(status().isOk());

    }

    @Test
    void whenAGetRequestIsSentAnErrorIsReceived() throws Exception {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
        mvc.perform(get("/updateStatus")).andExpect(status().is4xxClientError());
    }
}
