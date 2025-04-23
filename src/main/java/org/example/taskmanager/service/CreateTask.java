package org.example.taskmanager.service;

import org.example.taskmanager.controllers.Task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Class to create a new task with relevant fields.
 */
public class CreateTask {

    public Task createNewTask(String caseNumber, String title, String description,
                              String status, String dueDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime date = LocalDateTime.parse(dueDate, formatter);
        return new Task("1", caseNumber, title, description, status, date);

    }

}
