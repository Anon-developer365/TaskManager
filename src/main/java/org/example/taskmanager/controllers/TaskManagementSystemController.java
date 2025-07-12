package org.example.taskmanager.controllers;

import jakarta.validation.Valid;
import org.example.taskmanager.service.DeleteTask;
import org.example.taskmanager.service.GetATask;
import org.example.taskmanager.service.RetrieveTasks;
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

/**
 * Rest controller for the task manager end points.
 * Implements the task management system api.
 */
@RestController
public class TaskManagementSystemController implements TaskManagementSystemApi {

    /**
     * Get a task service for get Task end point.
     */
    private final GetATask getATask;

    /**
     * Create task orchestration service for Task end point.
     */
    private final CreateTaskOrchestration createTaskOrchestration;

    /**
     * Retrieve tasks service for get all tasks end point.
     */
    private final RetrieveTasks getAllTasks;

    /**
     * Update status orchestration to orchestrate updating a status for update status end point.
     */
    private final UpdateStatusOrchestration updateStatusOrchestration;

    /**
     * Delete task service for delete task end point.
     */
    private final DeleteTask deleteTask;


    /**
     * Autowired controller for rest controller.
     *
     * @param createTaskOrchestration create a task orchestration service.
     * @param getATask get a task service.
     * @param getAllTasks get all tasks service.
     * @param updateStatusOrchestration update status service.
     * @param deleteTask delete task service.
     */
    @Autowired
    public TaskManagementSystemController(CreateTaskOrchestration createTaskOrchestration, GetATask getATask,
                                          RetrieveTasks getAllTasks, UpdateStatusOrchestration updateStatusOrchestration,
                                          DeleteTask deleteTask) {
        this.createTaskOrchestration = createTaskOrchestration;
        this.getATask = getATask;
        this.getAllTasks = getAllTasks;
        this.updateStatusOrchestration = updateStatusOrchestration;
        this.deleteTask = deleteTask;
    }

    /**
     * Overridden method from API
     * @return null
     */
    @Override
    public TaskManagementSystemApiDelegate getDelegate() {
        return null;
    }

    /**
     * Overridden method from API to add a task to the database.
     *
     * @param transactionId ID of the transaction.
     * @param taskRequest task request object containing the details required to create a task in the database.
     * @return response entity containing a success response with the ID of the new task create and a success message.
     */
    @Override
    @RequestMapping(value = "/Task", method = RequestMethod.POST)
    public ResponseEntity<SuccessResponse> createTask(@Valid String transactionId, @Valid CreateTaskRequest taskRequest) {
            return ok(createTaskOrchestration.createTask(transactionId, taskRequest));
    }

    /**
     * Overridden method from API to get a task from the database.
     *
     * @param transactionId ID of the transaction.
     * @param taskId ID of the task to be returned.
     * @return response entity containing a Task object with the details of the task if found.
     */
    @Override
    @RequestMapping(value = "/Task", method = RequestMethod.GET)
    public ResponseEntity<Task> getTask(@Valid String transactionId, @Valid String taskId) {
        return ok(getATask.getATask(transactionId, taskId));
    }

    /**
     * Overridden method from API to get all tasks from the database.
     *
     * @param transactionId ID of the transaction
     * @return response entity containing a list of all Task objects in the database.
     */
    @Override
    @RequestMapping(value = "/allTasks", method = RequestMethod.GET)
    public ResponseEntity<TaskResponse> getTasks(@Validated String transactionId) {
        return ok(getAllTasks.getAllTasks(transactionId));
    }

    /**
     * Overridden method from API to update the status of a task in the database.
     *
     * @param transactionId ID of the transaction.
     * @param statusRequest status request object containing the ID of the task and the new status.
     * @return response entity containing a success response with the ID of the task and a success message.
     */
    @Override
    @RequestMapping(value = "/Task", method = RequestMethod.PUT)
    public ResponseEntity<SuccessResponse> updateStatus(@Valid String transactionId, @Valid UpdateStatusRequest statusRequest) {
        return ok(updateStatusOrchestration.updateStatus(transactionId, statusRequest));
    }

    /**
     * Overridden method from API to delete a task from the database.
     *
     * @param transactionId ID of the transaction.
     * @param taskId ID of the task to be deleted.
     * @return response entity containing a success response with the ID of the task deleted and a success message.
     */
    @Override
    @RequestMapping(value = "/Task", method = RequestMethod.DELETE)
    public ResponseEntity<SuccessResponse> deleteTask(@Valid String transactionId, @Valid String taskId) {
        return ok(deleteTask.deleteTask(transactionId, taskId));
    }

}
