package org.example.taskmanager.service;

import org.example.taskmanager.pojo.Task;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class SaveTaskTests {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private SaveTask saveTask;

    private Date date;

    private LocalDateTime dueDate;


    @Test
    void testDataIsSavedInTheDatabase() throws ParseException {

        final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        date = dateFormat.parse("2025-05-05 17:00");
        dueDate = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        saveTask = new SaveTask(taskRepository);

        Task task = new Task("1", "develop database", "create a database", "open status", dueDate);
        Mockito.when(taskRepository.save(task)).thenReturn(task);
        String actualOutput = saveTask.saveData(task);
        assertEquals("1", actualOutput);
    }

    @Test
    void checkIfTheTaskIdIsReturnedAsNullAnErrorIsThrown() throws ParseException {

        final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        date = dateFormat.parse("2025-05-05 17:00");
        dueDate = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        saveTask = new SaveTask(taskRepository);

        Task task = new Task(null, null, null, null, null);
        Task inputTask = new Task("1", "develop database", "create a database", "open status", dueDate);
        Mockito.when(taskRepository.save(inputTask)).thenReturn(task);
        assertThrows(RuntimeException.class, () -> saveTask.saveData(inputTask));
    }

}
