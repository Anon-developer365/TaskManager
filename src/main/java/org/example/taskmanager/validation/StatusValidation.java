package org.example.taskmanager.validation;

import io.micrometer.common.util.StringUtils;
import org.example.taskmanager.exceptions.TaskValidationErrorException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * class to validate status of a task.
 */
@Service
public class StatusValidation {
    /**
     * Java regex to be used when validating status
     */
    private static final String STATUS_REGEX = "\\w";

    /**
     * Method to validate status of a task
     *
     * @param status the status to be validated.
     */
    public void statusCheck(String status) {
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
        if (!errors.isEmpty()) {
            throw new TaskValidationErrorException(errors.toString());
        }
    }

}
