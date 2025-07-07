package org.example.taskmanager.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.taskmanager.exceptions.TaskValidationErrorException;
import org.example.taskmanager.pojo.Task;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.taskmanager.domain.SuccessResponse;

import java.util.Optional;


@Service
public class DeleteTask {

    /**
     * Task repository for service.
     */
    private final TaskRepository taskRepository;

    /**
     * Autowired constructor for task repository.
     * @param taskRepository task repository.
     */
    public DeleteTask(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public SuccessResponse deleteTask(String taskId) {
        SuccessResponse successResponse = new SuccessResponse();
        Optional<Task> found;
        try {
        found = taskRepository.findById(taskId);
        if (found.isPresent()) {
            taskRepository.deleteById(taskId);
            successResponse.setId(taskId);
            successResponse.setMessage("Task " + taskId + " deleted.");
        } } catch (EntityNotFoundException exception){
            throw new TaskValidationErrorException("No task found with that ID " + taskId);
        }
        return successResponse;

    }

}
