package org.example.taskmanager.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
public class CreateTaskController {

    @RequestMapping(value = "/createTask", method = RequestMethod.POST)
    public ResponseEntity<String> createTask() {
        return ok("Task Created");
    }

}
