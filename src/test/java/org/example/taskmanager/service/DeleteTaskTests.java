package org.example.taskmanager.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.taskmanager.exceptions.TaskNotFoundException;
import org.example.taskmanager.pojo.Task;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import uk.gov.hmcts.taskmanager.domain.SuccessResponse;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;


@SpringBootTest
public class DeleteTaskTests {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private DeleteTask deleteTask;

    private Date date;

    private LocalDateTime dueDate;

    @Test
    void checkWhenATaskIsInTheDataBaseAndIdMatchesThisIsDeleted() throws ParseException {
        deleteTask = new DeleteTask(taskRepository);
        final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        date = dateFormat.parse("2025-05-05 17:00");
        dueDate = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        Task task = new Task("1", "develop database", "create a database", "open status", dueDate);
        Mockito.when(taskRepository.findById("1")).thenReturn(Optional.of(task));
        doNothing().when(taskRepository).deleteById("1");
        String expectedMessage = "Task 1 deleted.";
        SuccessResponse response = deleteTask.deleteTask("1");
        assertEquals("1", response.getId());
        assertEquals(expectedMessage, response.getMessage());
    }

    @Test
    void checkWhenATaskIsInTheDataBaseAndIdDoesNotMatchAnErrorIsThrown() {
        deleteTask = new DeleteTask(taskRepository);
        Mockito.when(taskRepository.findById("1")).thenThrow(EntityNotFoundException.class);
        assertThrows(TaskNotFoundException.class, () -> deleteTask.deleteTask("1"));
    }
}
