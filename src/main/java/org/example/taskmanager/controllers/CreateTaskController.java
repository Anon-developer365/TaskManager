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
 * Rest controller for create task end point to create and save a new task in the database.
 */
@RestController
public class CreateTaskController {
    /**
     * service to create a new task, assign an ID and format the date.
     */
    private final CreateTask createTask;

    /**
     * service to save task in the database.
     */
    private final SaveTask saveTask;

    /**
     * service to validate submitted task data.
     */
    private final TaskValidation validation;

    /**
     * Autowired constructor for create task controller.
     *
     * @param createTask service to create a task with local date.
     * @param saveTask save task service to save task data in the database.
     * @param validation task validation service.
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
