package org.example.taskmanager.service;

import org.example.taskmanager.controllers.Task;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CreateTaskTests {

    private CreateTask createTask = new CreateTask();
    String date = "20-05-2025 09:00:00";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    LocalDateTime dueDate = LocalDateTime.parse(date, formatter);

    @Test
    void whenDetailsAreGivenANewTaskIsCreated(){
        Task task = createTask.createNewTask("case23", "", "open case", "open status", "20-05-2025 09:00:00");
        assert task != null;
        assert task.getDueDate().equals(dueDate);
    }
}
