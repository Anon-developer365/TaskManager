package org.example.taskmanager.controllers;

import org.example.taskmanager.pojo.Task;
import org.example.taskmanager.service.RetrieveTasks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

/**
 * Rest controller for the get all tasks end point.
 */
@RestController
public class RetrieveAllTasksController {

    /**
     * Retrieve tasks class to retrieve all tasks in the database.
     */
    private final RetrieveTasks retrieveTasks;

    /**
     * Autowired constructor for retrieve all tasks controller.
     *
     * @param retrieveTasks retrieve tasks service to be used in the controller.
     */
    @Autowired
    public RetrieveAllTasksController(RetrieveTasks retrieveTasks) {
        this.retrieveTasks = retrieveTasks;
    }

    /**
     * Method to retrieve all tasks in the database
     *
     * @return a list of tasks within the database.
     */
    @RequestMapping(value = "/getAllTasks", method = RequestMethod.GET)
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = new ArrayList<>(retrieveTasks.getAllTasks());
        return ok(tasks);
    }
}
