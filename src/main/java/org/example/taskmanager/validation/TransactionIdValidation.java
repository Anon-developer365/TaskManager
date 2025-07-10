package org.example.taskmanager.validation;

import org.example.taskmanager.exceptions.TaskValidationErrorException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TransactionIdValidation {

    private static final String TRANSACTION_ID_REGEX = "^0-9a-zA-Z-{1,10}$";

    public void validateTransactionId(String taskId) {
        final List<String> allErrors = new ArrayList<>();
        if (taskId.isBlank() || taskId.isEmpty()) {
            allErrors.add("Transaction ID is blank");
        } else {
            final Pattern pattern = Pattern.compile(TRANSACTION_ID_REGEX);
            final Matcher matcher = pattern.matcher(taskId);
            if (!matcher.find()) {
                allErrors.add("Transaction ID does not match the pattern 0-9a-zA-Z-{1,10}");
            }
        }
        if (!allErrors.isEmpty()) {
            throw new TaskValidationErrorException(allErrors.toString());
        }
    }
}
