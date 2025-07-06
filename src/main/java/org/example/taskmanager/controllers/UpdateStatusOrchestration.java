package org.example.taskmanager.controllers;

import org.example.taskmanager.service.UpdateStatus;
import org.example.taskmanager.validation.UpdateStatusValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
     * service to validate status and id.
     */
    private final UpdateStatusValidation updateStatusValidation;
    /**
     * Autowired constructor for update service controller. ]
     *
     * @param updateStatus update status service.
     */
    @Autowired
    public UpdateStatusOrchestration(UpdateStatus updateStatus, UpdateStatusValidation updateStatusValidation) {
        this.updateStatus = updateStatus;
        this.updateStatusValidation = updateStatusValidation;

    }

    /**
     * Method to update the status of an existing task.
     *
     * @param updateRequest update request containing the status and id of the task to be updated.
     * @return a success message with the updated status.
     */
    @RequestMapping(value = "/Task", method = RequestMethod.PUT)
    public SuccessResponse updateStatus(UpdateStatusRequest updateRequest) {
        SuccessResponse successResponse = new SuccessResponse();
        updateStatusValidation.verifyStatus(updateRequest.getId(), updateRequest.getStatus());
        updateStatus.updateStatus(updateRequest);
        successResponse.setId(updateRequest.getId());
        successResponse.setMessage("Status updated to: " + updateRequest.getStatus());
        return successResponse;
    }
}

