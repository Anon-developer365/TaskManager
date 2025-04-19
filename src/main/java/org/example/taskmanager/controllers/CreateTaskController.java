package org.example.taskmanager.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreateTaskController {

    @RequestMapping(value = "/createTask", method = RequestMethod.POST)
    @ResponseBody
    public String createTask() {
        return "Task Created";
    }

}
