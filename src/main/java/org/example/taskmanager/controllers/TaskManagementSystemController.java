package org.example.taskmanager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uk.gov.hmcts.taskmanager.api.controller.TaskManagementSystemApi;
import uk.gov.hmcts.taskmanager.domain.*;

import java.text.ParseException;

import static org.springframework.http.ResponseEntity.ok;

public class TaskManagementSystemController implements TaskManagementSystemApi {

    private CreateTaskController createTaskController;

    @Autowired
    public TaskManagementSystemController(CreateTaskController createTaskController) {
        this.createTaskController = createTaskController;
    }

    @Override
    @RequestMapping(value = "/Task", method = RequestMethod.POST)
    public ResponseEntity<SuccessResponse> createTask(String transactionId, CreateTaskRequest taskRequest) {

            return ok(createTaskController.createTask(transactionId, taskRequest));
    }

    @Override
    public ResponseEntity<Task> getTask(String transactionId) {
        return null;
    }

    @Override
    public ResponseEntity<TaskResponse> getTasks(String transactionId) {
        return null;
    }

    @Override
    @RequestMapping(value = "/Task", method = RequestMethod.PUT)
    public ResponseEntity<SuccessResponse> updateStatus(String transactionId, UpdateStatusRequest body) {
        return null;
    }

}
