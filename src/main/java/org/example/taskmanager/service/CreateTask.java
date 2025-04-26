package org.example.taskmanager.service;

import org.example.taskmanager.controllers.Task;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * Class to create a new task with relevant fields.
 */
@Service
public class CreateTask {

    /**
     * method to create a task, assign a unique id and parse the date into a local time format.
     *
     * @param title case title
     * @param description case description
     * @param status case status
     * @param dueDate due date for the task.
     * @return newly created task.
     */
    public Task createNewTask(String title, String description,
                              String status, String dueDate) {
        UUID uuid = UUID.randomUUID();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime date = LocalDateTime.parse(dueDate, formatter);
        return new Task(uuid.toString(), title, description, status, date);

    }

}
