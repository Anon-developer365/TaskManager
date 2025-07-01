package org.example.taskmanager.controllers;

import io.micrometer.common.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * class to validate status of a task.
 */
public class StatusValidation {
    /**
     * Java regex to be used when validating status
     */
    private static final String STATUS_REGEX = "\\w";

    /**
     * Method to validate status of a task
     *
     * @param status the status to be validated.
     * @return a list either empty if the status is valid or containing the validation errors.
     */
    public List<String> statusCheck(String status) {
        final List<String> errors = new ArrayList<>();
        if (StringUtils.isBlank(status)) {
            errors.add("Task status is empty");
        } else {
            final Pattern pattern = Pattern.compile(STATUS_REGEX);
            final Matcher matcher = pattern.matcher(status);
            if (!matcher.find()) {
                errors.add("Task status does not match the pattern a-zA-Z0-9");
            }

        }
        return errors;
    }

}
