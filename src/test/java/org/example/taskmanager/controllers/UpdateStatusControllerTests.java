package org.example.taskmanager.controllers;

import org.example.taskmanager.exceptions.TaskValidationErrorException;
import org.example.taskmanager.service.UpdateStatus;
import org.example.taskmanager.validation.UpdateStatusValidation;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import uk.gov.hmcts.taskmanager.domain.SuccessResponse;
import uk.gov.hmcts.taskmanager.domain.UpdateStatusRequest;

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

        UpdateStatusRequest updateStatusRequest = new UpdateStatusRequest();
        updateStatusRequest.setId(uuid.toString());
        updateStatusRequest.setStatus("This is a new status");

        String expectedResult = "Status updated to: " + updateStatusRequest.getStatus();
        updateStatusController = new UpdateStatusController(updateStatus, updateStatusValidation);
        doNothing().when(updateStatusValidation).verifyStatus(updateStatusRequest.getId(), updateStatusRequest.getStatus());
        when(updateStatus.updateStatus(updateStatusRequest)).thenReturn(true);
        SuccessResponse output = updateStatusController.updateStatus(updateStatusRequest);
        assert output != null;
        assertEquals(expectedResult, output.getMessage());
        assertEquals(updateStatusRequest.getId(), output.getId());

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

        UpdateStatusRequest updateStatusRequest = new UpdateStatusRequest();
        updateStatusRequest.setId(uuid.toString());
        updateStatusRequest.setStatus("This is a new status");

        updateStatusController = new UpdateStatusController(updateStatus, updateStatusValidation);

        String expectedError = "validation error";

        TaskValidationErrorException thrown = assertThrows(TaskValidationErrorException.class, () -> {
            doThrow(new TaskValidationErrorException("validation error")).when(updateStatusValidation).verifyStatus(uuid.toString(), updateStatusRequest.getStatus());
            updateStatusController.updateStatus(updateStatusRequest);
        });
        assertEquals(expectedError, thrown.getMessage());

    }
}
