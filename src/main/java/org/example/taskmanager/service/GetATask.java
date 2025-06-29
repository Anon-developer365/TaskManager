package org.example.taskmanager.service;

import org.example.taskmanager.pojo.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



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
        Task foundTask = taskRepository.getReferenceById(id);

            if(foundTask.getId() == null) {
                    throw new RuntimeException("Task with ID " + id + " not found");
                }
        return foundTask;
    }
}
