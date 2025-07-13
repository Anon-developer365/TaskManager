package org.example.taskmanager.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.taskmanager.exceptions.TaskNotFoundException;
import org.example.taskmanager.pojo.Task;
import org.example.taskmanager.validation.IdValidation;
import org.example.taskmanager.validation.ValidationOrchestration;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.taskmanager.domain.SuccessResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Delete Task Service to delete a task from the database.
 */
@Service
public class DeleteTask {

    /**
     * Task repository for service.
     */
    private final TaskRepository taskRepository;

    /**
     * Validation service for Id's
     */
    private final ValidationOrchestration validationOrchestration;

    /**
     * Autowired constructor for task repository.
     *
     * @param taskRepository task repository.
     * @param validationOrchestration validation service.
     */
    public DeleteTask(TaskRepository taskRepository,ValidationOrchestration validationOrchestration) {
        this.taskRepository = taskRepository;
        this.validationOrchestration = validationOrchestration;
    }

    /**
     * Method to validate and delete task from the database.
     *
     * @param transactionId ID of the transaction.
     * @param taskId ID of the task to be deleted.
     * @return success response containing the ID of the deleted task and a success message.
     */
    public SuccessResponse deleteTask(String transactionId, String taskId) {
        validationOrchestration.generalTaskValidation(transactionId, taskId);
        return delete(taskId);
    }

    /**
     * Method to delete the task from the database.
     *
     * @param taskId ID of the task to be deleted.
     * @return success response with the ID of the deleted task and a success message.
     */

    private SuccessResponse delete(String taskId) {
        final List<String> allErrors = new ArrayList<>();
        SuccessResponse successResponse = new SuccessResponse();
        Optional<Task> found;
        try {
            found = taskRepository.findById(taskId);
            if (found.isPresent()) {
                taskRepository.deleteById(taskId);
                successResponse.setId(taskId);
                successResponse.setMessage("Task " + taskId + " deleted.");
            } else {
                allErrors.add("No task found with that ID: " + taskId);
            }
        } catch (EntityNotFoundException exception){
            allErrors.add("No task found with that ID: " + taskId);
        }
        if (!allErrors.isEmpty()){
            throw new TaskNotFoundException(allErrors.toString());
        }
        return successResponse;
    }

}
