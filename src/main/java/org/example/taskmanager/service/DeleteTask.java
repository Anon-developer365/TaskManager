package org.example.taskmanager.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.taskmanager.exceptions.TaskNotFoundException;
import org.example.taskmanager.pojo.Task;
import org.example.taskmanager.validation.IdValidation;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.taskmanager.domain.SuccessResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class DeleteTask {

    /**
     * Task repository for service.
     */
    private final TaskRepository taskRepository;

    /**
     * Validation service for Id's
     */
    private final IdValidation idValidation;

    /**
     * Autowired constructor for task repository.
     * @param taskRepository task repository.
     */
    public DeleteTask(TaskRepository taskRepository, IdValidation idValidation) {
        this.taskRepository = taskRepository;
        this.idValidation = idValidation;
    }

    public SuccessResponse deleteTask(String transactionId, String taskId) {
        validate(transactionId, taskId);
        return delete(taskId);
    }

    private void validate(String transactionId, String taskId) {
        idValidation.validateId("Transaction", transactionId);
        idValidation.validateId("Task", taskId);
    }

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
