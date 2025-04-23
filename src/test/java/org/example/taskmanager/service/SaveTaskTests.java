package org.example.taskmanager.service;

import org.example.taskmanager.controllers.Task;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class SaveTaskTests {


    @Test
    void testDataIsSavedInTheDatabase() {
        UUID uuid = UUID.randomUUID();
        String date = "20-05-2025 09:00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime dueDate = LocalDateTime.parse(date, formatter);

        String expectedResult = uuid + " successfully saved";
        SaveTask saveTask = new SaveTask();
        Task task = new Task(uuid.toString(), "case20", "develop database", "", "open status", dueDate);
        String result = saveTask.saveData(task);
        assert result.equals(expectedResult);

    }
}
