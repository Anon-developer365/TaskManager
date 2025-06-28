package org.example.taskmanager.service;

import org.example.taskmanager.pojo.Task;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class GetATaskTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private GetATask getATask;

    @Test
    void checkIfATaskIsRequestedAndExistsItIsReturned() throws SQLException {
        getATask = new GetATask(taskRepository);

        String dueDate = "20-05-2025 09:00:00";
        Task task = new Task("1", "develop database", "create a database", "open status", dueDate);
        List<Task> expectedResult = new ArrayList<>();
        expectedResult.add(task);
        Mockito.when(taskRepository.findAll()).thenReturn(expectedResult);
        Task actualTask = getATask.getATask("1");
        assertEquals(actualTask.getId(), "1");
        assertEquals(actualTask.getTitle(), "develop database");
    }

    @Test
    void checkIfATaskIsRequestedAndDoesNotExistsAnErrorIsThrown() {
        getATask = new GetATask();
    }
}
