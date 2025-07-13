package org.example.taskmanager.controllers;

import org.example.taskmanager.service.UpdateStatus;
import org.example.taskmanager.validation.ValidationOrchestration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.taskmanager.domain.SuccessResponse;
import uk.gov.hmcts.taskmanager.domain.UpdateStatusRequest;


/**
 * Controller to update the status of an existing task.
 */
@Service
public class UpdateStatusOrchestration {

    /**
     * Update status service to update the status in the database.
     */
    private final UpdateStatus updateStatus;

    /**
     * service to validate submitted data.
     */
    private final ValidationOrchestration validationOrchestration;


    /**
     * Autowired constructor for update service controller.
     *
     * @param updateStatus update status service.
     * @param validationOrchestration validation service.
     */
    @Autowired
    public UpdateStatusOrchestration(UpdateStatus updateStatus, ValidationOrchestration validationOrchestration) {
        this.updateStatus = updateStatus;
        this.validationOrchestration = validationOrchestration;

    }

    /**
     * Method to update the status of an existing task.
     *
     * @param transactionId ID of the transaction.
     * @param updateRequest update request containing the status and id of the task to be updated.
     * @return a success message with the updated status.
     */
    public SuccessResponse updateStatus(String transactionId, UpdateStatusRequest updateRequest) {
        SuccessResponse successResponse = new SuccessResponse();
        validationOrchestration.updateStatusValidation(transactionId, updateRequest);
        updateStatus.updateStatus(updateRequest);
        successResponse.setId(updateRequest.getId());
        successResponse.setMessage("Status updated to: \"" + updateRequest.getStatus()+ "\"");
        return successResponse;
    }
}

