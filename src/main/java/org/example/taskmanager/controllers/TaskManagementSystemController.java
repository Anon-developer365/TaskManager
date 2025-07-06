package org.example.taskmanager.controllers;

import org.example.taskmanager.service.GetATask;
import org.example.taskmanager.service.RetrieveTasks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<SuccessResponse> createTask(String transactionId, CreateTaskRequest taskRequest) {

            return ok(createTaskOrchestration.createTask(transactionId, taskRequest));
    }

    @Override
    @RequestMapping(value = "/Task", method = RequestMethod.GET)
    public ResponseEntity<Task> getTask(String transactionId, String taskId) {
        return ok(getATask.getATask(taskId));
    }

    @Override
    @RequestMapping(value = "/allTasks", method = RequestMethod.GET)
    public ResponseEntity<TaskResponse> getTasks(String transactionId) {
        return ok(getAllTasks.getAllTasks());
    }

    @Override
    @RequestMapping(value = "/Task", method = RequestMethod.PUT)
    public ResponseEntity<SuccessResponse> updateStatus(String transactionId, UpdateStatusRequest body) {

        return ok(updateStatusOrchestration.updateStatus(body));
    }

}
