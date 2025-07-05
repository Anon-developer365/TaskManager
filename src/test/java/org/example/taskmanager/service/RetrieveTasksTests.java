package org.example.taskmanager.service;

import org.example.taskmanager.pojo.Task;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import uk.gov.hmcts.taskmanager.domain.TaskResponse;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class RetrieveTasksTests {

    @Mock
    private TaskRepository taskRepository;
    @InjectMocks
    private RetrieveTasks retrieveTasks;

    private Date date;

    private LocalDateTime dueDate;


    @Test
    void checkWhenThereAreTasksInTheDatabaseTheseAreAllReturned() throws ParseException {
        retrieveTasks = new RetrieveTasks(taskRepository);
        final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        date = dateFormat.parse("2025-05-05 17:00");
        dueDate = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        Task task = new Task("1", "develop database", "create a database", "open status", dueDate);
        List<Task> expectedResult = new ArrayList<>();
        expectedResult.add(task);
        Mockito.when(taskRepository.findAll()).thenReturn(expectedResult);
        TaskResponse tasks = retrieveTasks.getAllTasks();
        assertEquals(1, tasks.getTasks().size());
        assertEquals("1", tasks.getTasks().get(0).getId());
    }

    @Test
    void checkWhenThereAreNoTasksInTheDatabaseAnEmptyListIsReturned() {
        List<Task> expectedResult = new ArrayList<>();
        Mockito.when(taskRepository.findAll()).thenReturn(expectedResult);
        retrieveTasks = new RetrieveTasks(taskRepository);
        TaskResponse tasks = retrieveTasks.getAllTasks();
        assertNull(tasks.getTasks());
    }

}
