package org.example.taskmanager.controllers;

import org.example.taskmanager.pojo.Task;
import org.example.taskmanager.service.CreateTask;
import org.example.taskmanager.service.SaveTask;

import org.example.taskmanager.validation.ValidationOrchestration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.taskmanager.domain.CreateTaskRequest;
import uk.gov.hmcts.taskmanager.domain.SuccessResponse;

/**
 * Orchestration service for create task.
 */
@Service
public class CreateTaskOrchestration {

    /**
     * service to create a new task and assign an ID.
     */
    private final CreateTask createTask;

    /**
     * service to save task in the database.
     */
    private final SaveTask saveTask;

    /**
     * service to validate submitted task data.
     */
    private final ValidationOrchestration validation;

    /**
     * Autowired constructor for create task controller.
     *
     * @param createATask service to create a task with local date.
     * @param saveATask save task service to save task data in the database.
     * @param aValidation task validation service.
     */
    @Autowired
    public CreateTaskOrchestration(final CreateTask createATask,
                                   final SaveTask saveATask,
                                   final ValidationOrchestration aValidation) {
        this.createTask = createATask;
        this.saveTask = saveATask;
        this.validation = aValidation;
    }

    /**
     * Method to call required services in order
     * to create a task and save this within the database.
     *
     * @param transactionId ID of the transaction
     *                      made to the service.
     * @param taskRequest request data of the
     *                    task to be created and saved in the database.
     * @return a string containing the newly created
     * task id with a success message.
     */

    public SuccessResponse createATask(final String transactionId,
                                      final CreateTaskRequest taskRequest) {
        validation.createTaskValidation(transactionId, taskRequest);
        Task task = createTask.createNewTask(taskRequest.getTitle(),
                taskRequest.getTaskDescription(),
                taskRequest.getStatus(), taskRequest.getDueDate());
        String taskId = saveTask.saveData(task);
        SuccessResponse response = new SuccessResponse();
        response.setId(taskId);
        response.setMessage("Task Created Successfully");
        return response;
    }

}
