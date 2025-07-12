package org.example.taskmanager.controllers;

import org.example.taskmanager.service.UpdateStatus;
import org.example.taskmanager.validation.IdValidation;
import org.example.taskmanager.validation.StatusValidation;
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
     * Validation service for Id's
     */
    private final IdValidation idValidation;

    /**
     * class to validate status
     */
    private final StatusValidation statusValidation;

    /**
     * Autowired constructor for update service controller.
     *
     * @param updateStatus update status service.
     * @param idValidation validation service.
     * @param statusValidation status validation service.
     */
    @Autowired
    public UpdateStatusOrchestration(UpdateStatus updateStatus, IdValidation idValidation,
                                     StatusValidation statusValidation) {
        this.updateStatus = updateStatus;
        this.idValidation = idValidation;
        this.statusValidation = statusValidation;

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
        validate(transactionId, updateRequest);
        updateStatus.updateStatus(updateRequest);
        successResponse.setId(updateRequest.getId());
        successResponse.setMessage("Status updated to: \"" + updateRequest.getStatus()+ "\"");
        return successResponse;
    }

    /**
     * Method to validate transactionID and update status request.
     *
     * @param transactionId transactionID to be validated.
     * @param updateRequest update request with task ID and status to be validated.
     */
    private void validate(String transactionId, UpdateStatusRequest updateRequest) {
        idValidation.validateId("Transaction", transactionId);
        idValidation.validateId("Task", updateRequest.getId());
        statusValidation.statusCheck(updateRequest.getStatus());
    }
}

