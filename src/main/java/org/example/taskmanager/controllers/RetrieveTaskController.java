package org.example.taskmanager.controllers;

import org.example.taskmanager.service.GetATask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

import static org.springframework.http.ResponseEntity.ok;

@RestController
public class RetrieveTaskController {

    private GetATask getATask;

    @Autowired
    public RetrieveTaskController(GetATask getATask) {
        this.getATask = getATask;
    }
    @RequestMapping(value = "/getTask", method = RequestMethod.GET)
    public ResponseEntity<Task> getTask(String Id) {
        Task task = getATask.getATask(Id);
        return ok(task);
    }
}
