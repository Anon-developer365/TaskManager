package org.example.taskmanager.service;


import jakarta.persistence.EntityNotFoundException;
import org.example.taskmanager.exceptions.TaskValidationErrorException;
import org.example.taskmanager.validation.IdValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.taskmanager.domain.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Get a task class retrieves a task if it is present in the database or returns an error message.
 */
@Service
public class GetATask {

    /**
     * Task repository for service.
     */
    private final TaskRepository taskRepository;

    /**
     * Validation service for Id's
     */
    private final IdValidation idValidation;


    /**
     * Autowired constructor for Get a task service.
     *
     * @param taskRepository task repository.
     * @param idValidation validation service.
     */
    @Autowired
    public GetATask(TaskRepository taskRepository, IdValidation idValidation) {
        this.taskRepository = taskRepository;
        this.idValidation = idValidation;
    }

    /**
     * Method to retrieve a task.
     *
     * @param transactionId ID of the transaction
     * @param taskId the id of the task to be returned.
     * @return the task that wants to be retrieved.
     */
    public Task getATask(String transactionId, String taskId) {
        validate(transactionId, taskId);
        return getTask(taskId);

    }

    /**
     * Method to validate transaction and task ID.
     *
     * @param transactionId transaction ID to be validated.
     * @param taskId Task ID to be validated.
     */

    private void validate(String transactionId, String taskId) {
        idValidation.validateId("Transaction", transactionId);
        idValidation.validateId("Task", taskId);
    }

    /**
     * Method to get the task from the database.
     *
     * @param taskId ID of the task to be retrieved.
     * @return the task found in the database.
     */
    private Task getTask(String taskId) {
        Task transformedTask = new Task();
        Optional<org.example.taskmanager.pojo.Task> foundTask;
        final List<String> allErrors = new ArrayList<>();

        try {
            foundTask = taskRepository.findById(taskId);

            if (foundTask.isPresent()) {
                transformedTask.setId(foundTask.get().getId());
                transformedTask.setTitle(foundTask.get().getTitle());
                transformedTask.setStatus(foundTask.get().getStatus());
                transformedTask.setTaskDescription(foundTask.get().getDescription());
                transformedTask.setDueDate(foundTask.get().getDueDate());
            } else {
                allErrors.add("Task with ID " + taskId + " not found");
            }
        } catch (EntityNotFoundException exception){
            allErrors.add("Task with ID " + taskId + " not found");
        }
        if(!allErrors.isEmpty()) {
            throw new TaskValidationErrorException(allErrors.toString());
        }
        return transformedTask;
    }

}
