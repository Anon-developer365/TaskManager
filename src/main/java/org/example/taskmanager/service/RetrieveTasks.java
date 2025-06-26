package org.example.taskmanager.service;

import org.example.taskmanager.pojo.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Class to access the database and retrieve all tasks.
 */
@Service
public class RetrieveTasks {


    private final TaskRepository taskRepository;

    @Autowired
    public RetrieveTasks(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }


    /**
     * Method to retrieve all task data from the database.
     *
     * @return a list of tasks within the database.
     */
    public List<Task> getAllTasks() {
        return taskRepository.findAll();

    }
}
