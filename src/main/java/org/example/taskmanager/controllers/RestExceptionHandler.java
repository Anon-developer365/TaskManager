package org.example.taskmanager.controllers;

import lombok.ToString;
import org.example.taskmanager.exceptions.TaskValidationErrorException;
import org.example.taskmanager.pojo.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@ToString
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Customise the response for MissingRequestHeaderException.
     * This occurs when spring boot detects that a required header is missing.
     *
     * @param exception the exception that was thrown.
     * @return a {@code ResponseEntity} instance.
     */
    @ResponseBody
    @ExceptionHandler(TaskValidationErrorException.class)
    public ResponseEntity<ErrorResponse> handleTaskValidationException(final TaskValidationErrorException exception) {
        List<String> errors = new ArrayList<>();
        errors.add(exception.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(errors);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}
