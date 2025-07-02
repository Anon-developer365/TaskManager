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

    private final TaskRepository taskRepository;

    @Autowired
    public SaveTask(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /**
     * Method to save data in the task manager database.
     * @param task task required to be saved in the database.
     * @return returns a success message with the assigned ID of the task.
     */

    public String saveData(Task task) {
        Task savedTask = taskRepository.save(task);
        if (savedTask.getId() == null) {
            throw new RuntimeException("An error occurred saving the task");
        }
        return savedTask.getId();
    }
}


