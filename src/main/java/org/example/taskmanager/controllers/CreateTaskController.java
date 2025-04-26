package org.example.taskmanager.controllers;

import jakarta.persistence.Id;
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

    @Autowired
    public CreateTaskController(final CreateTask createTask, final SaveTask saveTask) {
        this.createTask = createTask;
        this.saveTask = saveTask;
    }

    @RequestMapping(value = "/createTask", method = RequestMethod.POST)
    public ResponseEntity<String> createTask(String title, String description, String status, String dueDate) {
       Task task = createTask.createNewTask(title, description, status, dueDate);
       String id = saveTask.saveData(task);
       return ok(id + " Task Created");
    }

}
