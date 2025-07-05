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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class RetrieveAllTasksControllerIntegrationTests {

    private final TaskRepository taskRepository;

    private RetrieveAllTasksController retrieveAllTasksController;

    private RetrieveTasks retrieveTasks;

    private Date date;

    private LocalDateTime dueDate;

    @Autowired
    public RetrieveAllTasksControllerIntegrationTests(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Test
    void checkTheServiceRunsSuccessfullyAndReturnsAllItemsInTheDatabase() throws ParseException {
        retrieveTasks = new RetrieveTasks(taskRepository);
        retrieveAllTasksController = new RetrieveAllTasksController(retrieveTasks);

        final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        date = dateFormat.parse("2025-05-05 17:00");
        dueDate = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        Task task = new Task("2", "develop database", "create a database", "open status", dueDate);
        taskRepository.save(task);

        ResponseEntity<List<Task>> output = retrieveAllTasksController.getAllTasks();
        assert Objects.requireNonNull(output.getBody()).get(output.getBody().size() -1).getId().equals("2");

    }

    @Test
    void checkTheServiceReturnsAnEmptyListIfTheDatabaseIsEmpty() {
        retrieveTasks = new RetrieveTasks(taskRepository);
        retrieveAllTasksController = new RetrieveAllTasksController(retrieveTasks);
        List<Task> expected = new ArrayList<>();

        ResponseEntity<List<Task>> output = retrieveAllTasksController.getAllTasks();
        assertEquals(HttpStatus.OK, output.getStatusCode());
        assertEquals(output.getBody(), expected);

    }

}
