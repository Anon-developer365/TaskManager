package org.example.taskmanager.integrationTest;

import org.example.taskmanager.validation.StatusValidation;
import org.example.taskmanager.controllers.UpdateStatusController;
import org.example.taskmanager.validation.UpdateStatusValidation;
import org.example.taskmanager.pojo.Task;
import org.example.taskmanager.service.TaskRepository;
import org.example.taskmanager.service.UpdateStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.ResponseEntity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
public class UpdateStatusControllerIntegrationTests {

    private final TaskRepository taskRepository;

    private final StatusValidation statusValidation;

    private UpdateStatusController updateStatusController;

    private UpdateStatus updateStatus;

    private UpdateStatusValidation updateStatusValidation;

    private Date date;

    private LocalDateTime dueDate;

    @Autowired
    public UpdateStatusControllerIntegrationTests(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
        this.statusValidation = new StatusValidation();
    }

    @Test
    void checkAStatusIsUpdatedWhenFoundInTheDatabase() throws ParseException {
        updateStatus = new UpdateStatus(taskRepository);
        updateStatusValidation = new UpdateStatusValidation(statusValidation);
        updateStatusController = new UpdateStatusController(updateStatus, updateStatusValidation);

        final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        date = dateFormat.parse("2025-05-05 17:00");
        dueDate = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        Task task = new Task("1", "develop database", "create a database", "open status", dueDate);
        String expectedOutput = "Status updated to working";
        taskRepository.save(task);
        ResponseEntity<String> actualOutput = updateStatusController.updateStatus("1", "working");
        Task updatedTask = taskRepository.getReferenceById("1");
        assertEquals(expectedOutput, actualOutput.getBody());
        assertEquals("working", updatedTask.getStatus());

    }

    @Test
    void checkAnErrorIsReturnedIfTheIdIsNotInTheDatabase() {
        updateStatus = new UpdateStatus(taskRepository);
        updateStatusValidation = new UpdateStatusValidation(statusValidation);
        updateStatusController = new UpdateStatusController(updateStatus, updateStatusValidation);

        assertThrows(RuntimeException.class, () -> updateStatusController.updateStatus("1", "working"));
    }

}
