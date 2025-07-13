package org.example.taskmanager.controllers;

import org.example.taskmanager.exceptions.TaskValidationErrorException;
import org.example.taskmanager.service.UpdateStatus;
import org.example.taskmanager.validation.IdValidation;
import org.example.taskmanager.validation.StatusValidation;
import org.example.taskmanager.validation.ValidationOrchestration;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.annotation.Validated;
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
public class UpdateStatusOrchestrationTests {
    @Autowired
    private final WebApplicationContext context;

    @Mock
    private UpdateStatus updateStatus;

    @Mock
    private ValidationOrchestration validationOrchestration;

    @Mock
    private StatusValidation statusValidation;

    @InjectMocks
    private UpdateStatusOrchestration updateStatusOrchestration;

    public UpdateStatusOrchestrationTests(WebApplicationContext context) {
        this.context = context;
    }


    @Test
    void aSuccessMessageIsReceivedWhenDetailsAreProvided() {
        String taskId = "1";

        UpdateStatusRequest updateStatusRequest = new UpdateStatusRequest();
        updateStatusRequest.setId(taskId);
        updateStatusRequest.setStatus("This is a new status");

        String expectedResult = "Status updated to: \"" + updateStatusRequest.getStatus()+"\"";
        updateStatusOrchestration = new UpdateStatusOrchestration(updateStatus, validationOrchestration, statusValidation);
        doNothing().when(validationOrchestration).updateStatusValidation("2", updateStatusRequest);
        when(updateStatus.updateStatus(updateStatusRequest)).thenReturn(true);
        SuccessResponse output = updateStatusOrchestration.updateStatus("2", updateStatusRequest);
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

        UpdateStatusRequest updateStatusRequest = new UpdateStatusRequest();
        updateStatusRequest.setId("1");
        updateStatusRequest.setStatus("This is a new status");

        updateStatusOrchestration = new UpdateStatusOrchestration(updateStatus, validationOrchestration, statusValidation);
        String expectedError = "validation error";

        TaskValidationErrorException thrown = assertThrows(TaskValidationErrorException.class, () -> {
            doThrow(new TaskValidationErrorException("validation error")).when(validationOrchestration).updateStatusValidation(
                    "2", updateStatusRequest);
            updateStatusOrchestration.updateStatus("2", updateStatusRequest);
        });
        assertEquals(expectedError, thrown.getMessage());

    }
}
