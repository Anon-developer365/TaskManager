package org.example.taskmanager.controllers;

import org.example.taskmanager.exceptions.TaskNotFoundException;
import org.example.taskmanager.exceptions.TaskValidationErrorException;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import uk.gov.hmcts.taskmanager.domain.ErrorResponse;

import java.util.Objects;

public class RestExceptionHandlerTest extends ResponseEntityExceptionHandler {

    @Test
    void testTaskValidationErrorException() {
        TaskValidationErrorException taskValidationException = new TaskValidationErrorException("Task is empty");
        RestExceptionHandler restExceptionHandler = new RestExceptionHandler();
        ResponseEntity<ErrorResponse> responseEntity = restExceptionHandler.handleTaskValidationException(taskValidationException);
        assert(Objects.requireNonNull(responseEntity.getBody()).getMessage().equals(taskValidationException.getMessage()));

    }

    @Test
    void testTaskNotFoundException() {
        TaskNotFoundException taskNotFoundException = new TaskNotFoundException("Task is empty");
        RestExceptionHandler restExceptionHandler = new RestExceptionHandler();
        ResponseEntity<ErrorResponse> responseEntity = restExceptionHandler.handleTaskNotFoundException(taskNotFoundException);
        assert(Objects.requireNonNull(responseEntity.getBody()).getMessage().equals(taskNotFoundException.getMessage()));

    }
}
