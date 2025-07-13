package org.example.taskmanager.service;

import org.example.taskmanager.pojo.Task;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import uk.gov.hmcts.taskmanager.domain.UpdateStatusRequest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
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
    void checkTheServiceUpdatesAStatusIfTheTaskExists() throws ParseException {
        updateStatus = new UpdateStatus(taskRepository);
        final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        Date date = dateFormat.parse("2025-05-05 17:00");
        LocalDateTime dueDate = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        UpdateStatusRequest updateStatusRequest = new UpdateStatusRequest();
        updateStatusRequest.setId("1");
        updateStatusRequest.setStatus("working");

        Task task = new Task("1", "develop database", "create a database", "open status", dueDate);
        Task updatedTask = new Task("1", "develop database", "create a database", "working", dueDate);
        Mockito.when(taskRepository.findById("1")).thenReturn(Optional.of(task));
        Mockito.when(taskRepository.save(updatedTask)).thenReturn(null);

        boolean output = updateStatus.updateStatus(updateStatusRequest);
        assertTrue(output);
    }

    @Test
    void checkTheServiceReturnsAnErrorWhenTheTaskDoesNotExists() {
        updateStatus = new UpdateStatus(taskRepository);
        Task task = new Task(null, null, null, null, null);
        Mockito.when(taskRepository.findById("1")).thenReturn(Optional.of(task));

        UpdateStatusRequest updateStatusRequest = new UpdateStatusRequest();
        updateStatusRequest.setId("1");
        updateStatusRequest.setStatus("working");

        assertThrows(RuntimeException.class, () -> updateStatus.updateStatus(updateStatusRequest));

    }

}
