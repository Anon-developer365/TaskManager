package org.example.taskmanager.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.taskmanager.exceptions.TaskValidationErrorException;
import org.example.taskmanager.pojo.Task;
import org.example.taskmanager.validation.ValidationOrchestration;
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
    private ValidationOrchestration validationOrchestration;

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private GetATask getATask;

    @Test
    void checkIfATaskIsRequestedAndExistsItIsReturned() throws ParseException {
        getATask = new GetATask(taskRepository, validationOrchestration);

        final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        Date date = dateFormat.parse("2025-05-05 17:00");
        LocalDateTime dueDate = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        Task task = new Task("1", "develop database", "create a database", "open status", dueDate);
        Mockito.when(taskRepository.findById("1")).thenReturn(Optional.of(task));
        doNothing().when(validationOrchestration).generalTaskValidation("2", "1");
        uk.gov.hmcts.taskmanager.domain.Task actualTask = getATask.getATask("2","1");
        assertEquals("1", actualTask.getId());
        assertEquals("develop database", actualTask.getTitle());
    }

    @Test
    void checkIfATaskIsRequestedAndDoesNotExistsAnErrorIsThrown() {

        getATask = new GetATask(taskRepository, validationOrchestration);
        doNothing().when(validationOrchestration).generalTaskValidation("2", "1");

        Mockito.when(taskRepository.findById("1")).thenThrow(EntityNotFoundException.class);
        assertThrows(TaskValidationErrorException.class, () -> getATask.getATask("2","1"));
    }

}
