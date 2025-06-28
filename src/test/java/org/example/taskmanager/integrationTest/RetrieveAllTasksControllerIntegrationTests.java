package org.example.taskmanager.integrationTest;

import org.example.taskmanager.controllers.RetrieveAllTasksController;
import org.example.taskmanager.pojo.Task;
import org.example.taskmanager.service.RetrieveTasks;
import org.example.taskmanager.service.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class RetrieveAllTasksControllerIntegrationTests {

    private final TaskRepository taskRepository;

    private RetrieveAllTasksController retrieveAllTasksController;

    private RetrieveTasks retrieveTasks;

    @Autowired
    public RetrieveAllTasksControllerIntegrationTests(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Test
    void checkTheServiceRunsSuccessfullyAndReturnsAllItemsInTheDatabase() throws SQLException {
        retrieveTasks = new RetrieveTasks(taskRepository);
        retrieveAllTasksController = new RetrieveAllTasksController(retrieveTasks);

        String dueDate = "20-05-2025 09:00:00";
        Task task = new Task("2", "develop database", "create a database", "open status", dueDate);
        taskRepository.save(task);

        ResponseEntity<List<Task>> output = retrieveAllTasksController.getAllTasks();
        assert Objects.requireNonNull(output.getBody()).get(output.getBody().size() -1).getId().equals("2");

    }

    @Test
    void checkTheServiceReturnsAnEmptyListIfTheDatabaseIsEmpty() throws SQLException {
        retrieveTasks = new RetrieveTasks(taskRepository);
        retrieveAllTasksController = new RetrieveAllTasksController(retrieveTasks);
        List<Task> expected = new ArrayList<>();

        ResponseEntity<List<Task>> output = retrieveAllTasksController.getAllTasks();
        assertEquals(HttpStatus.OK, output.getStatusCode());
        assertEquals(output.getBody(), expected);

    }
}
