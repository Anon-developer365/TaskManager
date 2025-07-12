package org.example.taskmanager.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.taskmanager.exceptions.TaskValidationErrorException;
import org.example.taskmanager.pojo.Task;
import org.example.taskmanager.validation.IdValidation;
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
import static org.mockito.Mockito.doNothing;

@SpringBootTest
public class GetATaskTest {

    @Mock
    private IdValidation idValidation;

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private GetATask getATask;

    private Date date;

    private LocalDateTime dueDate;

    @Test
    void checkIfATaskIsRequestedAndExistsItIsReturned() throws ParseException {
        getATask = new GetATask(taskRepository, idValidation);

        final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        date = dateFormat.parse("2025-05-05 17:00");
        dueDate = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        Task task = new Task("1", "develop database", "create a database", "open status", dueDate);
        Mockito.when(taskRepository.findById("1")).thenReturn(Optional.of(task));
        doNothing().when(idValidation).validateId("Transaction", "2");
        doNothing().when(idValidation).validateId("Task", "1");
        uk.gov.hmcts.taskmanager.domain.Task actualTask = getATask.getATask("2","1");
        assertEquals("1", actualTask.getId());
        assertEquals("develop database", actualTask.getTitle());
    }

    @Test
    void checkIfATaskIsRequestedAndDoesNotExistsAnErrorIsThrown() {

        getATask = new GetATask(taskRepository, idValidation);
        doNothing().when(idValidation).validateId("Transaction", "2");
        doNothing().when(idValidation).validateId("Task", "1");
        Mockito.when(taskRepository.findById("1")).thenThrow(EntityNotFoundException.class);
        assertThrows(TaskValidationErrorException.class, () -> getATask.getATask("2","1"));
    }

}
