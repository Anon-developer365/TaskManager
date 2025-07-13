package org.example.taskmanager.service;

import org.example.taskmanager.pojo.Task;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class CreateTaskTests {

    private final CreateTask createTask = new CreateTask();

    @Test
    void whenDetailsAreGivenANewTaskIsCreated() {

        String stringDate = "2025-05-05 17:00";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.UK);
        LocalDateTime dueDate = LocalDateTime.parse(stringDate, dateTimeFormatter);
        Task task = createTask.createNewTask("case title", "", "open status", dueDate);
        assert task != null;
        assert task.getDueDate().equals(dueDate);
    }
}
