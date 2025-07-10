package org.example.taskmanager.controllers;

import jakarta.validation.Valid;
import org.example.taskmanager.service.DeleteTask;
import org.example.taskmanager.service.GetATask;
import org.example.taskmanager.service.RetrieveTasks;
import org.example.taskmanager.validation.TaskIdValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.hmcts.taskmanager.api.controller.TaskManagementSystemApi;
import uk.gov.hmcts.taskmanager.api.controller.TaskManagementSystemApiDelegate;
import uk.gov.hmcts.taskmanager.domain.*;


import static org.springframework.http.ResponseEntity.ok;

@RestController
public class TaskManagementSystemController implements TaskManagementSystemApi {

    private final GetATask getATask;
    private final CreateTaskOrchestration createTaskOrchestration;

    private final RetrieveTasks getAllTasks;

    private final UpdateStatusOrchestration updateStatusOrchestration;

    private final DeleteTask deleteTask;

    /**
     * Validation service for TaskId
     */
    private final TaskIdValidation taskIdValidation;
    @Autowired
    public TaskManagementSystemController(CreateTaskOrchestration createTaskOrchestration, GetATask getATask,
                                          RetrieveTasks getAllTasks, UpdateStatusOrchestration updateStatusOrchestration,
                                          DeleteTask deleteTask, TaskIdValidation taskIdValidation) {
        this.createTaskOrchestration = createTaskOrchestration;
        this.getATask = getATask;
        this.getAllTasks = getAllTasks;
        this.updateStatusOrchestration = updateStatusOrchestration;
        this.deleteTask = deleteTask;
        this.taskIdValidation = taskIdValidation;
    }

    @Override
    public TaskManagementSystemApiDelegate getDelegate() {
        return null;
    }

    @Override
    @RequestMapping(value = "/Task", method = RequestMethod.POST)
    public ResponseEntity<SuccessResponse> createTask(@Valid String transactionId, @Valid CreateTaskRequest taskRequest) {

            return ok(createTaskOrchestration.createTask(transactionId, taskRequest));
    }

    @Override
    @RequestMapping(value = "/Task", method = RequestMethod.GET)
    public ResponseEntity<Task> getTask(@Valid String transactionId, @Valid String taskId) {
        taskIdValidation.validateTaskId(taskId);
        return ok(getATask.getATask(taskId));
    }

    @Override
    @RequestMapping(value = "/allTasks", method = RequestMethod.GET)
    public ResponseEntity<TaskResponse> getTasks(@Validated String transactionId) {
        return ok(getAllTasks.getAllTasks());
    }

    @Override
    @RequestMapping(value = "/Task", method = RequestMethod.PUT)
    public ResponseEntity<SuccessResponse> updateStatus(@Valid String transactionId, @Valid UpdateStatusRequest body) {

        return ok(updateStatusOrchestration.updateStatus(body));
    }

    @Override
    @RequestMapping(value = "/Task", method = RequestMethod.DELETE)
    public ResponseEntity<SuccessResponse> deleteTask(@Valid String transactionId, @Valid String taskId) {
        return ok(deleteTask.deleteTask(taskId));
    }

}
