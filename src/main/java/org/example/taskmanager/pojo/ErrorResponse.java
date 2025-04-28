package org.example.taskmanager.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * POJO class for error response object.
 */
@AllArgsConstructor
@Data
@ToString
public class ErrorResponse {
    private List<String> errors;

}
