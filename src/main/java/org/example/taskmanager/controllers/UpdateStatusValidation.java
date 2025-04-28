package org.example.taskmanager.controllers;

import io.micrometer.common.util.StringUtils;
import lombok.ToString;
import org.example.taskmanager.exceptions.EmptyTaskException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@ToString
public class UpdateStatusValidation {

    private StatusValidation statusValidation;

    public void verifyStatus(String id, String status) {
        statusValidation = new StatusValidation();
        final List<String> allErrors = new ArrayList<>();
        allErrors.addAll(statusValidation.statusCheck(status));
        allErrors.addAll(idCheck(id));
        if (!allErrors.isEmpty()) {
            throw new EmptyTaskException(allErrors.toString());
        }
    }

    private List<String> idCheck(String id) {
        final List<String> errors = new ArrayList<>();
        if (StringUtils.isBlank(id)) {
            errors.add("Task id is empty");
        }
        return errors;
    }
}
