package org.example.taskmanager.service;

import lombok.ToString;
import org.example.taskmanager.pojo.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class to save a task in the task manager database.
 */
@Service
@ToString
public class SaveTask {

    /**
     * Task repository for service.
     */
    private final TaskRepository taskRepository;

    /**
     * Autowired constructor for Save Task service.
     *
     * @param aTaskRepository Task repository for the service.
     */
    @Autowired
    public SaveTask(final TaskRepository aTaskRepository) {
        this.taskRepository = aTaskRepository;
    }

    /**
     * Method to save data in the task manager database.
     *
     * @param task task required to be saved in the database.
     * @return returns a success message with the assigned ID of the task.
     */

    public String saveData(final Task task) {
        Task savedTask = taskRepository.save(task);
        if (savedTask.getId() == null) {
            throw new RuntimeException("An error occurred saving the task");
        }
        return savedTask.getId();
    }
}


