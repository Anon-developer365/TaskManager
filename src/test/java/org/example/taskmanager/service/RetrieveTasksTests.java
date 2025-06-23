package org.example.taskmanager.service;

import org.example.taskmanager.pojo.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class RetrieveTasksTests {

    @Autowired
    private RetrieveTasks retrieveTasks;

    @Test
    void checkWhenThereAreTasksInTheDatabaseTheseAreAllReturned() throws SQLException {
        List<Task> tasks = retrieveTasks.getAllTasks();
        assertEquals(1, tasks.size());
        assertEquals("1", tasks.get(0).getId());
    }
}
