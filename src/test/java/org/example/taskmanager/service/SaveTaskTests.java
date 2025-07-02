package org.example.taskmanager.service;

import org.example.taskmanager.pojo.Task;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class SaveTaskTests {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private SaveTask saveTask;

    public SaveTaskTests() {
    }


    @Test
    void testDataIsSavedInTheDatabase() {
        saveTask = new SaveTask(taskRepository);
        String dueDate = "20-05-2025 09:00:00";
        Task task = new Task("1", "develop database", "create a database", "open status", dueDate);
        Mockito.when(taskRepository.save(task)).thenReturn(task);
        String actualOutput = saveTask.saveData(task);
        assertEquals("1", actualOutput);
    }

    @Test
    void checkIfTheTaskIdIsReturnedAsNullAnErrorIsThrown() {
        saveTask = new SaveTask(taskRepository);
        String dueDate = "20-05-2025 09:00:00";
        Task task = new Task(null, null, null, null, null);
        Task inputTask = new Task("1", "develop database", "create a database", "open status", dueDate);
        Mockito.when(taskRepository.save(inputTask)).thenReturn(task);
        assertThrows(RuntimeException.class, () -> saveTask.saveData(inputTask));
    }

}
