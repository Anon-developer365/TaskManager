package org.example.taskmanager.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

import static org.springframework.http.ResponseEntity.ok;

@RestController
public class RetrieveTaskController {
    @RequestMapping(value = "/getTask", method = RequestMethod.GET)
    public ResponseEntity<Task> getTask(String Id) {
        LocalDateTime dateTime = LocalDateTime.parse("2025-05-05T17:00:00");
        return ok(new Task(Id, "case23", "case title", "", "open status", dateTime));
    }
}
