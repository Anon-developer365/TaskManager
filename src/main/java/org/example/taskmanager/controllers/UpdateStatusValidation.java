package org.example.taskmanager.controllers;

import io.micrometer.common.util.StringUtils;
import org.example.taskmanager.exceptions.EmptyTaskException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateStatusValidation {

    private static final String STATUS_REGEX = "\\w";

    public void verifyStatus(String id, String status) {
        final List<String> allErrors = new ArrayList<>();
        allErrors.addAll(statusCheck(status));
        allErrors.addAll(idCheck(id));
        if (!allErrors.isEmpty()) {
            throw new EmptyTaskException(allErrors.toString());
        }
    }

    private List<String> statusCheck(String status) {
        final List<String> errors = new ArrayList<>();
        if (StringUtils.isBlank(status)) {
            errors.add("Task status is empty");
        } else {
            final Pattern pattern = Pattern.compile(STATUS_REGEX);
            final Matcher matcher = pattern.matcher(status);
            if (!matcher.find()) {
                errors.add("Task status doesnt match pattern a-zA-Z0-9");
            }

        }
        return errors;
    }

    private List<String> idCheck(String id) {
        final List<String> errors = new ArrayList<>();
        if (StringUtils.isBlank(id)) {
            errors.add("Task id is empty");
        }
        return errors;
    }
}
