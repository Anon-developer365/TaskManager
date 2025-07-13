package org.example.taskmanager.validation;

import org.example.taskmanager.exceptions.TaskValidationErrorException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ID validation Service class.
 */
@Service
public class IdValidation {

    /**
     * Regex for ID validation.
     */
    private static final String ID_REGEX = "^[0-9a-zA-Z-]{1,10}$";

    /**
     * Method to validate ID.
     *
     * @param type type of ID being validated.
     * @param id ID to be validated.
     */
    public List<String> validateId(String type, String id) {
        final List<String> allErrors = new ArrayList<>();
        if (id.isBlank() || id.isEmpty()) {
            allErrors.add(type + " ID is blank");
        } else {
            final Pattern pattern = Pattern.compile(ID_REGEX);
            final Matcher matcher = pattern.matcher(id);
            if (!matcher.find()) {
                allErrors.add(type + " ID does not match the pattern 0-9a-zA-Z-{1,10}");
            }
        }
        return allErrors;
    }
}
