package org.example.taskmanager.service;

import org.example.taskmanager.pojo.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TaskServiceTests {

    @Autowired
    private TaskService taskService;

    @Test
    void whenSaveTaskIsCalledDataIsSavedInTheDataBase() {
        String dueDate = "20-05-2025 09:00:00";
        Task task = new Task("2", "develop database", "create a database", "open status", dueDate);
        taskService.saveTask(task);
        List<Task> tasks = taskService.getTasks();
        System.out.println(tasks.size());
        System.out.println(tasks.get(0).getId());
        System.out.println(tasks.get(1).getId());
        assertEquals(2, tasks.size());

    }
}
