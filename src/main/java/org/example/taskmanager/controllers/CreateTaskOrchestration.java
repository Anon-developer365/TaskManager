package org.example.taskmanager.controllers;

import org.example.taskmanager.pojo.Task;
import org.example.taskmanager.service.CreateTask;
import org.example.taskmanager.service.SaveTask;
import org.example.taskmanager.validation.TaskValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.taskmanager.domain.CreateTaskRequest;
import uk.gov.hmcts.taskmanager.domain.SuccessResponse;



/**
 * Rest controller for create task end point to create and save a new task in the database.
 */
@Service
public class CreateTaskOrchestration {
    /**
     * service to create a new task, assign an ID and format the date.
     */
    private final CreateTask createTask;

    /**
     * service to save task in the database.
     */
    private final SaveTask saveTask;

    /**
     * service to validate submitted task data.
     */
    private final TaskValidation validation;

    /**
     * Autowired constructor for create task controller.
     *
     * @param createTask service to create a task with local date.
     * @param saveTask save task service to save task data in the database.
     * @param validation task validation service.
     */
    @Autowired
    public CreateTaskOrchestration(final CreateTask createTask, final SaveTask saveTask, final TaskValidation validation) {
        this.createTask = createTask;
        this.saveTask = saveTask;
        this.validation = validation;
    }

    /**
     * Method to call required services in order to create a task and save this within the database.
     *
     * @param transactionId ID of the transaction made to the service.
     * @param taskRequest request data of the task to be created and saved in the database.
     * @return a string containing the newly created task id with a success message.
     */

    public SuccessResponse createTask(String transactionId, CreateTaskRequest taskRequest) {
        validation.verifyTask(taskRequest.getTitle(), taskRequest.getTaskDescription(), taskRequest.getStatus(), taskRequest.getDueDate());
        Task task = createTask.createNewTask(taskRequest.getTitle(), taskRequest.getTaskDescription(), taskRequest.getStatus(), taskRequest.getDueDate());
        saveTask.saveData(task);
        SuccessResponse response = new SuccessResponse();
        response.setId(task.getId());
        response.setMessage("Task Created successfully");
        return response;
    }

}
