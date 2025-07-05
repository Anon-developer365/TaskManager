package org.example.taskmanager.controllers;

import org.example.taskmanager.service.GetATask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.hmcts.taskmanager.domain.Task;


/**
 * Class to retrieve a task from the database with a given id.
 */
@RestController
public class RetrieveTaskController {

    /**
     * get a task service to access the database and locate the task.
     */
    private final GetATask getATask;

    /**
     * Autowired constructor for get a task service.
     *
     * @param getATask get a task class.
     */
    @Autowired
    public RetrieveTaskController(GetATask getATask) {
        this.getATask = getATask;
    }

    /**
     * Controller method to call get a task class and return the task data.
     * @param Id of the task to be retrieved
     * @return the task to be returned.
     */
    @RequestMapping(value = "/Task", method = RequestMethod.GET)
    public Task getTask(String Id) {
        Task task = getATask.getATask(Id);
        return task;
    }
}
