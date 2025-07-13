package org.example.taskmanager.service;

import org.example.taskmanager.pojo.Task;
import org.example.taskmanager.validation.ValidationOrchestration;
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
import static org.mockito.Mockito.doNothing;

@SpringBootTest
public class RetrieveTasksTests {

    @Mock
    private ValidationOrchestration validationOrchestration;
    @Mock
    private TaskRepository taskRepository;
    @InjectMocks
    private RetrieveTasks retrieveTasks;


    @Test
    void checkWhenThereAreTasksInTheDatabaseTheseAreAllReturned() throws ParseException {
        retrieveTasks = new RetrieveTasks(taskRepository, validationOrchestration);
        final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        Date date = dateFormat.parse("2025-05-05 17:00");
        LocalDateTime dueDate = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        Task task = new Task("1", "develop database", "create a database", "open status", dueDate);
        List<Task> expectedResult = new ArrayList<>();
        expectedResult.add(task);
        Mockito.when(taskRepository.findAll()).thenReturn(expectedResult);
        doNothing().when(validationOrchestration).getAllTaskValidation("2");
        TaskResponse tasks = retrieveTasks.getAllTasks("2");
        assertEquals(1, tasks.getTasks().size());
        assertEquals("1", tasks.getTasks().get(0).getId());
    }

    @Test
    void checkWhenThereAreNoTasksInTheDatabaseAnEmptyListIsReturned() {
        List<Task> expectedResult = new ArrayList<>();
        Mockito.when(taskRepository.findAll()).thenReturn(expectedResult);
        doNothing().when(validationOrchestration).getAllTaskValidation("2");
        retrieveTasks = new RetrieveTasks(taskRepository, validationOrchestration);
        TaskResponse tasks = retrieveTasks.getAllTasks("2");
        assertNull(tasks.getTasks());
    }

}
