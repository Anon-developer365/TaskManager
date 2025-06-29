package org.example.taskmanager.service;

import org.example.taskmanager.pojo.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


/**
 * Get a task class retrieves a task if it is present in the database or returns an error message.
 */
@Service
public class GetATask {

    private final TaskRepository taskRepository;

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
        Optional<Task> foundTask = taskRepository.findById(id);
        Task task = new Task(null, null, null, null, null);

            if(foundTask.isEmpty() || foundTask.get().getId() == null) {
                    throw new RuntimeException("Task with ID " + id + " not found");
                } else {
                    task.setId(foundTask.get().getId());
                    task.setStatus(foundTask.get().getStatus());
                    task.setDescription(foundTask.get().getDescription());
                    task.setTitle(foundTask.get().getTitle());
                    task.setDueDate(foundTask.get().getDueDate());
                }
        return task;
    }
}
