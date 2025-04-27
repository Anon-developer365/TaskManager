package org.example.taskmanager.controllers;

import org.example.taskmanager.exceptions.EmptyTaskException;
import org.example.taskmanager.pojo.ErrorResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Objects;

public class RestExceptionHandlerTest extends ResponseEntityExceptionHandler {

    private RestExceptionHandler restExceptionHandler;

    @Test
    void testEmptyTaskException() {
        EmptyTaskException emptyTaskException = new EmptyTaskException("Task is empty");
        restExceptionHandler = new RestExceptionHandler();
        ResponseEntity<ErrorResponse> responseEntity = restExceptionHandler.handleEmptyTaskException(emptyTaskException);
        assert(Objects.requireNonNull(responseEntity.getBody()).getErrors().get(0).equals(emptyTaskException.getMessage()));

    }
}
