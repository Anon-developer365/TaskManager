package org.example.taskmanager.service;

import org.example.taskmanager.pojo.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest
public class RetrieveTasksTests {

    @Autowired
    private TaskRepository taskRepository;
    private RetrieveTasks retrieveTasks;


    @Test
    void checkWhenThereAreTasksInTheDatabaseTheseAreAllReturned() {
        retrieveTasks = new RetrieveTasks(taskRepository);
        String dueDate = "20-05-2025 09:00:00";
        Task task = new Task("1", "develop database", "create a database", "open status", dueDate);
        taskRepository.save(task);
        List<Task> tasks = retrieveTasks.getAllTasks();
        assertEquals(1, tasks.size());
        assertEquals("1", tasks.get(0).getId());
    }

    @Test
    void checkWhenThereAreNoTasksInTheDatabaseAnEmptyListIsReturned() {
        retrieveTasks = new RetrieveTasks(taskRepository);
        List<Task> tasks = retrieveTasks.getAllTasks();
        assertEquals(0, tasks.size());
    }
}
