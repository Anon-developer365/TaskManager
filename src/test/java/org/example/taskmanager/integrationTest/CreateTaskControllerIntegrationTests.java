package org.example.taskmanager.integrationTest;

import org.example.taskmanager.controllers.CreateTaskController;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import uk.gov.hmcts.taskmanager.domain.CreateTaskRequest;
import uk.gov.hmcts.taskmanager.domain.SuccessResponse;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
public class CreateTaskControllerIntegrationTests {

    private StatusValidation statusValidation;

    private CreateTaskController createTaskController;

    private CreateTask createTask;

    private final TaskRepository taskRepository;

    private SaveTask saveTask;

    private TaskValidation taskValidation;

    private CreateTaskRequest createTaskRequest;

    @Autowired
    public CreateTaskControllerIntegrationTests(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
        this.statusValidation = new StatusValidation();
    }

    @Test
    void aSuccessMessageIsReceivedWhenTheEndPointIsHit() throws ParseException {
        createTask = new CreateTask();
        taskValidation = new TaskValidation(statusValidation);
        saveTask = new SaveTask(taskRepository);
        createTaskController = new CreateTaskController(createTask, saveTask, taskValidation);
        final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        Date date = (dateFormat.parse("2025-05-05 17:00"));
        createTaskRequest = new CreateTaskRequest();
        createTaskRequest.setTitle("case title");
        createTaskRequest.setTaskDescription("");
        createTaskRequest.setStatus("open status");
        createTaskRequest.setDueDate(date);
        SuccessResponse output = createTaskController.createTask("1", createTaskRequest);
        assert output != null;
        assert output.getMessage().equals("Task Created successfully");
        Task task = taskRepository.getReferenceById(output.getId());
        assertEquals("case title", task.getTitle());

    }

    @Test
    void whenThereIsAValidationErrorThisIsReturned() throws ParseException {
        createTask = new CreateTask();
        taskValidation = new TaskValidation(statusValidation);
        saveTask = new SaveTask(taskRepository);
        final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        Date date = (dateFormat.parse("2025-05-05 17:00"));
        createTaskRequest = new CreateTaskRequest();
        createTaskRequest.setTitle("case title");
        createTaskRequest.setTaskDescription("");
        createTaskRequest.setStatus("#");
        createTaskRequest.setDueDate(date);
        createTaskController = new CreateTaskController(createTask, saveTask, taskValidation);
        assertThrows(TaskValidationErrorException.class, () -> createTaskController.createTask("1", createTaskRequest));
    }

}
