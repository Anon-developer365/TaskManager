package org.example.taskmanager.integrationTest;

import org.example.taskmanager.controllers.RetrieveAllTasksController;
import org.example.taskmanager.pojo.Task;
import org.example.taskmanager.service.RetrieveTasks;
import org.example.taskmanager.service.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import uk.gov.hmcts.taskmanager.domain.TaskResponse;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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

        TaskResponse output = retrieveAllTasksController.getAllTasks();
        assertEquals("2", output.getTasks().get(output.getTasks().size()-1).getId());

    }

    @Test
    void checkTheServiceReturnsAnEmptyListIfTheDatabaseIsEmpty() {
        retrieveTasks = new RetrieveTasks(taskRepository);
        retrieveAllTasksController = new RetrieveAllTasksController(retrieveTasks);

        TaskResponse output = retrieveAllTasksController.getAllTasks();
        assertNull(output.getTasks());

    }

}
