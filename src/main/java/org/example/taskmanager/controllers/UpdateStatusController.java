package org.example.taskmanager.controllers;

import org.example.taskmanager.service.UpdateStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

/**
 * Controller to update the status of an existing task.
 */
@RestController
public class UpdateStatusController {

    /**
     * Update status service to update the status in the database.
     */
    private final UpdateStatus updateStatus;

    /**
     * Autowired constructor for update service controller. ]
     *
     * @param updateStatus update status service.
     */
    @Autowired
    public UpdateStatusController(UpdateStatus updateStatus) {
        this.updateStatus = updateStatus;
    }

    /**
     * Method to update the status of an existing task.
     *
     * @param Id id of the task in the database
     * @param status the new status of the task.
     * @return a success message with the updated status.
     */
    @RequestMapping(value = "/updateStatus", method = RequestMethod.PUT)
    public ResponseEntity<String> updateStatus(String Id, String status) {
        updateStatus.updateStatus(Id, status);
        return ok("status updated to " + status);
    }
}

