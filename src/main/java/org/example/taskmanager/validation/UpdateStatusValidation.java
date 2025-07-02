package org.example.taskmanager.validation;

import io.micrometer.common.util.StringUtils;
import lombok.ToString;
import org.example.taskmanager.exceptions.TaskValidationErrorException;
import org.example.taskmanager.validation.StatusValidation;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final StatusValidation statusValidation;

    @Autowired
    public UpdateStatusValidation(StatusValidation statusValidation) {
        this.statusValidation = statusValidation;
    }

    /**
     * Method to validate update status request.
     *
     * @param id the ID to be validated
     * @param status the new  status to be validated.
     */
    public void verifyStatus(String id, String status) {
        final List<String> allErrors = new ArrayList<>();
        allErrors.addAll(statusValidation.statusCheck(status));
        allErrors.addAll(idCheck(id));
        if (!allErrors.isEmpty()) {
            throw new TaskValidationErrorException(allErrors.toString());
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
