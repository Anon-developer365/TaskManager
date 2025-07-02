package org.example.taskmanager.controllers;

import org.example.taskmanager.pojo.Task;
import org.example.taskmanager.service.RetrieveTasks;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.mockito.Mockito.when;

@WebAppConfiguration
@SpringBootTest
public class RetrieveAllTasksControllerTests {

    @Mock
    RetrieveTasks retrieveTasks;

    @InjectMocks
    RetrieveAllTasksController retrieveAllTasksController;


    @Test
    void whenAnEmptyListIsReceivedBackFromTheServiceThisIsReturned() {
        retrieveAllTasksController = new RetrieveAllTasksController(retrieveTasks);
        List<Task> expected = new ArrayList<>();
        when(retrieveTasks.getAllTasks()).thenReturn(expected);

        ResponseEntity<List<Task>> actual = retrieveAllTasksController.getAllTasks();
        assert actual.getStatusCode() == HttpStatus.OK;
        assert Objects.requireNonNull(actual.getBody()).isEmpty();

    }

    @Test
    void whenAListWithATaskIsReceivedBackFromTheServiceThisIsReturned() {
        retrieveAllTasksController = new RetrieveAllTasksController(retrieveTasks);
        UUID id = UUID.randomUUID();
        String dueDate = "20-05-2025 09:00";
        Task task = new Task(id.toString(), "title", "description", "status", dueDate);
        List<Task> expected = new ArrayList<>();
        expected.add(task);
        when(retrieveTasks.getAllTasks()).thenReturn(expected);

        ResponseEntity<List<Task>> actual = retrieveAllTasksController.getAllTasks();
        assert actual.getStatusCode() == HttpStatus.OK;
        assert Objects.requireNonNull(actual.getBody()).size() == (expected.size());

    }

    @Test
    void whenAListWithMoreThanOneTaskIsReceivedBackFromTheServiceAllItemsAreReturned() {
        retrieveAllTasksController = new RetrieveAllTasksController(retrieveTasks);
        UUID id = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        String dueDate = "20-05-2025 09:00";
        Task task = new Task(id.toString(), "title", "description", "status", dueDate);
        Task task2 = new Task(id2.toString(), "title", "description", "status", dueDate);
        List<Task> expected = new ArrayList<>();
        expected.add(task);
        expected.add(task2);
        when(retrieveTasks.getAllTasks()).thenReturn(expected);

        ResponseEntity<List<Task>> actual = retrieveAllTasksController.getAllTasks();
        assert actual.getStatusCode() == HttpStatus.OK;
        assert Objects.requireNonNull(actual.getBody()).size() == (expected.size());

    }
}
