package org.example.taskmanager.service;

import org.example.taskmanager.exceptions.TaskNotFoundException;
import org.example.taskmanager.pojo.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.taskmanager.domain.UpdateStatusRequest;

import java.util.Optional;

/**
 * Update status class created to update the status of a task.
 */
@Service
public class UpdateStatus {

    /**
     * Task repository for service.
     */
    private final TaskRepository taskRepository;

    /**
     * Autowired constructor for Update status service.
     *
     * @param aTaskRepository Task repository for the service.
     */
    @Autowired
    public UpdateStatus(final TaskRepository aTaskRepository) {
        this.taskRepository = aTaskRepository;
    }

    /**
     * Method to update the task with the matching ID number to the new status.
     *
     * @param  updateRequest request containing the
     *                       id and new status of the task to be updated.
     * @return boolean returns true if a task is updated.
     */
    public boolean updateStatus(final UpdateStatusRequest updateRequest) {
        Optional<Task> task = taskRepository.findById(updateRequest.getId());
        if (task.isEmpty() || task.get().getId() == null) {
            throw new TaskNotFoundException("Task with ID "
                    + updateRequest.getId() + " not found");
        } else {
            Task updatedTask = new Task(
                    updateRequest.getId(),
                    task.get().getTitle(),
                    task.get().getDescription(),
                    updateRequest.getStatus(),
                    task.get().getDueDate());
            taskRepository.save(updatedTask);
        }
        return true;
    }
}
