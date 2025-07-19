package org.example.taskmanager.validation;

import io.micrometer.common.util.StringUtils;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class to validate a task.
 */
@Service
@ToString
public class TaskValidation {

    /**
     * Date checker service.
     */
    private final DateChecker dateChecker;

    /**
     * Regex for title.
     */
    private static final String TITLE_REGEX = "\\w";

    /**
     * Regex for description.
     */
    private static final String DESCRIPTION_REGEX = "\\w";

    /**
     * Autowired constructor for Task Validation.
     * @param aDateChecker Date checker service.
     */
    @Autowired
    public TaskValidation(final DateChecker aDateChecker) {
        this.dateChecker = aDateChecker;
    }

    /**
     * Method to verify task details.
     *
     * @param title task title to be validated
     * @param description description, if present to be validated.
     * @param dueDate due date to be validated.
     * @return a list of errors found or
     * an empty list if there are no errors.
     */
    public List<String> verifyTask(final String title,
                                   final String description,
                                   final LocalDateTime dueDate) {
        final List<String> allErrors = new ArrayList<>();
        allErrors.addAll(titleCheck(title));
        allErrors.addAll(descriptionCheck(description));
        allErrors.addAll(dueDateCheck(dueDate));
        return allErrors;
    }

    /**
     * Method to validate title.
     *
     * @param title title to be validated
     * @return returns either an empty list
     * or a list containing title validation errors.
     */
    private List<String> titleCheck(final String title) {
        final List<String> errors = new ArrayList<>();
        if (StringUtils.isBlank(title)) {
            errors.add("Task title is blank");
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
     * Method to validate description.
     *
     * @param description description to be validated.
     * @return returns either an empty list or
     * a list containing description validation errors.
     */
    private List<String> descriptionCheck(final String description) {
        final List<String> errors = new ArrayList<>();
        if (!StringUtils.isBlank(description)) {
            final Pattern pattern = Pattern.compile(DESCRIPTION_REGEX);
            final Matcher matcher = pattern.matcher(description);
            if (!matcher.find()) {
                errors.add("Task description does "
                        + "not match the pattern a-zA-Z0-9");
            }

        }
        return errors;
    }

    /**
     * Method to validate due date.
     *
     * @param dueDate due date to be validated.
     * @return returns either an empty list
     * or a list containing due date validation errors.
     */
    private List<String> dueDateCheck(final LocalDateTime dueDate) {
        final List<String> errors = new ArrayList<>();
        if (!(dueDate == null)) {
            if (dateChecker.checkDateIsBankHoliday(dueDate)) {
                errors.add("Task due date is a Bank Holiday.");
            } else if (dateChecker.checkDateIsWeekend(dueDate)) {
                errors.add("Task due date is a Weekend");
            }
        } else {
                errors.add("Task due date is Blank");
        }
        return errors;
    }

}
