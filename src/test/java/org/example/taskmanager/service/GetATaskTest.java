package org.example.taskmanager.service;

import org.example.taskmanager.pojo.Task;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;
import java.util.Optional;

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
        Mockito.when(taskRepository.findById("1")).thenReturn(Optional.of(task));
        Task actualTask = getATask.getATask("1");
        assertEquals("1", actualTask.getId());
        assertEquals("develop database", actualTask.getTitle());
    }

    @Test
    void checkIfATaskIsRequestedAndDoesNotExistsAnErrorIsThrown() {

        getATask = new GetATask(taskRepository);
        Mockito.when(taskRepository.findById("1")).thenReturn(null);
        assertThrows(RuntimeException.class, () -> getATask.getATask("1"));
    }
}
