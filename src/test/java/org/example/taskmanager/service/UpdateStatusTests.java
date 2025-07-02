package org.example.taskmanager.service;

import org.example.taskmanager.pojo.Task;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class UpdateStatusTests {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private UpdateStatus updateStatus;

    @Test
    void checkTheServiceUpdatesAStatusIfTheTaskExists() {
        updateStatus = new UpdateStatus(taskRepository);
        String dueDate = "20-05-2025 09:00:00";
        Task task = new Task("1", "develop database", "create a database", "open status", dueDate);
        Task updatedTask = new Task("1", "develop database", "create a database", "working", dueDate);
        Mockito.when(taskRepository.findById("1")).thenReturn(Optional.of(task));
        Mockito.when(taskRepository.save(updatedTask)).thenReturn(null);

        boolean output = updateStatus.updateStatus("1", "working");
        assertTrue(output);
    }

    @Test
    void checkTheServiceReturnsAnErrorWhenTheTaskDoesNotExists() {
        updateStatus = new UpdateStatus(taskRepository);
        Task task = new Task(null, null, null, null, null);
        Mockito.when(taskRepository.findById("1")).thenReturn(Optional.of(task));

        assertThrows(RuntimeException.class, () -> updateStatus.updateStatus("1", "working"));

    }

}
