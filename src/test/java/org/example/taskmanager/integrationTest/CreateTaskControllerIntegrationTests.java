package org.example.taskmanager.integrationTest;

import org.example.taskmanager.controllers.CreateTaskController;
import org.example.taskmanager.controllers.TaskValidation;
import org.example.taskmanager.exceptions.TaskValidationErrorException;
import org.example.taskmanager.pojo.Task;
import org.example.taskmanager.service.CreateTask;
import org.example.taskmanager.service.SaveTask;
import org.example.taskmanager.service.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
public class CreateTaskControllerIntegrationTests {

    private CreateTaskController createTaskController;

    private CreateTask createTask;

    private final TaskRepository taskRepository;

    private SaveTask saveTask;

    private TaskValidation taskValidation;

    @Autowired
    public CreateTaskControllerIntegrationTests(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Test
    void aSuccessMessageIsReceivedWhenTheEndPointIsHit() {
        createTask = new CreateTask();
        taskValidation = new TaskValidation();
        saveTask = new SaveTask(taskRepository);
        createTaskController = new CreateTaskController(createTask, saveTask, taskValidation);
        ResponseEntity<String> output = createTaskController.createTask("case title", "", "open status", "2025-05-05 17:00");
        assert output != null;
        assert output.getBody().contains("Task Created");
        String[] splitOutput = output.getBody().split(" ");
        Task task = taskRepository.getReferenceById(splitOutput[0]);
        assertEquals("case title", task.getTitle());

    }

    @Test
    void whenThereIsAValidationErrorThisIsReturned() {
        createTask = new CreateTask();
        taskValidation = new TaskValidation();
        saveTask = new SaveTask(taskRepository);
        createTaskController = new CreateTaskController(createTask, saveTask, taskValidation);
        assertThrows(TaskValidationErrorException.class, () -> createTaskController.createTask("case title", "", "open status", "2025"));
    }
}
