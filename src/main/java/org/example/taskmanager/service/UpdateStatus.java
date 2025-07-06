package org.example.taskmanager.service;

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

    private final TaskRepository taskRepository;

    @Autowired
    public UpdateStatus(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /**
     * Method to update the task with the matching ID number to the new status.
     *
     * @param  updateRequest request containing the id and new status of the task to be updated.
     * @return boolean returns true if a task is updated.
     */
    public boolean updateStatus(UpdateStatusRequest updateRequest) {
        Optional<Task> task = taskRepository.findById(updateRequest.getId());
        if(task.isEmpty() || task.get().getId() == null) {
            throw new RuntimeException("Task with ID " + updateRequest.getId() + " not found");
        } else {
            Task updatedTask = new Task(updateRequest.getId(), task.get().getTitle(), task.get().getDescription(), updateRequest.getStatus(), task.get().getDueDate());
            taskRepository.save(updatedTask);
        }
        return true;
    }
}
