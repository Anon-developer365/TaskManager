package org.example.taskmanager.controllers;

import org.example.taskmanager.exceptions.TaskValidationErrorException;
import org.example.taskmanager.service.UpdateStatus;
import org.example.taskmanager.validation.ValidationOrchestration;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.gov.hmcts.taskmanager.domain.SuccessResponse;
import uk.gov.hmcts.taskmanager.domain.UpdateStatusRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@WebAppConfiguration
@SpringBootTest
public class UpdateStatusOrchestrationTests {

    @Mock
    private UpdateStatus updateStatus;

    @Mock
    private ValidationOrchestration validationOrchestration;

    @InjectMocks
    private UpdateStatusOrchestration updateStatusOrchestration;


    @Test
    void aSuccessMessageIsReceivedWhenDetailsAreProvided() {
        String taskId = "1";

        UpdateStatusRequest updateStatusRequest = new UpdateStatusRequest();
        updateStatusRequest.setId(taskId);
        updateStatusRequest.setStatus("This is a new status");

        String expectedResult = "Status updated to: \"" + updateStatusRequest.getStatus()+"\"";
        updateStatusOrchestration = new UpdateStatusOrchestration(updateStatus, validationOrchestration);
        doNothing().when(validationOrchestration).updateStatusValidation("2", updateStatusRequest);
        when(updateStatus.updateStatus(updateStatusRequest)).thenReturn(true);
        SuccessResponse output = updateStatusOrchestration.updateStatus("2", updateStatusRequest);
        assert output != null;
        assertEquals(expectedResult, output.getMessage());
        assertEquals(updateStatusRequest.getId(), output.getId());

    }

    @Test
    void anExceptionIsThrownWhenThereIsAValidationError() {

        UpdateStatusRequest updateStatusRequest = new UpdateStatusRequest();
        updateStatusRequest.setId("1");
        updateStatusRequest.setStatus("This is a new status");

        updateStatusOrchestration = new UpdateStatusOrchestration(updateStatus, validationOrchestration);
        String expectedError = "validation error";

        TaskValidationErrorException thrown = assertThrows(TaskValidationErrorException.class, () -> {
            doThrow(new TaskValidationErrorException("validation error")).when(validationOrchestration).updateStatusValidation(
                    "2", updateStatusRequest);
            updateStatusOrchestration.updateStatus("2", updateStatusRequest);
        });
        assertEquals(expectedError, thrown.getMessage());

    }
}
