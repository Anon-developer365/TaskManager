package org.example.taskmanager.validation;

import org.example.taskmanager.exceptions.TaskValidationErrorException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UpdateStatusValidationTests {

    @Mock
    private StatusValidation statusValidation;

    @InjectMocks
    private UpdateStatusValidation validation;

    private String status;
    private String id;

    @Test
    void whenStatusIsEmptyAnErrorIsReturned() {
        validation = new UpdateStatusValidation(statusValidation);
        status = "";
        id = "1";
        List<String> expectedErrors = new ArrayList<>();
        expectedErrors.add("Task status is empty");
        when(statusValidation.statusCheck(status)).thenThrow(new TaskValidationErrorException(expectedErrors.toString()));

        TaskValidationErrorException thrown = assertThrows(TaskValidationErrorException.class, () -> validation.verifyStatus(id, status));
        assertEquals(expectedErrors.toString(), thrown.getMessage());

    }

    @Test
    void whenStatusDoesNotMatchTheCorrectFormatAnErrorIsReturned() {
        status = "||";
        id = "1";
        List<String> expectedErrors = new ArrayList<>();
        expectedErrors.add("Task status does not match the pattern a-zA-Z0-9");
        when(statusValidation.statusCheck(status)).thenThrow(new TaskValidationErrorException(expectedErrors.toString()));

        TaskValidationErrorException thrown = assertThrows(TaskValidationErrorException.class, () -> validation.verifyStatus(id, status));
        assertEquals(expectedErrors.toString(), thrown.getMessage());

    }

    @Test
    void whenStatusAndIdAreValidNoErrorIsThrown() {
        status = "status";
        id = "1";

        List<String> expectedErrors = new ArrayList<>();
        when(statusValidation.statusCheck(status)).thenReturn(expectedErrors);
        assertDoesNotThrow(() -> validation.verifyStatus(id, status));

    }
}
