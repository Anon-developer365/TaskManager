package org.example.taskmanager.service;

import org.example.taskmanager.pojo.Task;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CreateTaskTests {

    private CreateTask createTask = new CreateTask();
    String dueDate = "20-05-2025 09:00";

    @Test
    void whenDetailsAreGivenANewTaskIsCreated(){
        Task task = createTask.createNewTask("case title", "", "open status", "20-05-2025 09:00");
        assert task != null;
        assert task.getDueDate().equals(dueDate);
    }
}
