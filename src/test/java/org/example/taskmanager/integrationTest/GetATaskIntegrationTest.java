package org.example.taskmanager.integrationTest;

import org.example.taskmanager.controllers.RetrieveTaskController;
import org.example.taskmanager.pojo.Task;
import org.example.taskmanager.service.GetATask;
import org.example.taskmanager.service.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.ResponseEntity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
public class GetATaskIntegrationTest {

    private final TaskRepository taskRepository;

    private GetATask getATask;
    private RetrieveTaskController retrieveTaskController;

    private Date date;

    private LocalDateTime dueDate;

    @Autowired
    public GetATaskIntegrationTest(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Test
    void checkIfATaskExistsItIsReturned() throws ParseException {
        getATask = new GetATask(taskRepository);
        retrieveTaskController = new RetrieveTaskController(getATask);

        final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        date = dateFormat.parse("2025-05-05 17:00");
        dueDate = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        Task task = new Task("2", "develop database", "create a database", "open status", dueDate);
        taskRepository.save(task);

        ResponseEntity<Task> results =  retrieveTaskController.getTask("2");
        assert Objects.requireNonNull(results.getBody()).getId().equals("2");
        assert results.getBody().getDueDate().equals(dueDate);
    }

    @Test
    void checkIfThereIsMoreThanOneTaskOnlyTheRequestedTaskIsReturned() throws ParseException {
        getATask = new GetATask(taskRepository);
        retrieveTaskController = new RetrieveTaskController(getATask);

        final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        date = dateFormat.parse("2025-05-05 17:00");
        dueDate = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        Task task = new Task("1", "develop database", "create a database", "open status", dueDate);
        Task taskTwo = new Task("2", "update database", "update a database", "working", dueDate);
        taskRepository.save(task);
        taskRepository.save(taskTwo);

        ResponseEntity<Task> results =  retrieveTaskController.getTask("2");
        assert Objects.requireNonNull(results.getBody()).getId().equals("2");
        assert results.getBody().getDueDate().equals(dueDate);
    }

    @Test
    void IfThereAreNoTasksAnErrorIsReturned() {
        getATask = new GetATask(taskRepository);
        retrieveTaskController = new RetrieveTaskController(getATask);

        assertThrows(RuntimeException.class, () -> retrieveTaskController.getTask("2"));

    }

    @Test
    void IfThereAreTasksButTheIdDoesNotMatchAnErrorIsReturned() throws ParseException {
        getATask = new GetATask(taskRepository);
        retrieveTaskController = new RetrieveTaskController(getATask);

        final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        date = dateFormat.parse("2025-05-05 17:00");
        dueDate = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        Task task = new Task("1", "develop database", "create a database", "open status", dueDate);
        taskRepository.save(task);

        assertThrows(RuntimeException.class, () -> retrieveTaskController.getTask("2"));

    }

}