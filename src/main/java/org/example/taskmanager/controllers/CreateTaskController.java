package org.example.taskmanager.controllers;

import org.example.taskmanager.pojo.Task;
import org.example.taskmanager.service.CreateTask;
import org.example.taskmanager.service.SaveTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

/**
 * Rest controller for the create task end point.
 */
@RestController
public class CreateTaskController {
    private final CreateTask createTask;

    private final SaveTask saveTask;

    private final TaskValidation validation;

    /**
     * Autowired constructor for create task controller.
     *
     * @param createTask service to create a task with local date.
     * @param saveTask save task service to save task data in the database.
     */
    @Autowired
    public CreateTaskController(final CreateTask createTask, final SaveTask saveTask, final TaskValidation validation) {
        this.createTask = createTask;
        this.saveTask = saveTask;
        this.validation = validation;
    }

    /**
     * Method to call required services in order to create a task and save this within the database.
     *
     * @param title case title
     * @param description description of the case.
     * @param status case status
     * @param dueDate date the task is due to be completed.
     * @return a string containing the newly created task id with a success message.
     */
    @RequestMapping(value = "/createTask", method = RequestMethod.POST)
    public ResponseEntity<String> createTask(String title, String description, String status, String dueDate) {
        validation.verifyTask(title, description, status, dueDate);
        Task task = createTask.createNewTask(title, description, status, dueDate);
        String id = saveTask.saveData(task);
        return ok(id + " Task Created");
    }

}
