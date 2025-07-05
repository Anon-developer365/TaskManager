package org.example.taskmanager.validation;

import io.micrometer.common.util.StringUtils;
import lombok.ToString;
import org.example.taskmanager.exceptions.TaskValidationErrorException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class to validate a task
 */
@Service
@ToString
public class TaskValidation {

    /**
     * class to validate status and return outcome to task validation.
     */
    private final StatusValidation statusValidation;

    private static final String TITLE_REGEX = "\\w";

    private static final String DESCRIPTION_REGEX = "\\w";

    public TaskValidation(StatusValidation statusValidation) {
        this.statusValidation = statusValidation;
    }

    /**
     * Method to verify task details
     *
     * @param title task title to be validated
     * @param description description, if present to be validated.
     * @param status task status to be validated
     * @param dueDate due date to be validated.
     */
    public void verifyTask(String title, String description, String status, @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date dueDate) {
        final List<String> allErrors = new ArrayList<>();
        allErrors.addAll(titleCheck(title));
        allErrors.addAll(descriptionCheck(description));
        allErrors.addAll(statusValidation.statusCheck(status));
        allErrors.addAll(dueDateCheck(dueDate));
        if (!allErrors.isEmpty()) {
            throw new TaskValidationErrorException(allErrors.toString());
        }
    }

    /**
     * Method to validate title
     *
     * @param title title to be validated
     * @return returns either an empty list or a list containing title validation errors.
     */
    private List<String> titleCheck(String title) {
        final List<String> errors = new ArrayList<>();
        if (StringUtils.isBlank(title)) {
            errors.add("Task title is empty");
        } else {
            final Pattern pattern = Pattern.compile(TITLE_REGEX);
            final Matcher matcher = pattern.matcher(title);
            if (!matcher.find()) {
                errors.add("Task title does not match the pattern a-zA-Z0-9");
            }

        }
        return errors;
    }

    /**
     * Method to validate description
     *
     * @param description description to be validated.
     * @return returns either an empty list or a list containing description validation errors.
     */
    private List<String> descriptionCheck(String description) {
        final List<String> errors = new ArrayList<>();
        if (!StringUtils.isBlank(description)) {
            final Pattern pattern = Pattern.compile(DESCRIPTION_REGEX);
            final Matcher matcher = pattern.matcher(description);
            if (!matcher.find()) {
                errors.add("Task description does not match the pattern a-zA-Z0-9");
            }

        }
        return errors;
    }

    /**
     * Method to validate due date.
     *
     * @param dueDate due date to be validated.
     * @return returns either an empty list or a list containing due date validation errors.
     */
    private List<String> dueDateCheck(Date dueDate) {
        final List<String> errors = new ArrayList<>();
        if (dueDate == null) {
            errors.add("Task due date is empty");
        }
        return errors;
    }

}
