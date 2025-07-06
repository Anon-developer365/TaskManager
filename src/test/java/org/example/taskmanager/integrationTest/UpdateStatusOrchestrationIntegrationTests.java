package org.example.taskmanager.integrationTest;

import org.example.taskmanager.controllers.UpdateStatusOrchestration;
import org.example.taskmanager.validation.StatusValidation;
import org.example.taskmanager.validation.UpdateStatusValidation;
import org.example.taskmanager.pojo.Task;
import org.example.taskmanager.service.TaskRepository;
import org.example.taskmanager.service.UpdateStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import uk.gov.hmcts.taskmanager.domain.SuccessResponse;
import uk.gov.hmcts.taskmanager.domain.UpdateStatusRequest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
public class UpdateStatusOrchestrationIntegrationTests {

    private final TaskRepository taskRepository;

    private final StatusValidation statusValidation;

    private UpdateStatusOrchestration updateStatusOrchestration;

    private UpdateStatus updateStatus;

    private UpdateStatusValidation updateStatusValidation;

    private Date date;

    private LocalDateTime dueDate;

    @Autowired
    public UpdateStatusOrchestrationIntegrationTests(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
        this.statusValidation = new StatusValidation();
    }

    @Test
    void checkAStatusIsUpdatedWhenFoundInTheDatabase() throws ParseException {
        updateStatus = new UpdateStatus(taskRepository);
        updateStatusValidation = new UpdateStatusValidation(statusValidation);
        updateStatusOrchestration = new UpdateStatusOrchestration(updateStatus, updateStatusValidation);

        final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        date = dateFormat.parse("2025-05-05 17:00");
        dueDate = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        UpdateStatusRequest updateStatusRequest = new UpdateStatusRequest();
        updateStatusRequest.setId("1");
        updateStatusRequest.setStatus("working");

        Task task = new Task("1", "develop database", "create a database", "open status", dueDate);
        String expectedOutput = "Status updated to: working";
        taskRepository.save(task);
        SuccessResponse actualOutput = updateStatusOrchestration.updateStatus(updateStatusRequest);
        Task updatedTask = taskRepository.getReferenceById("1");
        assertEquals(expectedOutput, actualOutput.getMessage());
        assertEquals("working", updatedTask.getStatus());

    }

    @Test
    void checkAnErrorIsReturnedIfTheIdIsNotInTheDatabase() {
        updateStatus = new UpdateStatus(taskRepository);
        updateStatusValidation = new UpdateStatusValidation(statusValidation);
        updateStatusOrchestration = new UpdateStatusOrchestration(updateStatus, updateStatusValidation);

        UpdateStatusRequest updateStatusRequest = new UpdateStatusRequest();
        updateStatusRequest.setId("1");
        updateStatusRequest.setStatus("working");

        assertThrows(RuntimeException.class, () -> updateStatusOrchestration.updateStatus(updateStatusRequest));
    }

}
