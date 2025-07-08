package org.example.taskmanager.service;


import jakarta.persistence.EntityNotFoundException;
import org.example.taskmanager.exceptions.TaskValidationErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.taskmanager.domain.Task;

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
     * Autowired constructor for task repository.
     * @param taskRepository task repository.
     */
    @Autowired
    public GetATask(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /**
     * Method to retrieve a task.
     *
     * @param id the id of the task to be returned.
     * @return the task that wants to be retrieved.
     */
    public Task getATask(String id) {
        Task transformedTask = new Task();
        Optional<org.example.taskmanager.pojo.Task> foundTask;

        try {
            foundTask = taskRepository.findById(id);

            if (foundTask.isPresent()) {
                transformedTask.setId(foundTask.get().getId());
                transformedTask.setTitle(foundTask.get().getTitle());
                transformedTask.setStatus(foundTask.get().getStatus());
                transformedTask.setTaskDescription(foundTask.get().getDescription());
                transformedTask.setDueDate(foundTask.get().getDueDate());
            }
        } catch (EntityNotFoundException exception){
            throw new TaskValidationErrorException("Task with ID " + id + " not found");
        }
        return transformedTask;

    }
}
