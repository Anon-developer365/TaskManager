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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
public class UpdateStatusControllerIntegrationTests {

    private final TaskRepository taskRepository;

    private final StatusValidation statusValidation;

    private UpdateStatusController updateStatusController;

    private UpdateStatus updateStatus;

    private UpdateStatusValidation updateStatusValidation;

    @Autowired
    public UpdateStatusControllerIntegrationTests(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
        this.statusValidation = new StatusValidation();
    }
    /**

    @Test
    void checkAStatusIsUpdatedWhenFoundInTheDatabase() {
        updateStatus = new UpdateStatus(taskRepository);
        updateStatusValidation = new UpdateStatusValidation(statusValidation);
        updateStatusController = new UpdateStatusController(updateStatus, updateStatusValidation);

        String dueDate = "20-05-2025 09:00:00";
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
    **/
}
