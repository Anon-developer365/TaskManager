package org.example.taskmanager.controllers;

import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.hmcts.taskmanager.api.controller.TaskManagementSystemApi;
import uk.gov.hmcts.taskmanager.domain.*;

import java.text.ParseException;

import static org.springframework.http.ResponseEntity.ok;


public class TaskManagementSystemController implements TaskManagementSystemApi {

    private CreateTaskController createTaskController;

    private RetrieveTaskController retrieveTaskController;

    private RetrieveAllTasksController allTasksController;

    private UpdateStatusController updateStatusController;
    @Autowired
    public TaskManagementSystemController(CreateTaskController createTaskController, RetrieveTaskController retrieveTaskController,
                                          RetrieveAllTasksController allTasksController, UpdateStatusController updateStatusController) {
        this.createTaskController = createTaskController;
        this.retrieveTaskController = retrieveTaskController;
        this.allTasksController = allTasksController;
        this.updateStatusController = updateStatusController;
    }

    @Override
    @RequestMapping(value = "/Task", method = RequestMethod.POST)
    public ResponseEntity<SuccessResponse> createTask(String transactionId, CreateTaskRequest taskRequest) {

            return ok(createTaskController.createTask(transactionId, taskRequest));
    }

    @Override
    @RequestMapping(value = "/Task", method = RequestMethod.GET)
    public ResponseEntity<Task> getTask(String transactionId, String taskId) {
        return ok(retrieveTaskController.getTask(taskId));
    }

    @Override
    @RequestMapping(value = "/allTasks", method = RequestMethod.GET)
    public ResponseEntity<TaskResponse> getTasks(String transactionId) {
        return ok(allTasksController.getAllTasks());
    }

    @Override
    @RequestMapping(value = "/Task", method = RequestMethod.PUT)
    public ResponseEntity<SuccessResponse> updateStatus(String transactionId, UpdateStatusRequest body) {

        return ok(updateStatusController.updateStatus(body));
    }

}
