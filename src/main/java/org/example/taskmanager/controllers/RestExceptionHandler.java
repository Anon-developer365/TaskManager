package org.example.taskmanager.controllers;

import lombok.ToString;
import org.example.taskmanager.exceptions.TaskNotFoundException;
import org.example.taskmanager.exceptions.TaskValidationErrorException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import uk.gov.hmcts.taskmanager.domain.ErrorResponse;

import java.time.LocalDateTime;

@ControllerAdvice
@ToString
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Customise the response for TaskValidationException.
     *
     * @param exception the exception that was thrown.
     * @return a {@code ResponseEntity} instance.
     */
    @ResponseBody
    @ExceptionHandler(TaskValidationErrorException.class)
    public ResponseEntity<ErrorResponse> handleTaskValidationException(final TaskValidationErrorException exception) {
        LocalDateTime localDateTime = LocalDateTime.now();
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setError("Validation Error");
        errorResponse.setDate(localDateTime);
        errorResponse.setMessage(exception.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Customise the response for TaskNotFoundException.
     * This occurs when spring boot detects that a required header is missing.
     *
     * @param exception the exception that was thrown.
     * @return a {@code ResponseEntity} instance.
     */
    @ResponseBody
    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTaskNotFoundException(final TaskNotFoundException exception) {
        LocalDateTime localDateTime = LocalDateTime.now();
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setError("Task Not Found Error");
        errorResponse.setDate(localDateTime);
        errorResponse.setMessage(exception.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


}
