package org.example.taskmanager.controllers;

import io.micrometer.common.util.StringUtils;
import lombok.ToString;
import org.example.taskmanager.exceptions.EmptyTaskException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to validate update status request.
 */
@Service
@ToString
public class UpdateStatusValidation {

    /**
     * class to validate status
     */
    private StatusValidation statusValidation;

    /**
     * Method to validate update status request.
     *
     * @param id the ID to be validated
     * @param status the new  status to be validated.
     */
    public void verifyStatus(String id, String status) {
        statusValidation = new StatusValidation();
        final List<String> allErrors = new ArrayList<>();
        allErrors.addAll(statusValidation.statusCheck(status));
        allErrors.addAll(idCheck(id));
        if (!allErrors.isEmpty()) {
            throw new EmptyTaskException(allErrors.toString());
        }
    }

    /**
     * Method to validate ID.
     *
     * @param id ID to be validated.
     * @return returns either an empty list or a list containing ID validation errors.
     */
    private List<String> idCheck(String id) {
        final List<String> errors = new ArrayList<>();
        if (StringUtils.isBlank(id)) {
            errors.add("Task id is empty");
        }
        return errors;
    }
}
