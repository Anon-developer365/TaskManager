package org.example.taskmanager.controllers;

import io.micrometer.common.util.StringUtils;
import lombok.ToString;
import org.example.taskmanager.exceptions.EmptyTaskException;
import org.example.taskmanager.pojo.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ToString
public class TaskValidation {

    private static final String TITLE_REGEX = "\\w";

    private static final String DESCRIPTION_REGEX = "\\w";

    private static final String STATUS_REGEX = "\\w";


    public void verifyTask(String title, String description, String status, String dueDate) {
        final List<String> allErrors = new ArrayList<>();
        allErrors.addAll(titleCheck(title));
        allErrors.addAll(descriptionCheck(description));
        allErrors.addAll(statusCheck(status));
        if (!allErrors.isEmpty()) {
            throw new EmptyTaskException(allErrors.toString());
        }
    }

    private List<String> titleCheck(String title) {
        final List<String> errors = new ArrayList<>();
        if (StringUtils.isBlank(title)) {
            errors.add("Task title is empty");
        } else {
            final Pattern pattern = Pattern.compile(TITLE_REGEX);
            final Matcher matcher = pattern.matcher(title);
            if (!matcher.find()) {
                errors.add("Task title doesnt match pattern a-zA-Z0-9");
            }

        }
        return errors;
    }

    private List<String> descriptionCheck(String description) {
        final List<String> errors = new ArrayList<>();
        if (!StringUtils.isBlank(description)) {
            final Pattern pattern = Pattern.compile(DESCRIPTION_REGEX);
            final Matcher matcher = pattern.matcher(description);
            if (!matcher.find()) {
                errors.add("Task description doesnt match pattern a-zA-Z0-9");
            }

        }
        return errors;
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



}
