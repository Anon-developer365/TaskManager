package org.example.taskmanager.validation;

import org.example.taskmanager.exceptions.TaskValidationErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.taskmanager.domain.CreateTaskRequest;
import uk.gov.hmcts.taskmanager.domain.UpdateStatusRequest;

import java.util.ArrayList;
import java.util.List;

@Service
public class ValidationOrchestration {

    private final IdValidation idValidation;

    private final StatusValidation statusValidation;

    private final TaskValidation taskValidation;

    @Autowired
    public ValidationOrchestration(IdValidation idValidation, StatusValidation statusValidation, TaskValidation taskValidation) {
        this.taskValidation = taskValidation;
        this.idValidation = idValidation;
        this.statusValidation = statusValidation;
    }

    public void generalTaskValidation(String transactionId, String taskId) {
        final List<String> allErrors = new ArrayList<>();
        allErrors.addAll(idValidation.validateId("Transaction", transactionId));
        allErrors.addAll(idValidation.validateId("Task", taskId));
        checkErrorList(allErrors);
    }

    public void createTaskValidation(String transactionId, CreateTaskRequest createTaskRequest) {
        final List<String> allErrors = new ArrayList<>();
        allErrors.addAll(idValidation.validateId("Transaction", transactionId));
        allErrors.addAll(taskValidation.verifyTask(createTaskRequest.getTitle(), createTaskRequest.getTaskDescription(),
                createTaskRequest.getDueDate()));
        allErrors.addAll(statusValidation.statusCheck(createTaskRequest.getStatus()));
        checkErrorList(allErrors);
    }

    public void updateStatusValidation(String transactionId, UpdateStatusRequest updateStatusRequest) {
        final List<String> allErrors = new ArrayList<>();
        allErrors.addAll(idValidation.validateId("Transaction", transactionId));
        allErrors.addAll(idValidation.validateId("Task", updateStatusRequest.getId()));
        allErrors.addAll(statusValidation.statusCheck(updateStatusRequest.getStatus()));
        checkErrorList(allErrors);
    }

    public void getAllTaskValidation(String transactionId) {
        final List<String> allErrors = new ArrayList<>(idValidation.validateId("Transaction", transactionId));
        checkErrorList(allErrors);
    }

    private void checkErrorList(List<String> allErrors) {
        if (!allErrors.isEmpty()) {
            throw new TaskValidationErrorException(allErrors.toString());
        }
    }
}
