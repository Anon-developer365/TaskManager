package org.example.taskmanager.service;

import jakarta.persistence.EntityNotFoundException;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class GetATaskTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private GetATask getATask;

    private Date date;

    private LocalDateTime dueDate;

    @Test
    void checkIfATaskIsRequestedAndExistsItIsReturned() throws ParseException {
        getATask = new GetATask(taskRepository);

        final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        date = dateFormat.parse("2025-05-05 17:00");
        dueDate = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        Task task = new Task("1", "develop database", "create a database", "open status", dueDate);
        Mockito.when(taskRepository.findById("1")).thenReturn(Optional.of(task));
        Task actualTask = getATask.getATask("1");
        assertEquals("1", actualTask.getId());
        assertEquals("develop database", actualTask.getTitle());
    }

    @Test
    void checkIfATaskIsRequestedAndDoesNotExistsAnErrorIsThrown() {

        getATask = new GetATask(taskRepository);
        Mockito.when(taskRepository.findById("1")).thenThrow(EntityNotFoundException.class);
        assertThrows(RuntimeException.class, () -> getATask.getATask("1"));
    }

}
