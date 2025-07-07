package org.example.taskmanager.controllers;

import org.example.taskmanager.service.GetATask;
import org.example.taskmanager.service.RetrieveTasks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.hmcts.taskmanager.api.controller.TaskManagementSystemApi;
import uk.gov.hmcts.taskmanager.domain.*;


import static org.springframework.http.ResponseEntity.ok;

@RestController
public class TaskManagementSystemController implements TaskManagementSystemApi {

    private final GetATask getATask;
    private final CreateTaskOrchestration createTaskOrchestration;

    private final RetrieveTasks getAllTasks;

    private final UpdateStatusOrchestration updateStatusOrchestration;
    @Autowired
    public TaskManagementSystemController(CreateTaskOrchestration createTaskOrchestration, GetATask getATask,
                                          RetrieveTasks getAllTasks, UpdateStatusOrchestration updateStatusOrchestration) {
        this.createTaskOrchestration = createTaskOrchestration;
        this.getATask = getATask;
        this.getAllTasks = getAllTasks;
        this.updateStatusOrchestration = updateStatusOrchestration;
    }

    @Override
    @RequestMapping(value = "/Task", method = RequestMethod.POST)
    public ResponseEntity<SuccessResponse> createTask(@Validated String transactionId, @Validated CreateTaskRequest taskRequest) {

            return ok(createTaskOrchestration.createTask(transactionId, taskRequest));
    }

    @Override
    @RequestMapping(value = "/Task", method = RequestMethod.GET)
    public ResponseEntity<Task> getTask(@Validated String transactionId, String taskId) {
        return ok(getATask.getATask(taskId));
    }

    @Override
    @RequestMapping(value = "/allTasks", method = RequestMethod.GET)
    public ResponseEntity<TaskResponse> getTasks(@Validated String transactionId) {
        return ok(getAllTasks.getAllTasks());
    }

    @Override
    @RequestMapping(value = "/Task", method = RequestMethod.PUT)
    public ResponseEntity<SuccessResponse> updateStatus(@Validated String transactionId, @Validated UpdateStatusRequest body) {

        return ok(updateStatusOrchestration.updateStatus(body));
    }

    @Override
    @RequestMapping(value = "/Task", method = RequestMethod.DELETE)
    public ResponseEntity<SuccessResponse> deleteTask(@Validated String transactionId, @Validated String TaskId) {
        return null;
    }

}
