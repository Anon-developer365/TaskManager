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


    public void verifyTask(Task task) {
        final List<String> allErrors = new ArrayList<>();
        allErrors.addAll(idCheck(task));
        allErrors.addAll(titleCheck(task));
        allErrors.addAll(descriptionCheck(task));
        if (!allErrors.isEmpty()) {
            throw new EmptyTaskException(allErrors.toString());
        }
    }

    private List<String> idCheck(Task task) {
        final List<String> errors = new ArrayList<>();
        if (StringUtils.isBlank(task.getId())) {
            errors.add("Task ID is empty");
        }
        return errors;
    }

    private List<String> titleCheck(Task task) {
        final List<String> errors = new ArrayList<>();
        if (StringUtils.isBlank(task.getTitle())) {
            errors.add("Task title is empty");
        } else {
            final Pattern pattern = Pattern.compile(TITLE_REGEX);
            final Matcher matcher = pattern.matcher(task.getTitle());
            if (!matcher.find()) {
                errors.add("Task title doesnt match pattern a-zA-Z0-9");
            }

        }
        return errors;
    }

    private List<String> descriptionCheck(Task task) {
        final List<String> errors = new ArrayList<>();
        if (!StringUtils.isBlank(task.getDescription())) {
            final Pattern pattern = Pattern.compile(DESCRIPTION_REGEX);
            final Matcher matcher = pattern.matcher(task.getDescription());
            if (!matcher.find()) {
                errors.add("Task description doesnt match pattern a-zA-Z0-9");
            }

        }
        return errors;
    }



}
