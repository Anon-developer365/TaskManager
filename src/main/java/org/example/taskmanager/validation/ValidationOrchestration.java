package org.example.taskmanager.validation;

import org.example.taskmanager.exceptions.TaskValidationErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.taskmanager.domain.CreateTaskRequest;
import uk.gov.hmcts.taskmanager.domain.UpdateStatusRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Orchestration class for the validation for each service.
 */
@Service
public class ValidationOrchestration {

    /**
     * ID validation service.
     */
    private final IdValidation idValidation;

    /**
     * Status Validation service.
     */
    private final StatusValidation statusValidation;

    /**
     * Task Validation service.
     */
    private final TaskValidation taskValidation;

    /**
     * Autowired constructor for validation orchestration service.
     *
     * @param anIdValidation id validation service.
     * @param aStatusValidation status validation service.
     * @param aTaskValidation task validation service.
     */
    @Autowired
    public ValidationOrchestration(final IdValidation anIdValidation,
                                   final StatusValidation aStatusValidation,
                                   final TaskValidation aTaskValidation) {
        this.taskValidation = aTaskValidation;
        this.idValidation = anIdValidation;
        this.statusValidation = aStatusValidation;
    }

    /**
     * Method to validate transaction ID and task ID.
     *
     * @param transactionId transaction ID to be validated.
     * @param taskId task ID to be validated.
     */
    public void generalTaskValidation(final String transactionId,
                                      final String taskId) {
        final List<String> allErrors = new ArrayList<>();
        allErrors.addAll(idValidation.validateId("Transaction", transactionId));
        allErrors.addAll(idValidation.validateId("Task", taskId));
        checkErrorList(allErrors);
    }

    /**
     * Method to validate transaction ID and Create
     * task request details (title, description, status, due date).
     *
     * @param transactionId transaction ID to be validated.
     * @param createTaskRequest create task request to be validated.
     */
    public void createTaskValidation(final String transactionId,
                                     final CreateTaskRequest
                                             createTaskRequest) {
        final List<String> allErrors = new ArrayList<>();
        allErrors.addAll(idValidation.validateId("Transaction", transactionId));
        allErrors.addAll(taskValidation.verifyTask(
                createTaskRequest.getTitle(),
                createTaskRequest.getTaskDescription(),
                createTaskRequest.getDueDate()));
        allErrors.addAll(
                statusValidation.statusCheck(createTaskRequest.getStatus()));
        checkErrorList(allErrors);
    }

    /**
     * Method to validated transaction ID and
     * update status request details (task ID and new status).
     *
     * @param transactionId transaction ID to be validated.
     * @param updateStatusRequest update status request to be validated.
     */
    public void updateStatusValidation(final String transactionId,
                                       final UpdateStatusRequest
                                               updateStatusRequest) {
        final List<String> allErrors = new ArrayList<>();
        allErrors.addAll(
                idValidation.validateId("Transaction", transactionId));
        allErrors.addAll(
                idValidation.validateId("Task", updateStatusRequest.getId()));
        allErrors.addAll(
                statusValidation.statusCheck(updateStatusRequest.getStatus()));
        checkErrorList(allErrors);
    }

    /**
     * Method to validate transaction ID only.
     *
     * @param transactionId transaction ID to be validated.
     */
    public void getAllTaskValidation(final String transactionId) {
        final List<String> allErrors = new ArrayList<>(
                idValidation.validateId("Transaction", transactionId));
        checkErrorList(allErrors);
    }

    /**
     * Method to check if error lists are empty (no errors found).
     *
     * @param allErrors a list of all errors found in the request.
     */
    private void checkErrorList(final List<String> allErrors) {
        if (!allErrors.isEmpty()) {
            throw new TaskValidationErrorException(allErrors.toString());
        }
    }
}
