package org.example.taskmanager.service;

import org.example.taskmanager.pojo.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class to save a task in the task manager database.
 */
@Service
public class SaveTask {

    @Autowired
    private TaskService taskService;
    // database URL
    static final String DB_URL = "jdbc:h2:mem:c1d5e563-2bfb-4251-a42d-2dfc2e79b621";

    public SaveTask(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * Method to save data in the task manager database.
     * @param task task required to be saved in the database.
     * @return returns a success message with the assigned ID of the task.
     */
    public String saveData(Task task) {
        taskService.saveTask(task);
        return task.getId();
    }
}


