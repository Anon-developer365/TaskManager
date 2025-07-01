package org.example.taskmanager.service;

import org.example.taskmanager.pojo.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
     * @param id id of the task to be updated.
     * @param status new status of the task.
     * @return boolean returns true if a task is updated.
     */
    public boolean updateStatus(String id, String status) {
        Optional<Task> task = taskRepository.findById(id);
        if(task.isEmpty() || task.get().getId() == null) {
            throw new RuntimeException("Task with ID " + id + " not found");
        } else {
            Task updatedTask = new Task(id, task.get().getTitle(), task.get().getDescription(), status, task.get().getDueDate());
            taskRepository.save(updatedTask);

        }
        return true;
    }
}
