package org.example.taskmanager.controllers;

import org.example.taskmanager.service.RetrieveTasks;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.gov.hmcts.taskmanager.domain.TaskResponse;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
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
        TaskResponse taskResponse = new TaskResponse();
        when(retrieveTasks.getAllTasks()).thenReturn(taskResponse);

        TaskResponse actual = retrieveAllTasksController.getAllTasks();
        assertNull(actual.getTasks());

    }

    @Test
    void whenAListWithATaskIsReceivedBackFromTheServiceThisIsReturned() throws ParseException {
        retrieveAllTasksController = new RetrieveAllTasksController(retrieveTasks);
        UUID id = UUID.randomUUID();
        final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = dateFormat.parse("2025-05-05 17:00");

        uk.gov.hmcts.taskmanager.domain.Task task = new uk.gov.hmcts.taskmanager.domain.Task();
        task.setTaskDescription("description");
        task.setId(id.toString());
        task.setStatus("status");
        task.setTitle("title");
        task.setDueDate(date);
        TaskResponse expected = new TaskResponse();
        expected.addTasksItem(task);
        when(retrieveTasks.getAllTasks()).thenReturn(expected);

        TaskResponse actual = retrieveAllTasksController.getAllTasks();
        assertEquals(expected.getTasks().size(), actual.getTasks().size());

    }

    @Test
    void whenAListWithMoreThanOneTaskIsReceivedBackFromTheServiceAllItemsAreReturned() throws ParseException {
        retrieveAllTasksController = new RetrieveAllTasksController(retrieveTasks);
        UUID id = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = dateFormat.parse("2025-05-05 17:00");
        uk.gov.hmcts.taskmanager.domain.Task task = new uk.gov.hmcts.taskmanager.domain.Task();
        task.setTaskDescription("description");
        task.setId(id.toString());
        task.setStatus("status");
        task.setTitle("title");
        task.setDueDate(date);
        TaskResponse expected = new TaskResponse();
        expected.addTasksItem(task);
        uk.gov.hmcts.taskmanager.domain.Task task2 = new uk.gov.hmcts.taskmanager.domain.Task();
        task.setTaskDescription("description");
        task.setId(id2.toString());
        task.setStatus("status");
        task.setTitle("title");
        task.setDueDate(date);
        expected.addTasksItem(task2);
        when(retrieveTasks.getAllTasks()).thenReturn(expected);

        TaskResponse actual = retrieveAllTasksController.getAllTasks();
        assertEquals(expected.getTasks().size(), actual.getTasks().size());

    }

}
