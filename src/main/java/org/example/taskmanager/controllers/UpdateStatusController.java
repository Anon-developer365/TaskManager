package org.example.taskmanager.controllers;

import org.example.taskmanager.service.UpdateStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
public class UpdateStatusController {

    private final UpdateStatus updateStatus;

    @Autowired
    public UpdateStatusController(UpdateStatus updateStatus) {
        this.updateStatus = updateStatus;
    }

    @RequestMapping(value = "/updateStatus", method = RequestMethod.PUT)
    public ResponseEntity<String> updateStatus(String Id, String status) {
        updateStatus.updateStatus(Id, status);
        return ok("status updated to " + status);
    }
}

