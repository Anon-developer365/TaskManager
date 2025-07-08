package org.example.taskmanager.integrationTest;

import org.example.taskmanager.controllers.CreateTaskOrchestration;
import org.example.taskmanager.validation.StatusValidation;
import org.example.taskmanager.validation.TaskValidation;
import org.example.taskmanager.exceptions.TaskValidationErrorException;
import org.example.taskmanager.pojo.Task;
import org.example.taskmanager.service.CreateTask;
import org.example.taskmanager.service.SaveTask;
import org.example.taskmanager.service.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import uk.gov.hmcts.taskmanager.domain.CreateTaskRequest;
import uk.gov.hmcts.taskmanager.domain.SuccessResponse;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
public class CreateTaskOrchestrationIntegrationTests {

    private final StatusValidation statusValidation;

    private CreateTaskOrchestration createTaskOrchestration;

    private CreateTask createTask;

    private final TaskRepository taskRepository;

    private SaveTask saveTask;

    private TaskValidation taskValidation;

    private CreateTaskRequest createTaskRequest;

    @Autowired
    public CreateTaskOrchestrationIntegrationTests(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
        this.statusValidation = new StatusValidation();
    }

    @Test
    void aSuccessMessageIsReceivedWhenTheEndPointIsHit() {
        createTask = new CreateTask();
        taskValidation = new TaskValidation(statusValidation);
        saveTask = new SaveTask(taskRepository);
        createTaskOrchestration = new CreateTaskOrchestration(createTask, saveTask, taskValidation);

        String stringDate = "2025-05-05 17:00";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.UK);
        LocalDateTime dueDate = LocalDateTime.parse(stringDate, dateTimeFormatter);

        createTaskRequest = new CreateTaskRequest();
        createTaskRequest.setTitle("case title");
        createTaskRequest.setTaskDescription("");
        createTaskRequest.setStatus("open status");
        createTaskRequest.setDueDate(dueDate);
        SuccessResponse output = createTaskOrchestration.createTask("1", createTaskRequest);
        assert output != null;
        assert output.getMessage().equals("Task Created successfully");
        Task task = taskRepository.getReferenceById(output.getId());
        assertEquals("case title", task.getTitle());

    }

    @Test
    void whenThereIsAValidationErrorThisIsReturned() {
        createTask = new CreateTask();
        taskValidation = new TaskValidation(statusValidation);
        saveTask = new SaveTask(taskRepository);

        String stringDate = "2025-05-05 17:00";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.UK);
        LocalDateTime dueDate = LocalDateTime.parse(stringDate, dateTimeFormatter);

        createTaskRequest = new CreateTaskRequest();
        createTaskRequest.setTitle("case title");
        createTaskRequest.setTaskDescription("");
        createTaskRequest.setStatus("#");
        createTaskRequest.setDueDate(dueDate);
        createTaskOrchestration = new CreateTaskOrchestration(createTask, saveTask, taskValidation);
        assertThrows(TaskValidationErrorException.class, () -> createTaskOrchestration.createTask("1", createTaskRequest));
    }

}
