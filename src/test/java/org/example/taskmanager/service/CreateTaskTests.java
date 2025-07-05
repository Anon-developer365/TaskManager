package org.example.taskmanager.service;

import org.example.taskmanager.pojo.Task;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class CreateTaskTests {

    private CreateTask createTask = new CreateTask();

    private Date date;

    private LocalDateTime dueDate;

    @Test
    void whenDetailsAreGivenANewTaskIsCreated() throws ParseException {
        final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        date = dateFormat.parse("2025-05-05 17:00");
        dueDate = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        Task task = createTask.createNewTask("case title", "", "open status", date);
        assert task != null;
        assert task.getDueDate().equals(dueDate);
    }
}
