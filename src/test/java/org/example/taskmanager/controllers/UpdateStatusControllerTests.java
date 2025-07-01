package org.example.taskmanager.controllers;

import org.example.taskmanager.exceptions.TaskValidationErrorException;
import org.example.taskmanager.service.UpdateStatus;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@SpringBootTest
public class UpdateStatusControllerTests {
    @Autowired
    private final WebApplicationContext context;

    @Mock
    UpdateStatus updateStatus;

    @Mock
    UpdateStatusValidation updateStatusValidation;

    @InjectMocks
    private UpdateStatusController updateStatusController;

    public UpdateStatusControllerTests(WebApplicationContext context) {
        this.context = context;
    }


    @Test
    void aSuccessMessageIsReceivedWhenDetailsAreProvided() {
        UUID uuid = UUID.randomUUID();

        String status = "This is a new status";
        String expectedResult = "Status updated to " + status;
        updateStatusController = new UpdateStatusController(updateStatus, updateStatusValidation);
        doNothing().when(updateStatusValidation).verifyStatus(uuid.toString(), status);
        when(updateStatus.updateStatus(uuid.toString(), status)).thenReturn(true);
        ResponseEntity<String> output = updateStatusController.updateStatus(uuid.toString(), status);
        assert output != null;
        assert Objects.equals(output.getBody(), expectedResult);

    }


    @Test
    void whenAGetRequestIsSentAnErrorIsReceived() throws Exception {
        MockMvc mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
        mvc.perform(get("/updateStatus")).andExpect(status().is4xxClientError());
    }

    @Test
    void anExceptionIsThrownWhenThereIsAValidationError() {
        UUID uuid = UUID.randomUUID();

        String status = "This is a new status";
        updateStatusController = new UpdateStatusController(updateStatus, updateStatusValidation);

        String expectedError = "validation error";

        TaskValidationErrorException thrown = assertThrows(TaskValidationErrorException.class, () -> {
            doThrow(new TaskValidationErrorException("validation error")).when(updateStatusValidation).verifyStatus(uuid.toString(), status);
            updateStatusController.updateStatus(uuid.toString(), status);
        });
        assertEquals(expectedError, thrown.getMessage());

    }
}
